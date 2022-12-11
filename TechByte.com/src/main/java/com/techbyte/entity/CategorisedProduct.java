package com.techbyte.entity;

import lombok.Data;

@Data
public class CategorisedProduct {
	private Integer productId;
	private String productName;
	private String image;
	private String brand;
	private Integer stock;
	private Double price;
	private Double rating;
	private Integer category_category_id;
	private Integer category_id;
	private String category;
}
