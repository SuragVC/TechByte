package com.techbyte.entity;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Data;

@Entity
@Data
@Table(name = "Customer_Order")
public class Order {
	@Id
	private Integer orderId;
	@JsonIgnore
	private LocalDate date;
	@JsonIgnore
	private LocalTime time;
	private Integer product_Id_1;
	private Integer product_1_qty;
	private Integer product_Id_2;
 	private Integer product_2_qty;
	private Integer product_id_3;
	private Integer product_3_qty;
	private Double totalOrderAmount;
	@OneToOne
	private Payment payment;
	@OneToOne
	private Address address;
	private OrderStatus orderStatus;
	
}
