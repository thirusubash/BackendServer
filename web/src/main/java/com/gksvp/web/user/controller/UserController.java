package com.gksvp.web.user.controller;

import com.gksvp.web.user.dto.UserDTO;
import com.gksvp.web.user.entity.User;
import com.gksvp.web.user.service.UserService;
import com.gksvp.web.util.AESEncryption;
import com.gksvp.web.util.otp.OtpService;
import com.twilio.rest.api.v2010.account.Message;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController {

    private final UserService userService;
    private final OtpService otpService;
    private final AESEncryption aesEncryption;

    public UserController(UserService userService, OtpService otpService, AESEncryption aesEncryption) {
        this.userService = userService;
        this.otpService = otpService;
        this.aesEncryption = aesEncryption;
    }

    @GetMapping
    public ResponseEntity<List<UserDTO>> getAllUsers() throws Exception {
        List<UserDTO> users = userService.getAllUsers();
        return new ResponseEntity<>(users, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserDTO> getUserById(@PathVariable Long id) throws Exception {
        UserDTO user = userService.getUserById(id);
        if (user != null) {
            return new ResponseEntity<>(user, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping
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

    @PutMapping("/{id}")
    public ResponseEntity<User> updateUser(@PathVariable Long id, @RequestBody User user) throws Exception {
        User updatedUser = userService.updateUser(id, user);
        if (updatedUser != null) {
            return new ResponseEntity<>(updatedUser, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable Long id) throws Exception {
        boolean deleted = userService.deleteUser(id);
        if (deleted) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping("/changenumberreqst")
    public ResponseEntity<String> changenumberrequest(@RequestParam Long id, @RequestParam String mobile_no) {
        try {
            Message message = otpService.mobileOtp(mobile_no);
            if (message.getStatus() == Message.Status.FAILED) {
                return ResponseEntity.badRequest().body("Failed to send OTP. Please check the mobile number.");
            } else {
                return ResponseEntity.ok("OTP sent successfully.");
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("An error occurred while sending OTP: " + e.getMessage());
        }
    }

    @PutMapping("/changenumber")
    public ResponseEntity<String> changenumber(@RequestParam Long id, @RequestParam String mobileno,
            @RequestParam int otp) {
        try {
            Boolean status = otpService.verifyMobileOtp(mobileno, otp);
            if (status) {
                Boolean updateStatus = userService.updateNumber(id, mobileno);
                if (updateStatus) {
                    return ResponseEntity.ok("Mobile number updated successfully.");
                }
                return ResponseEntity.ok("Mobile number update failed.");
            } else {
                return ResponseEntity.badRequest().body("Wrong OTP. Please try again.");
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("An error occurred while processing the request: " + e.getMessage());
        }
    }
}
