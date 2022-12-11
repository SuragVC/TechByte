package com.techbyte.entity;

import java.time.LocalDateTime;
import java.util.Optional;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Data;

@Entity
public class CurrentSessionUser {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Integer id;
	
	private String uuid;
	
	private String mobileNo;
	
	private LocalDateTime localDateTime;
	
	@JsonIgnore
	private Integer userId;

	public Integer getUserId() {
		return userId;
	}



	public void setUserId(Integer userId) {
		this.userId = userId;
	}



	public CurrentSessionUser() {
		super();
		// TODO Auto-generated constructor stub
	}



	public CurrentSessionUser( String uuid, String mobileNo,LocalDateTime localDateTime) {
		super();
		this.uuid = uuid;
		this.mobileNo = mobileNo;
		this.localDateTime = localDateTime;
	}



	public Integer getId() {
		return id;
	}



	public void setId(Integer id) {
		this.id = id;
	}

	public String getUuid() {
		return uuid;
	}



	public void setUuid(String uuid) {
		this.uuid = uuid;
	}



	public String getMobileNo() {
		return mobileNo;
	}



	public void setMobileNo(String mobileNo) {
		this.mobileNo = mobileNo;
	}



	public LocalDateTime getLocalDateTime() {
		return localDateTime;
	}



	public void setLocalDateTime(LocalDateTime localDateTime) {
		this.localDateTime = localDateTime;
	}



	@Override
	public String toString() {
		return uuid;
	}
	
	

	
}
