package com.ashokit.service;

import java.util.List;

import com.ashokit.domain.ActivateAccount;
import com.ashokit.domain.Login;
import com.ashokit.domain.User;


public interface UserRegistrationService {
	
	public boolean save(User user);
	
	public boolean activatedAccount(ActivateAccount activateAccount);
	
	public List<User> getAllUsers();
	
	public User getUserById(Long uid);
	
	
	public boolean deleteUser(Long uid);
	
	public boolean changeAccountStatus(Long uid,String status);
	
	public String login(Login login);
	
	public String forgetPwd(String email);

}
