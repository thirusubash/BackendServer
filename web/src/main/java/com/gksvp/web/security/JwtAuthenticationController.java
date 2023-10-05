package com.gksvp.web.security;

import com.gksvp.web.security.model.JwtRequest;
import com.gksvp.web.security.model.JwtResponse;
import com.gksvp.web.user.entity.User;
import com.gksvp.web.user.service.UserService;
import com.gksvp.web.util.AESEncryption;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin
public class JwtAuthenticationController {

	private static final Logger logger = LoggerFactory.getLogger(JwtAuthenticationController.class);


	private final AuthenticationManager authenticationManager;


	private final JwtTokenUtil jwtTokenUtil;


	private final JwtUserDetailsService myUserDetailsService;


	private final UserService userService;


	private final AESEncryption aesEncryption;

	public JwtAuthenticationController(AuthenticationManager authenticationManager, JwtTokenUtil jwtTokenUtil, JwtUserDetailsService myUserDetailsService, UserService userService, AESEncryption aesEncryption) {
		this.authenticationManager = authenticationManager;
		this.jwtTokenUtil = jwtTokenUtil;
		this.myUserDetailsService = myUserDetailsService;
		this.userService = userService;
		this.aesEncryption = aesEncryption;
	}

	@PostMapping(value = "/authenticate")
	@CrossOrigin(origins = "https://localhost:8080", allowCredentials = "true")
	public ResponseEntity<?> createAuthenticationToken(@RequestBody JwtRequest authenticationRequest) throws Exception {
		String username;
		switch (getUsernameType(authenticationRequest.getUsername())) {
			case MOBILE_NUMBER -> {
				User mobileUser = userService.getUserByMobileNo(authenticationRequest.getUsername());
				if (mobileUser != null) {
					username = mobileUser.getUserName();
					logger.info("Authenticated with mobile number: {}", authenticationRequest.getUsername());
				} else {
					throw new UsernameNotFoundException("Mobile number not found: " + authenticationRequest.getUsername());
				}
			}
			case EMAIL -> {
				User emailUser = userService.getUserByEmail(authenticationRequest.getUsername());
				if (emailUser != null) {
					username = emailUser.getUserName();
					logger.info("Authenticated with email: {}", username);
				} else {
					throw new UsernameNotFoundException("Email not found: " + authenticationRequest.getUsername());
				}
			}
			case OTHER -> {
				username = aesEncryption.encrypt(authenticationRequest.getUsername());
				logger.info("Authenticated with other username: {}", username);
			}
			default -> {
				throw new UsernameNotFoundException("Unknown username type: " + authenticationRequest.getUsername());
			}
		}

		authenticate(username, authenticationRequest.getPassword());
		final UserDetails userDetails = myUserDetailsService.loadUserByUsername(username);
		final String token = jwtTokenUtil.generateToken(userDetails);

		return ResponseEntity.ok(new JwtResponse(token));
	}


	private enum UsernameType {
		MOBILE_NUMBER, EMAIL, OTHER
	}

	private UsernameType getUsernameType(String username) {
		if (username.matches("\\d{10}")) {
			return UsernameType.MOBILE_NUMBER;
		} else if (username.matches("[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Z|a-z]{2,}")) {
			return UsernameType.EMAIL;
		} else {
			return UsernameType.OTHER;
		}
	}



	private void authenticate( String username, String password) throws Exception {
		try {
			authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
		} catch (DisabledException e) {
			throw new Exception("USER_DISABLED", e);
		} catch (BadCredentialsException e) {
			throw new Exception("INVALID_CREDENTIALS", e);
		}
	}


	@PostMapping("/register")
	@CrossOrigin(origins = "https://localhost:8080", allowCredentials = "true")
	public ResponseEntity<?> registerUser(@RequestBody User user) {
		try {
			if (userService.isEmailTaken(user.getEmail())) {
				return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Email is already taken");
			}
			if (userService.isMobileTaken(user.getMobileNo())) {
				return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Mobile number is already taken");
			}
			if (userService.isUsernameTaken(user.getUserName())) {
				return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Username is already taken");
			}
			user.setActive(true);
			user = userService.createUser(user);
			logger.info("User registered successfully with id: {}", user.getId());
			return ResponseEntity.ok("User( " + user.getId() + " )registered successfully");
		} catch (Exception e) {
			logger.error("An error occurred while registering user: {}", e.getMessage());
			// Return an appropriate error response
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
					.body("An error occurred while registering user");
		}
	}




}
