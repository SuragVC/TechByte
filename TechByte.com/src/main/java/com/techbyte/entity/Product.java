package com.techbyte.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import org.hibernate.annotations.Type;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Product {
	@Id
	private Integer productId;
	private String productName;
	private String image;
	private String brand;
	
	private Double price;
	@JsonIgnore
	private Double rating;
	@JsonIgnore
	private Integer upRatedPeopleCount;
	@JsonIgnore
	private Integer downRatedPeopleCount;
	@NotBlank
	@NotNull
	private String category;
	
	public Product(Integer productId,String productName,String image,String brand,Double price,Double rating) {
		this.productId=productId;
		this.productName=productName;
		this.image=image;
		this.brand=brand;
		
		this.price=price;
		this.rating=rating;
	}
}
