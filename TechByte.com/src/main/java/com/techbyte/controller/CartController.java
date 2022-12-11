package com.techbyte.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.techbyte.entity.Product;
import com.techbyte.exception.LoginException;
import com.techbyte.exception.ProductException;
import com.techbyte.services.CartServices;

@RestController
@RequestMapping("/cart")
public class CartController {
	private CartServices cartService;

	public CartServices getCartService() {
		return cartService;
	}
	@Autowired
	public void setCartService(CartServices cartService) {
		this.cartService = cartService;
	}
	@GetMapping("add/{key}/{productID}")
	public ResponseEntity<String>addProductToCart(@RequestParam String key,@RequestParam Integer productID) throws ProductException, LoginException{
		String ans = cartService.addProductToCart(key, productID);
		return new ResponseEntity<String>(ans,HttpStatus.ACCEPTED);
	}
	@GetMapping("all/{key}")
	public ResponseEntity<List<Product>>getProductsFromCart(@RequestParam String key) throws ProductException, LoginException{
		List<Product>list = cartService.getProductfromCart(key);
		return new ResponseEntity<List<Product>>(list,HttpStatus.ACCEPTED);
	}
	@DeleteMapping("delete/{key}")
	public ResponseEntity<String>deleteProductsFromCart(@RequestParam String key,@RequestParam Integer productID) throws ProductException, LoginException{
		String ans = cartService.removeProductFromList(key, productID);
		return new ResponseEntity<String>(ans,HttpStatus.ACCEPTED);
	}
}
