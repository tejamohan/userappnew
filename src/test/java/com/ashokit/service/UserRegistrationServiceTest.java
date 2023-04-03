package com.ashokit.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.BDDMockito.given;

import java.io.BufferedReader;
import java.io.FileReader;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.apache.catalina.startup.ListenerCreateRule.OptionalListener;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.BeanUtils;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.cglib.core.EmitUtils;
import org.springframework.data.domain.Example;

import com.ashokit.appproperties.UserRegAppProperties;
import com.ashokit.constatnts.EmailUtils;
import com.ashokit.constatnts.UserRegConstants;
import com.ashokit.domain.ActivateAccount;
import com.ashokit.domain.Login;
import com.ashokit.domain.User;
import com.ashokit.domain.UserRegistration;
import com.ashokit.repo.UserRegistrationRepository;

@SpringBootTest
@ExtendWith(MockitoExtension.class)
public class UserRegistrationServiceTest {
    @Mock	
	private UserRegistrationRepository repo;
	
    @Mock
    private UserRegAppProperties props;
    
    @Mock
    private UserRegConstants constants;

    @InjectMocks
	private UserRegistrationServiceImpl service;
   
    @InjectMocks
    private EmailUtils utils;
    
    
    private Map<String,String> messages =new HashMap<>();
    
     
    
    
    @Test
    public void activateAccTest() {
      	UserRegistration entity=new UserRegistration();
    	ActivateAccount active=new ActivateAccount();
    	active.setRegistrationMail("tejamunakala@gmail.com");
    	active.setTemporaryPassword("~@456gdtg");
   	entity.setEmail(active.getRegistrationMail());
   	entity.setPassword(active.getTemporaryPassword());
    	   List<UserRegistration> users=null;
    	   Example<UserRegistration> add=Example.of(entity);
         given(repo.findAll(add)).willReturn(users);
         boolean testFaail=service.activatedAccount(active);
         assertFalse(testFaail);
    }
    @Test
    public void activateAccTestTrue() {
     
    	UserRegistration entity=new UserRegistration();
    	ActivateAccount active=new ActivateAccount();
    	active.setRegistrationMail("tejamunakala@gmail.com");
    	active.setTemporaryPassword("~@456gdtg");
    	entity.setEmail(active.getRegistrationMail());
    	entity.setPassword(active.getTemporaryPassword());
    	 List<UserRegistration> users=getUserData(); 	 
    	   Example<UserRegistration> add=Example.of(entity);
         when(repo.findAll(add)).thenReturn(users);
      	  UserRegistration reg=users.get(0);
    	  reg.setPassword("~378hfnjvn");
    	  reg.setActiveSw("active");
           repo.save(reg);
            boolean reTrue=service.activatedAccount(active);
           assertTrue(reTrue);
           
    	 
      
         
    }
    @Test
    public void getAllUsersTest() {
    	
    	List<User> user=getUsersData();
    	 List<UserRegistration> users=getUserData();
    	 given(repo.findAll()).willReturn(users);
    	 user=service.getAllUsers();
    	 assertThat(user);
    	 
    	 
    	
    	
    }
    @Test
    public void deleteById() {
    	
    
    	 UserRegistration reg=new UserRegistration();
         reg.setUserRegId(1l);    
         repo.deleteById(reg.getUserRegId());
    	 boolean setTrue=service.deleteUser(reg.getUserRegId());
    	 assertTrue(setTrue);
    	 
    	 
    	
    	
    }
    @Test
    public void changeStstusTrueTest() {
    	UserRegistration reg=new UserRegistration();
    	reg.setName("teja");
    	reg.setEmail("tejamunakala@gmail.com");
    	reg.setDob(LocalDate.now());
    	reg.setMobileNumber(8121507113l);
    	reg.setSsnNo(306080);
    	reg.setPassword("7gui888`");
    	reg.setActiveSw("N");
    	reg.setUserRegId(1l);
    	reg.setCreatedBy("teja");
    	reg.setCreatedDate(LocalDate.now());
    	reg.setUdatedBy("teja");
    	reg.setUpdatedDate(LocalDate.now());
         Optional<UserRegistration> regs=Optional.of(reg);
     given(repo.findById(reg.getUserRegId())).willReturn(regs);
        reg.setActiveSw("Y");
        repo.save(reg);
        boolean setTrue=service.changeAccountStatus(reg.getUserRegId(), reg.getActiveSw());
        assertTrue(setTrue);
        
    	 
    	 
    	
    	
    }
    @Test
    public void changeStstusFalseTest() {
    	UserRegistration reg=new UserRegistration();
    	
    	reg.setName("teja");
    	reg.setEmail("tejamunakala@gmail.com");
    	reg.setDob(LocalDate.now());
    	reg.setMobileNumber(8121507113l);
    	reg.setSsnNo(306080);
    	reg.setPassword("7gui888`");
    	reg.setActiveSw("N");
    	reg.setUserRegId(1l);
    	reg.setCreatedBy("teja");
    	reg.setCreatedDate(LocalDate.now());
    	reg.setUdatedBy("teja");
    	reg.setUpdatedDate(LocalDate.now());
    	Optional<UserRegistration> regs=Optional.empty();
     given(repo.findById(1l)).willReturn(regs);
        boolean setFalse=service.changeAccountStatus(reg.getUserRegId(), reg.getActiveSw());
        assertFalse(setFalse);
        
    	 
    	 
    	
    	
    }
    @Test
    public void loginTestNullCheck() {
    	Login login=new Login();
    	login.setEmailId(null);
    	login.setPassword("~@6754vbds");
          UserRegistration reg=null;
    	//Optional<UserRegistration> empty=Optional.ofNullable(reg);
    	when(repo.findByEmailAndPassword(login.getEmailId(), login.getPassword())).thenReturn(reg);
        String msg=service.login(login);
        String excpextedResult=props.getMessages().get(UserRegConstants.INVALID_CREDENTIALS);
        assertEquals(excpextedResult, msg);
    	 
    	 
    	
    	
    }
    @Test
    public void loginTest() {
    	Login login=new Login();
    	String msg="success";
    	login.setEmailId("tejamunakala@gmail.com");
    	login.setPassword("~@6754vbds");
          UserRegistration reg=getUserReg();
    	//Optional<UserRegistration> empty=Optional.ofNullable(reg);
    	when(repo.findByEmailAndPassword(login.getEmailId(), login.getPassword())).thenReturn(reg);
        reg.getActiveSw().equals(UserRegConstants.ACTIVE);
        service.login(login);
        assertEquals( UserRegConstants.SUCCESS,msg);
    	 
    	 
    	
    	
    }
    @Test
    public void loginTests() {
    	Login login=new Login();
    	String msg="account-inactive";
    	login.setEmailId("tejamunakala@gmail.com");
    	login.setPassword("~@6754vbds");
          UserRegistration reg=getUserReg();
          reg.setActiveSw("in-active");
    	//Optional<UserRegistration> empty=Optional.ofNullable(reg);
    	when(repo.findByEmailAndPassword(login.getEmailId(), login.getPassword())).thenReturn(reg);
    	 reg.getActiveSw().equals(UserRegConstants.ACTIVE);
        service.login(login);
        assertEquals( UserRegConstants.INACTIVE_ACCOUNT,msg);
    	 
    	 
    	
    	
    }
    @Test
    public void getUserById() {
      UserRegistration reg=getUserReg();
      User user=getUser();    
         Optional<UserRegistration> regs=Optional.of(reg);
     given(repo.findById(reg.getUserRegId())).willReturn(regs);
        regs.isPresent();
           BeanUtils.copyProperties(regs, user);
        User getUser=service.getUserById(reg.getUserRegId());
        assertThat(getUser);
        
    	 
    	 
    	 
    	
    	
    }
    
    @Test
    public void forgetPwdNullCheck() {
    	String email=null;
          UserRegistration reg=null;
    	//Optional<UserRegistration> empty=Optional.ofNullable(reg);
    	when(repo.findByEmail(email)).thenReturn(reg);
        String msg=service.forgetPwd(email);
        String excpextedResult=UserRegConstants.INVALID_CREDENTIALS;
        assertEquals(excpextedResult, msg);
    	 
    	 
    	
    	
    }
//    @Test
//    public void forgetPwdCheck() {
//    	String email="tejamunakala@gmail.com";
//    	String result="Password sent your registered email";
//          UserRegistration reg=getUserReg();
//    	when(repo.findByEmail(email)).thenReturn(reg);
//    	String subject="Forget PassWord";
//    	String body=getBody(reg.getName(), reg.getPassword());
//		 boolean email1=utils.sendMessageWithAttachment(reg.getEmail(), subject, body);
//          String msg=service.forgetPwd(email);
//        assertEquals(result, msg);
//    	 
//    	 
//    	
//    	
//    }
   
    private List<UserRegistration> getUserData() {
    	List<UserRegistration> regs=new ArrayList<>();
    	UserRegistration reg=new UserRegistration();
    	reg.setName("teja");
    	reg.setEmail("tejamunakala@gmail.com");
    	reg.setDob(LocalDate.now());
    	reg.setMobileNumber(8121507113l);
    	reg.setSsnNo(306080);
    	reg.setPassword("7gui888`");
    	reg.setActiveSw("N");
    	reg.setUserRegId(1l);
    	reg.setCreatedBy("teja");
    	reg.setCreatedDate(LocalDate.now());
    	reg.setUdatedBy("teja");
    	reg.setUpdatedDate(LocalDate.now());
    	
      	UserRegistration reg1=new UserRegistration();
     	reg1.setName("nag");
    	reg1.setEmail("tejamunakala@gmail.com");
    	reg1.setDob(LocalDate.now());
    	reg1.setMobileNumber(8121507117l);
    	reg1.setSsnNo(406080);
    	reg1.setPassword("7gui899`");
    	reg1.setActiveSw("N");
    	reg1.setUserRegId(2l);
    	reg1.setCreatedBy("nag");
    	reg1.setCreatedDate(LocalDate.now());
    	reg1.setUdatedBy("nag");
    	reg1.setUpdatedDate(LocalDate.now());
    	regs.add(reg);
    	regs.add(reg1);
    
    	
		return regs;
    	
    }
    private List<User> getUsersData() {
    	List<User> regs=new ArrayList<>();
    	User reg=new User();
    	reg.setName("teja");
    	reg.setEmail("tejamunakala@gmail.com");
    	reg.setDob(LocalDate.now());
    	reg.setMobileNumber(8121507113l);
    	reg.setSsnNo(306080);	
      	User reg1=new User();
     	reg1.setName("nag");
    	reg1.setEmail("tejamunakala@gmail.com");
    	reg1.setDob(LocalDate.now());
    	reg1.setMobileNumber(8121507117l);
    	reg1.setSsnNo(406080);
    	
    	regs.add(reg);
    	regs.add(reg1);
    
    	
		return regs;
    	
    }
    private UserRegistration getUserReg() {
	UserRegistration reg=new UserRegistration();
    	
    	reg.setName("teja");
    	reg.setEmail("tejamunakala@gmail.com");
    	reg.setDob(LocalDate.now());
    	reg.setMobileNumber(8121507113l);
    	reg.setSsnNo(306080);
    	reg.setPassword("7gui888`");
    	reg.setActiveSw("active");
    	reg.setUserRegId(1l);
    	reg.setCreatedBy("teja");
    	reg.setCreatedDate(LocalDate.now());
    	reg.setUdatedBy("teja");
    	reg.setUpdatedDate(LocalDate.now());
		return reg;
    	
    }
    private User getUser() {
        	
     	User reg=new User();
    	reg.setName("teja");
    	reg.setEmail("tejamunakala@gmail.com");
    	reg.setDob(LocalDate.now());
    	reg.setMobileNumber(8121507113l);
    	reg.setSsnNo(306080);    		
    	return reg;
        	
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
			
		}
		
		return mailBody;
		
	}

	

}
