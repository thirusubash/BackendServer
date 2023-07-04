package com.gksvp.web.Security;

import java.io.Serializable;
import java.security.KeyPair;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import com.gksvp.web.model.user.User;
import com.gksvp.web.service.user.UserService;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;

@Component
public class JwtTokenUtil implements Serializable {

	private static final long serialVersionUID = -2550185165626007488L;

	@Autowired
	private UserService userService;

	// private Key[] generateSafeToken() {
	// try{
	// KeyPairGenerator keyGen = KeyPairGenerator.getInstance("RSA");
	// keyGen.initialize(4096);
	// KeyPair pair = keyGen.generateKeyPair();
	// byte[] encodedPrivateKey = pair.getPrivate().getEncoded();
	// byte[] encodedPublicKey = pair.getPublic().getEncoded();
	// System.out.println(Base64.getEncoder().encodeToString(encodedPrivateKey));
	// System.out.println(Base64.getEncoder().encodeToString(encodedPublicKey));

	// Key key = Keys.hmacShaKeyFor(encodedPrivateKey);
	// Key key1=Keys.hmacShaKeyFor(encodedPublicKey);
	// Key keys[]={key,key1};
	// return keys;
	// }catch(Exception e)
	// {

	// System.out.println("error"+e);
	// return null;
	// }
	// }
	private final KeyPair nkeys = Keys.keyPairFor(SignatureAlgorithm.RS512);

	public static final long JWT_TOKEN_VALIDITY = 5 * 60 * 60;

	public String getUsernameFromToken(String token) {
		System.out.println("username from token   " + getClaimFromToken(token, Claims::getSubject));
		return getClaimFromToken(token, Claims::getSubject);
	}

	public Date getIssuedAtDateFromToken(String token) {
		System.out.println("Issue date" + getClaimFromToken(token, Claims::getIssuedAt));
		return getClaimFromToken(token, Claims::getIssuedAt);
	}

	public Date getExpirationDateFromToken(String token) {
		System.out.println("Expired date " + getClaimFromToken(token, Claims::getExpiration));
		return getClaimFromToken(token, Claims::getExpiration);
	}

	public <T> T getClaimFromToken(String token, Function<Claims, T> claimsResolver) {
		final Claims claims = getAllClaimsFromToken(token);
		return claimsResolver.apply(claims);
	}

	private Claims getAllClaimsFromToken(String token) {
		Claims claims = null;
		try {

			claims = Jwts.parserBuilder()
					.setSigningKey(nkeys.getPublic())
					.build()
					.parseClaimsJws(token).getBody();

		} catch (Exception e) {
			System.out.println("eror" + ": " + e.getMessage());
		}
		return claims;
	}

	private Boolean isTokenExpired(String token) {
		final Date expiration = getExpirationDateFromToken(token);
		return expiration.before(new Date());
	}

	private Boolean ignoreTokenExpiration(String token) {
		// here you specify tokens, for that the expiration is ignored
		return false;
	}

	public String generateToken(UserDetails userDetails) {
		Map<String, Object> claims = new HashMap<>();
		User user = userService.getUserByUserName(userDetails.getUsername());
		final String authorities = userDetails.getAuthorities().stream()
				.map(GrantedAuthority::getAuthority)
				.collect(Collectors.joining(","));
		// claims.put("scopes", roles.stream().map(role -> new
		// SimpleGrantedAuthority(role.getName())).collect(Collectors.toList()));
		claims.put("roles", authorities);
		claims.put("iss", "www.gksvp.com");
		claims.put("isActive", userDetails.isEnabled());
		claims.put("email", user.getEmail());
		claims.put(authorities, authorities);
		claims.put("pan", user.getPAN());
		System.out.println("claims info : " + claims);
		return doGenerateToken(claims, userDetails.getUsername());
	}

	private String doGenerateToken(Map<String, Object> claims, String subject) {

		return Jwts.builder().setClaims(claims).setSubject(subject).setIssuedAt(new Date(System.currentTimeMillis()))
				.setExpiration(new Date(System.currentTimeMillis() + JWT_TOKEN_VALIDITY * 1000))
				.signWith(nkeys.getPrivate()).compact();
	}

	public Boolean canTokenBeRefreshed(String token) {
		return (!isTokenExpired(token) || ignoreTokenExpiration(token));
	}

	public Boolean validateToken(String token, UserDetails userDetails) {
		final String username = getUsernameFromToken(token);
		System.out.println("useranem from validate method" + username);
		return (username.equals(userDetails.getUsername()) && !isTokenExpired(token));
	}
}
