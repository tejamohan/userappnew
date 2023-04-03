package com.ashokit.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.ashokit.domain.ActivateAccount;
import com.ashokit.domain.Login;
import com.ashokit.domain.User;

import com.ashokit.service.UserRegistrationService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@RestController
public class UserRegistrationController {
	
	
	private ObjectMapper mapper;
	
	private UserRegistrationService service;
	
	
	
	
	public UserRegistrationController(ObjectMapper mapper, UserRegistrationService service) {
		super();
		this.mapper = mapper;
		this.service = service;
	}



    @PostMapping("/saveUser")
	public ResponseEntity<String> saveUser(@RequestBody User user) {
		boolean isSaved=service.save(user);
		String msg="";
		if(isSaved) {
		     msg="Registration Successful";
		     return new ResponseEntity<>(msg,HttpStatus.CREATED);
		}else {
			msg="Registration Failed";
			return new ResponseEntity<>(msg,HttpStatus.INTERNAL_SERVER_ERROR);
		}
		

		
	}
    
    @PostMapping("/activateUser")
	public ResponseEntity<String> activateUserAccount(@RequestBody ActivateAccount activateAccount) {
		 boolean isActivated=service.activatedAccount(activateAccount);
			String msg="";
		 if(isActivated) {
			 msg="Account Activated Successfully";
			 return new ResponseEntity<>(msg,HttpStatus.CREATED);
		 }else {
			 msg="Bad Credentials";
		 }
		
		return new ResponseEntity<>(msg,HttpStatus.BAD_REQUEST);
		
	}
    @GetMapping("/getAllUsers")
 	public ResponseEntity<String> getAllUserAccounts() throws JsonProcessingException{
 		
 		
 		return new ResponseEntity<>(mapper.writeValueAsString(service.getAllUsers()),HttpStatus.OK);
 		
 	}
    @GetMapping("/getAccountById/{uid}")
 	public ResponseEntity<String> getAccountById(@PathVariable Long uid) throws JsonProcessingException{
 		
 		
 		return new ResponseEntity<>(mapper.writeValueAsString(service.getUserById(uid)),HttpStatus.OK);
 		
 	}
    @DeleteMapping("/deleteById/{uid}")
   	public ResponseEntity<String> deleteAccountById(@PathVariable Long uid) throws JsonProcessingException{
   		
   		
   		return new ResponseEntity<>(mapper.writeValueAsString(service.deleteUser(uid)),HttpStatus.OK);
   		
   	}
    @PutMapping("/changeAccStatus/{uid}/{status}")
   	public ResponseEntity<String> changeAccountStatus(@PathVariable Long uid,@PathVariable String status) {
   		boolean isStatusChanged=service.changeAccountStatus(uid, status);
   		String msg="";
   		if(isStatusChanged) {
   			msg="Status Changed Successfully";
   			return new ResponseEntity<>(msg,HttpStatus.CREATED);
   		}else {
   			msg="Status not Changed";
   			return new ResponseEntity<>(msg,HttpStatus.CREATED);
   		
   		}
   		
   		
   		
   	}
    @PostMapping("/login")
  	public ResponseEntity<String> saveUser(@RequestBody Login login) throws JsonProcessingException{
  		
  		
  		return new ResponseEntity<>(mapper.writeValueAsString(service.login(login)),HttpStatus.CREATED);
  		
  	}
    
    @GetMapping("/getAccountByEmail/{email}")
  	public ResponseEntity<String> getAccountById(@PathVariable String email) throws JsonProcessingException{
  		
  		
  		return new ResponseEntity<>(mapper.writeValueAsString(service.forgetPwd(email)),HttpStatus.OK);
  		
  	}

	

}
