package com.ashokit.constatnts;

import javax.mail.internet.MimeMessage;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;

@Component
public class EmailUtils {
	


	private JavaMailSender mailSender;
	
	boolean isValidMail=false;
	
	
	public EmailUtils(JavaMailSender mailSender) {
		super();
		
		this.mailSender = mailSender;
	}


	public boolean sendMessageWithAttachment(
		
			  String to, String subject, String body)  {
			    // ...
			    
			    MimeMessage message = mailSender.createMimeMessage();
			     try {
			    MimeMessageHelper helper = new MimeMessageHelper(message, true);
			    
			
			    helper.setTo(to);
			    helper.setSubject(subject);
			    helper.setText(body,true);
			    mailSender.send(message);
			    isValidMail=true;
			     }catch(Exception e) {
			    	
			     }
			   

			   return isValidMail;
			    // ...
			}

}
