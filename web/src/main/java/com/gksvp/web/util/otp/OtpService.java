package com.gksvp.web.util.otp;

import com.twilio.rest.api.v2010.account.Message;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

@Service
public class OtpService {

    @Autowired
    private EmailService emailService;

    private final Map<String, OtpData> mobileOtpMap = new HashMap<>();
    private Map<String, OtpData> mailOtpMap = new HashMap<>();
    @Autowired
    private TwilioSMSService twilioSMSService;

    public Message mobileOtp(String phoneNumber) {
        int otp = generateOtp();
        mobileOtpMap.put(phoneNumber, new OtpData(otp, LocalDateTime.now()));

        String message = "Your gksvp OTP is: " + otp;
        Message message2 = twilioSMSService.sendSms(phoneNumber, message);
        return message2;
    }

    public void MailOtp(String email) {
        int otp = generateOtp();
        LocalDateTime creationTime = LocalDateTime.now();

        OtpData otpData = new OtpData(otp, creationTime);
        mailOtpMap.put(email, otpData);

        String subject = "Your OTP for GKSVP ";
        String message = "Your OTP is: " + otp;

        emailService.sendEmail(email, subject, message);
    }

    private int generateOtp() {
        Random random = new Random();
        return 100000 + random.nextInt(900000); // Generate a 6-digit OTP
    }

    public boolean verifyMobileOtp(String phoneNumber, int enteredOtp) {
        OtpData otpData = mobileOtpMap.get(phoneNumber);
        if (otpData != null && otpData.getOtp() == enteredOtp) {
            LocalDateTime creationTime = otpData.getCreationTime();
            if (Duration.between(creationTime, LocalDateTime.now()).toMinutes() <= 15) {
                mobileOtpMap.remove(phoneNumber); // Remove OTP from map after successful verification
                return true;
            }
        }
        return false;
    }

    public boolean verifyMailOtp(String phoneNumber, int enteredOtp) {
        OtpData otpData = mailOtpMap.get(phoneNumber);
        if (otpData != null && otpData.getOtp() == enteredOtp) {
            LocalDateTime creationTime = otpData.getCreationTime();
            if (Duration.between(creationTime, LocalDateTime.now()).toMinutes() <= 15) {
                mailOtpMap.remove(phoneNumber); // Remove OTP from map after successful verification
                return true;
            }
        }
        return false;
    }

    private static class OtpData {
        private int otp;
        private LocalDateTime creationTime;

        public OtpData(int otp, LocalDateTime creationTime) {
            this.otp = otp;
            this.creationTime = creationTime;
        }

        public int getOtp() {
            return otp;
        }

        public LocalDateTime getCreationTime() {
            return creationTime;
        }
    }
}
