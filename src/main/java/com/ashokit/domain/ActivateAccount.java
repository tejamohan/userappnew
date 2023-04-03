package com.ashokit.domain;



import lombok.Data;

@Data
public class ActivateAccount {
	
	private String registrationMail;
	private String newPassword;
	private String temporaryPassword;
	private String conformPassword;
	

}
