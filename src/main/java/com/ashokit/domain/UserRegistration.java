package com.ashokit.domain;

import java.time.LocalDate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import lombok.Data;

@Data
@Entity
public class UserRegistration {
	
@Id
@GeneratedValue(strategy = GenerationType.IDENTITY)
@Column(name = "USER_REG_ID")
private Long userRegId;

@Column(name = "NAME")
private String name;

@Column(name="EMAIL_ID")
private String email;

@Column(name="MOBILE_NUMBER")
private Long mobileNumber;

@Column(name = "DATE_OF_BIRTH")
private LocalDate dob;

@Column(name = "SSN_ID")
private Integer ssnNo;

@Column(name = "PASSWORD")
private String password;

@Column(name = "ACTIVE_SW")
private String activeSw;

@Column(name="CREATED_BY")
private String createdBy;


@Column(name = "UPDATED_BY")
private String udatedBy;


@Column(name = "CREATED_DATE",updatable = false)
@CreationTimestamp
private LocalDate createdDate;



@Column(name = "UPDATED_DATE",insertable = false)
@UpdateTimestamp
private LocalDate updatedDate;

}
