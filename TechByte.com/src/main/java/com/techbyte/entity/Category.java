package com.techbyte.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

import lombok.Data;

@Entity
@Data

public class Category {
	@Id
	@Column(unique = true)
	private Integer category_Id;
	private String category;
}
