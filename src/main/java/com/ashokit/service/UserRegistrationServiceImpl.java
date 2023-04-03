package com.ashokit.service;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;




import org.passay.CharacterRule;
import org.passay.EnglishCharacterData;
import org.passay.PasswordGenerator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.Example;

import org.springframework.stereotype.Service;

import com.ashokit.appproperties.UserRegAppProperties;
import com.ashokit.constatnts.EmailUtils;
import com.ashokit.constatnts.UserRegConstants;
import com.ashokit.domain.ActivateAccount;
import com.ashokit.domain.Login;
import com.ashokit.domain.User;
import com.ashokit.domain.UserRegistration;
import com.ashokit.repo.UserRegistrationRepository;




@Service
public class UserRegistrationServiceImpl implements UserRegistrationService{
	
	Logger logger=LoggerFactory.getLogger(UserRegistrationServiceImpl.class);
	

	private EmailUtils emailUtils;
	

	private UserRegistrationRepository repository;
	

	
	private Map<String,String> messages =new HashMap<>();





	public UserRegistrationServiceImpl(EmailUtils emailUtils, UserRegistrationRepository repository,
			UserRegAppProperties appProps) {
		super();
		this.emailUtils = emailUtils;
		this.repository = repository;
		this.messages = appProps.getMessages();	
	}

	@Override
	public boolean save(User user) {
		UserRegistration entity=new UserRegistration();
		BeanUtils.copyProperties(user, entity);
		entity.setPassword(generateRandomPassword());
		entity.setActiveSw(UserRegConstants.IN_ACTIVE);
		
		entity=repository.save(entity);
		String msg=messages.get(UserRegConstants.REG_SUCCESS);
		try {
		String body=getBody(entity.getName(), entity.getPassword());
		emailUtils.sendMessageWithAttachment(entity.getEmail(), msg, body);
		}catch(Exception e) {
			logger.error("Exception Occured",e);
		}
		
		
		return true;
	}
	
	

	@Override
	public boolean activatedAccount(ActivateAccount activateAccount) {
	UserRegistration entity=new UserRegistration();
	entity.setEmail(activateAccount.getRegistrationMail());
	entity.setPassword(activateAccount.getTemporaryPassword());
	
    Example<UserRegistration> add=Example.of(entity);
          List<UserRegistration> getAll=repository.findAll(add);
          if(getAll==null) {
        	  return false;
          }else {
        	  UserRegistration reg=getAll.get(0);
        	  reg.setPassword(activateAccount.getNewPassword());
        	  reg.setActiveSw(UserRegConstants.ACTIVE);
        	  repository.save(reg);
        	  return true;
          }
	  
	
		
		
	     
		
	}

	@Override
	public List<User> getAllUsers() {
		
		List<UserRegistration> getAll=repository.findAll();
		List<User> getUsersList=new ArrayList<>();
		for(UserRegistration entity:getAll) {
			User user= new User();
			BeanUtils.copyProperties(entity, user);
			getUsersList.add(user);
		}

		return getUsersList;
	}



	@Override
	public boolean deleteUser(Long uid) {
	  repository.deleteById(uid);
		return true;
	}

	@Override
	public boolean changeAccountStatus(Long uid, String status) {
		Optional<UserRegistration> getUser= repository.findById(uid);
	
		if(getUser.isPresent()) {
			UserRegistration registartion=getUser.get();
			registartion.setActiveSw(status);
			repository.save(registartion);
			return true;
		}else {
		return false;
		}
	}

	@Override
	public String login(Login login) {
	
	       UserRegistration entity=repository.findByEmailAndPassword(login.getEmailId(), login.getPassword());
	
	       if(entity==null) {
	    	   return messages.get(UserRegConstants.INVALID_CREDENTIALS);
	       }else {
	    	   if(entity.getActiveSw().equals(UserRegConstants.ACTIVE)) {
	    		   return messages.get(UserRegConstants.SUCCESS);
	    	   }else {
	    		   return messages.get(UserRegConstants.INACTIVE_ACCOUNT);
	    	   }
	    	
	       }
	       
	       

	}

	@Override
	public User getUserById(Long uid) {
		Optional<UserRegistration> getUser= repository.findById(uid);
		User user=new User();
		if(getUser.isPresent()) {
		UserRegistration registration=getUser.get();
			BeanUtils.copyProperties(registration, user);
		}
		
	     
		return user;
	}



	@Override
	public String forgetPwd(String email) {
		
		UserRegistration entity=repository.findByEmail(email);
		boolean email1=false;
		if(entity==null) {
			return UserRegConstants.INVALID_CREDENTIALS;
		}else {
			String subject="Forget PassWord";
			try {
				
				String body=getBody(entity.getName(), entity.getPassword());
				 email1=emailUtils.sendMessageWithAttachment(entity.getEmail(), subject, body);
			     if(email1) {
			    	 return "Password sent your registered email";
			     }
			} catch (Exception e) {
			
				logger.error("Exception occured",e);
			}
		}
		return null;
	
		
	
		
	}
	
	
	private String generateRandomPassword() {

		List rules = Arrays.asList(new CharacterRule(EnglishCharacterData.UpperCase, 1),
				new CharacterRule(EnglishCharacterData.LowerCase, 1), new CharacterRule(EnglishCharacterData.Digit, 1),new CharacterRule(EnglishCharacterData.Special, 1));

		PasswordGenerator generator = new PasswordGenerator();
		
		return generator.generatePassword(8, rules);
	}
	
	private String getBody(String fullname,String tempPwd)  {
		String filename=UserRegConstants.FILENAME;
		String url="";
		String mailBody=null;
		try (FileReader fileReader=new FileReader(filename);
				BufferedReader bufferedReader=new BufferedReader(fileReader)){
		
		
		
		StringBuilder buffer=new StringBuilder();
		
		String line=bufferedReader.readLine();
		while(line!=null) {		
			buffer.append(line);
			line=bufferedReader.readLine();
			
		}

		mailBody=buffer.toString();
		mailBody=mailBody.replace(UserRegConstants.NAME, fullname);
		mailBody=mailBody.replace(UserRegConstants.PASWORD, tempPwd);
		mailBody=mailBody.replace(UserRegConstants.URL, url);
		
		    
		}catch(Exception e) {
			logger.error("Exception Occured",e);
		}
		
		return mailBody;
		
	}
	
	}


