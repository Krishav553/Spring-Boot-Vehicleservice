package com.vehicle.services.twilio;

import java.text.DecimalFormat;
import java.util.Random;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.twilio.Twilio;
import com.twilio.rest.api.v2010.account.Message;
import com.twilio.type.PhoneNumber;
import com.vehicle.services.entity.DAOUser;

@Service

public class SMSnotification {
	
	@Value("${sms.acc.id}")
	public String ACCOUNT_SID;
	@Value("${sms.acc.auth.token}")
	public String AUTH_TOKEN;
	@Value("${sms.trial.no}")
	public String phoneNumber;
	

	public String sendSMS(DAOUser user) { 
		String otp= new DecimalFormat("0000").format(new Random().nextInt(9999));
		System.out.println(otp);
	    Twilio.init(ACCOUNT_SID, AUTH_TOKEN);

	    Message message = Message
	            .creator(new PhoneNumber("+91"+user.getPhonenumber()), // to
	                    new PhoneNumber(phoneNumber), // from
	                    "Hello "+ user.getName() + " , aapke gaand marwane ka otp yeh hai " + otp)
	            .create();

	    System.out.println(message.getSid());
	    return otp;
	}
	

}
