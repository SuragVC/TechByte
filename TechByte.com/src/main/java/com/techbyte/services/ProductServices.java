package com.techbyte.services;

import java.util.List;

import com.techbyte.entity.Product;
import com.techbyte.entity.ProductsForFrontEnd;
import com.techbyte.exception.LoginException;
import com.techbyte.exception.ProductException;

public interface ProductServices {
	public Product addProduct(String adminKey,Product product)throws ProductException,LoginException;
	public List<Product>getAll()throws ProductException;
	public List<Product>getAllProductByCategory(String category)throws ProductException;
	public Product upRateAproduct(String key,Integer productId)throws ProductException,LoginException;
	public Product downRateAproduct(String key,Integer productId)throws ProductException,LoginException;
	public List<Product>topRatedProducts();
	public List<Product>getProductsByBrand(String brand)throws ProductException;
	public List<Product>topFiveHighPricedProducts()throws ProductException;
	public List<ProductsForFrontEnd>productForFrontEnd()throws ProductException;
}
