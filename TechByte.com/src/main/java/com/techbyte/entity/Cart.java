package com.techbyte.entity;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import lombok.Data;

@Entity
@Data
public class Cart {
	@Id
	private Integer cartId;
	private String userName;
	@ManyToMany(cascade = CascadeType.ALL,fetch = FetchType.LAZY)
	List<Product> products=new ArrayList<>();
}
