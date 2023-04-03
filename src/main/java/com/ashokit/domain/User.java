package com.ashokit.domain;

import java.time.LocalDate;

import lombok.Data;



@Data
public class User {
	

	private String name;


	private String email;


	private Long mobileNumber;


	private LocalDate dob;


	private Integer ssnNo;


	
  
}
