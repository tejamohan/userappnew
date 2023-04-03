package com.ashokit.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ashokit.domain.UserRegistration;

public interface UserRegistrationRepository extends JpaRepository<UserRegistration, Long>{
	
	
	public UserRegistration findByEmailAndPassword(String email,String password);
	
	public UserRegistration findByEmail(String email);

}
