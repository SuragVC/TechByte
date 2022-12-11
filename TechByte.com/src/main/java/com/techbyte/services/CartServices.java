package com.techbyte.services;

import java.util.List;

import com.techbyte.entity.Product;
import com.techbyte.exception.LoginException;
import com.techbyte.exception.ProductException;

public interface CartServices {
	public List<Product>getProductfromCart(String key)throws ProductException;
	public String addProductToCart(String key,Integer productID)throws ProductException,LoginException;
	public String removeProductFromList(String key,Integer productID)throws ProductException,LoginException;
}
