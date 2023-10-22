package com.gksvp.web.security;

import com.gksvp.web.user.entity.Role;
import com.gksvp.web.user.entity.User;
import com.gksvp.web.user.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@Transactional(readOnly = true)
public class JwtUserDetailsService implements UserDetailsService {

    private final UserService userService;
    private static final Logger logger = LoggerFactory.getLogger(JwtUserDetailsService.class);

    public JwtUserDetailsService(UserService userService) {
        this.userService = userService;
    }

    @Override
    public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException {
        try {
            User user = userService.getUserByUserName(userName);
            if (user == null) {
                throw new UsernameNotFoundException("User not found with username: " + userName);
            }
            List<GrantedAuthority> authorities = getUserAuthority(user.getRoles());
            return buildUserForAuthentication(user, authorities);
        } catch (Exception e) {
            logger.error("Error loading user by username", e);
            throw new UsernameNotFoundException("User not found with username: " + userName, e);
        }
    }

    /**
     * Converts roles to Spring Security GrantedAuthority objects.
     */
    private List<GrantedAuthority> getUserAuthority(Set<Role> userRoles) {
        return userRoles.stream()
                .map(role -> new SimpleGrantedAuthority(role.getName()))
                .collect(Collectors.toList());
    }

    /**
     * Convert custom User object into Spring's UserDetails object.
     */
    private UserDetails buildUserForAuthentication(User user, List<GrantedAuthority> authorities) {
        return new org.springframework.security.core.userdetails.User(
                user.getUserName(),
                user.getPassword(),
                user.getActive(),
                user.getAccountNonExpired(),
                user.getCredentialsNonExpired(),
                user.getAccountNotLocked(),
                authorities);
    }
}
