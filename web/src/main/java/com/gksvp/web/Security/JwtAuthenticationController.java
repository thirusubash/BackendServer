package com.gksvp.web.Security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import com.gksvp.web.Security.model.JwtRequest;
import com.gksvp.web.Security.model.JwtResponse;
import com.gksvp.web.user.entity.User;
import com.gksvp.web.user.service.UserService;
import com.gksvp.web.util.AESEncryption;

@RestController
@CrossOrigin
public class JwtAuthenticationController {
	@Autowired
	private AuthenticationManager authenticationManager;

	@Autowired
	private JwtTokenUtil jwtTokenUtil;

	@Autowired
	private JwtUserDetailsService myUserDetailsService;
	@Autowired
	private UserService userService;

	@Autowired
	private AESEncryption aesEncryption;

	@PostMapping(value = "/authenticate")
	@CrossOrigin(origins = "https://localhost:8080", allowCredentials = "true")
	public ResponseEntity<?> createAuthenticationToken(@RequestBody JwtRequest authenticationRequest) throws Exception {
		String username;
		switch (getUsernameType(authenticationRequest.getUsername())) {
			case MOBILE_NUMBER:
				User mobileUser = userService.getUserByMobileNo(authenticationRequest.getUsername());
				username = mobileUser.getUserName();
				System.out.println(username);
				break;
			case EMAIL:
				User emailUser = userService.getUserByEmail(authenticationRequest.getUsername());
				username = emailUser.getUserName();
				break;
			case OTHER:
				username = aesEncryption.encrypt(authenticationRequest.getUsername());
				break;
			default:
				// Provide a default value
				username = null;
				break;
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

	@PostMapping("/register")
	@CrossOrigin(origins = "https://localhost:8080", allowCredentials = "true")
	public ResponseEntity<?> registerUser(@RequestBody User user) {
		try {
			String encryptedEmail = aesEncryption.encrypt(user.getEmail());
			String encryptedMobileNo = aesEncryption.encrypt(user.getMobileNo());
			String encryptedUserName = aesEncryption.encrypt(user.getUserName());

			if (userService.isEmailTaken(encryptedEmail)) {
				return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Email is already taken");
			}
			if (userService.isMobileTaken(encryptedMobileNo)) {
				return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Mobile number is already taken");
			}
			if (userService.isUsernameTaken(encryptedUserName)) {
				return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Username is already taken");
			}

			user.setActive(true);
			user = userService.createUser(user);
			return ResponseEntity.ok("User( " + user.getId() + " )registered successfully");
		} catch (Exception e) {
			// Return an appropriate error response
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
					.body("An error occurred while registering user");
		}
	}

	private void authenticate(String username, String password) throws Exception {
		try {
			authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
		} catch (DisabledException e) {
			throw new Exception("USER_DISABLED", e);
		} catch (BadCredentialsException e) {
			throw new Exception("INVALID_CREDENTIALS", e);
		}
	}
}
