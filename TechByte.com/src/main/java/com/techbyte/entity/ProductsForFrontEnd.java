package com.techbyte.entity;

import org.springframework.stereotype.Component;

import lombok.Data;

@Component
@Data
public class ProductsForFrontEnd {
	private String thumbnail;
	private String product_name;
}
