package com.gksvp.web.Security;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import org.springframework.transaction.annotation.Transactional;

import com.gksvp.web.user.entity.Role;
import com.gksvp.web.user.entity.User;
import com.gksvp.web.user.service.UserService;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class JwtUserDetailsService implements UserDetailsService {

    private final UserService userService;

    public JwtUserDetailsService(UserService userService) {
        this.userService = userService;
    }

    @Override
    @Transactional
    public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException {
        try {
            User user = userService.getUserByUserName(userName);
            List<GrantedAuthority> authorities = getUserAuthority(user.getRoles());
            return buildUserForAuthentication(user, authorities);
        } catch (UsernameNotFoundException ex) {
            throw ex; // Re-throw the exception to propagate it
        } catch (Exception e) {
            // Handle other exceptions here if needed
            throw new UsernameNotFoundException("User not found", e);
        }
    }

    private List<GrantedAuthority> getUserAuthority(Set<Role> userRoles) {
        return userRoles.stream()
                .map(role -> new SimpleGrantedAuthority(role.getName()))
                .collect(Collectors.toList());
    }

    private UserDetails buildUserForAuthentication(User user, List<GrantedAuthority> authorities) {

        return new org.springframework.security.core.userdetails.User(
                user.getUserName(),
                user.getPassword(),
                user.getActive(),
                true,
                true,
                true,
                authorities);
    }
}
