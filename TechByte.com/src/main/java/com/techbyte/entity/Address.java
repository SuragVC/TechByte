package com.techbyte.entity;

import javax.persistence.Entity;
import javax.persistence.Id;

import lombok.Data;

@Entity
@Data
public class Address {
	@Id
	private Integer addressId;
	private String address;
	private String city;
	private String pincode;
	private String state;
	
}
