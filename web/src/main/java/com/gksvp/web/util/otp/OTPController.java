package com.gksvp.web.util.otp;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import com.gksvp.web.user.entity.User;
import com.gksvp.web.user.repository.UserRepository;
import com.twilio.rest.api.v2010.account.Message;

public class OTPController {

    private final OtpService otpService;
    private final UserRepository userRepository;

    private OTPController(OtpService otpService, UserRepository userRepository) {
        this.otpService = otpService;
        this.userRepository = userRepository;
    }

    @PostMapping("/send-otp")
    public ResponseEntity<String> sendOTP(@RequestBody String mobilenumber) throws Exception {

        Message message = otpService.mobileOtp(mobilenumber);

        if (message.getStatus() == Message.Status.FAILED) {

            return ResponseEntity.ok("failed to send OTP ");
        } else {
            return ResponseEntity.badRequest().body("OTP has already been sent to this mobile nummber " + mobilenumber);
        }
    }

    @PostMapping("/send-otp-mobile")
    public ResponseEntity<String> sendOTPtoMobile(@RequestBody String mobilenumber) throws Exception {
        User user = userRepository.findByMobileNo(mobilenumber);
        Message message = otpService.mobileOtp(user.getMobileNo());

        if (message.getStatus() == Message.Status.FAILED) {

            return ResponseEntity.ok("failed to send OTP ");
        } else {
            return ResponseEntity.badRequest().body("OTP has already been sent to this mobile nummber "
                    + user.getMobileNo().substring(Math.max(0, user.getMobileNo().length() - 4)));
        }
    }

    @PostMapping("/verify-otp")
    public ResponseEntity<String> verifyOTP(@RequestBody String mobilenumber, @RequestParam int otp) {
        try {
            boolean otpstatus = otpService.verifyMobileOtp(mobilenumber, otp);

            if (otpstatus) {
                return ResponseEntity.ok().body("OTP verified");
            } else {
                return ResponseEntity.badRequest().body("Wrong OTP");
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An error occurred");
        }
    }
}
