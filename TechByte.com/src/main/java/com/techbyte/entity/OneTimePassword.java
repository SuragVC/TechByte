package com.techbyte.entity;

import java.time.LocalTime;

import javax.persistence.Entity;
import javax.persistence.Id;

import lombok.Data;

@Entity
@Data
public class OneTimePassword {
	@Id
	private Integer otpId;
	private Integer OTP;
	private LocalTime openTime;
	private String status;
}
