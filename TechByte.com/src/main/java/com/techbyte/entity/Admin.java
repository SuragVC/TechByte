package com.techbyte.entity;


import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.Email;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Data
public class Admin {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Integer id;
	@NotNull
	private Integer adminId;

	@NotNull
	@Pattern(regexp="[a-zA-Z ]{3,12}", message = "User Name must not contains any numbers and Special Character")
	private String adminName;
	

	@NotNull
	@Size(min=10,max=10)
	@Pattern(regexp="[6-9]{1}[0-9]{9}", message = "Mobile number must have 10 digits mobile Number")
	private String mobileNo;
	
	@NotNull
	@Pattern(regexp="[a-zA-Z0-9]{6,12}",message="Password must contain between 6 to 12 characters.")
	private String password;
	@Email
	private String email;
	
}
