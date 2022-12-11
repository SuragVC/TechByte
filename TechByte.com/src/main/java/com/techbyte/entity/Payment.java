package com.techbyte.entity;

import javax.persistence.Entity;
import javax.persistence.Id;

import lombok.Data;

@Entity
@Data
public class Payment {
	@Id
	private Integer paymentId;
	private Double paymentPrice;
	private Double totalCostForProducts;
	private Double returnToCustomer;
}
