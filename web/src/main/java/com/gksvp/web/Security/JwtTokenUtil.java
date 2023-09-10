package com.gksvp.web.Security;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.Serial;
import java.io.Serializable;
import java.security.KeyPair;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import com.gksvp.web.user.entity.User;
import com.gksvp.web.user.service.UserService;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;

@Component
public class JwtTokenUtil implements Serializable {
	private static final Logger logger = LoggerFactory.getLogger(JwtTokenUtil.class);

	@Serial
	private static final long serialVersionUID = -2550185165626007488L;

	private final UserService userService;

	private final KeyPair keys = Keys.keyPairFor(SignatureAlgorithm.RS512);

	public static final long JWT_TOKEN_VALIDITY = 5 * 60 * 60;

	public JwtTokenUtil(UserService userService) {
		this.userService = userService;
	}

	public String getUsernameFromToken(String token) {
		return getClaimFromToken(token, Claims::getSubject);
	}

	private Date getIssuedAtDateFromToken(String token) {
		return getClaimFromToken(token, Claims::getIssuedAt);
	}

	public Date getExpirationDateFromToken(String token) {
		return getClaimFromToken(token, Claims::getExpiration);
	}

	public <T> T getClaimFromToken(String token, Function<Claims, T> claimsResolver) {
		final Claims claims = getAllClaimsFromToken(token);
		return claimsResolver.apply(claims);
	}

	private Claims getAllClaimsFromToken(String token) {
		Claims claims;
		try {
			claims = Jwts.parserBuilder()
					.setSigningKey(keys.getPublic())
					.build()
					.parseClaimsJws(token).getBody();
		} catch (Exception e) {
			logger.error("Error parsing JWT token: {}", e.getMessage());
			throw e;
		}
		return claims;
	}

	private Boolean isTokenExpired(String token) {
		final Date expiration = getExpirationDateFromToken(token);
		return expiration.before(new Date());
	}

	private Boolean ignoreTokenExpiration(String token) {
		logger.warn("Ignoring token expiration"+ token);
		return false;
	}

	public String generateToken(UserDetails userDetails) throws Exception {
		Map<String, Object> claims = new HashMap<>();
		User user = userService.getUserByUserName(userDetails.getUsername());

		final String authorities = userDetails.getAuthorities().stream()
				.map(GrantedAuthority::getAuthority)
				.collect(Collectors.joining(","));

		claims.put("roles", authorities);
		claims.put("iss", "www.gksvp.com");
		claims.put("isActive", userDetails.isEnabled());
		claims.put("id", user.getId());

		return doGenerateToken(claims, userDetails.getUsername());
	}

	private String doGenerateToken(Map<String, Object> claims, String subject) {
		// Set your audience here
		String audience = "https://gksvp.com";
		return Jwts.builder()
				.setHeaderParam("alg", "RS512") // Algorithm
				.setHeaderParam("typ", "JWT") // Token Type
				.setClaims(claims)
				.setSubject(subject)
				.setAudience(audience)
				.setIssuedAt(new Date(System.currentTimeMillis()))
				.setExpiration(new Date(System.currentTimeMillis() + JWT_TOKEN_VALIDITY * 1000))
				.signWith(keys.getPrivate()).compact();
	}

	public Boolean canTokenBeRefreshed(String token) {
		return (!isTokenExpired(token) || ignoreTokenExpiration(token));
	}

	public Boolean validateToken(String token, UserDetails userDetails) {
		final String username = getUsernameFromToken(token);
		logger.info("Username from validate method: {}", username);
		return (username.equals(userDetails.getUsername()) && !isTokenExpired(token));
	}
}
