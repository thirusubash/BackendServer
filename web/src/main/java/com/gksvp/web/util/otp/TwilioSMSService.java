package com.gksvp.web.util.otp;

import com.twilio.Twilio;
import com.twilio.rest.api.v2010.account.Message;
import com.twilio.type.PhoneNumber;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class TwilioSMSService {

    @Value("${twilio.account-sid}")
    private String accountSid;

    @Value("${twilio.auth-token}")
    private String authToken;

    @Value("${twilio.phone-number}")
    private String twilioPhoneNumber;

    public Message sendSms(String toPhoneNumber, String messageBody) {
        try {
            Twilio.init(accountSid, authToken);

            Message message = Message.creator(
                    new PhoneNumber(toPhoneNumber), // Recipient's phone number
                    new PhoneNumber(twilioPhoneNumber), // Twilio phone number
                    messageBody)
                    .create();

            System.out.println("Message SID: " + message.getSid());

            return message;
        } catch (Exception e) {
            throw new RuntimeException("Failed to send SMS: " + e.getMessage(), e);
        }
    }
}
