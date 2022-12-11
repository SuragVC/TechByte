package com.techbyte.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.techbyte.entity.Product;
import com.techbyte.entity.ProductsForFrontEnd;
import com.techbyte.exception.LoginException;
import com.techbyte.exception.ProductException;
import com.techbyte.services.ProductServices;

@RestController
@RequestMapping("/product")
public class ProductController {
	private ProductServices productServices;

	public ProductServices getProductServices() {
		return productServices;
	}
	@Autowired
	public void setProductServices(ProductServices productServices) {
		this.productServices = productServices;
	}
	@PostMapping("/add/{adminKey}")
	public ResponseEntity<Product>addProduct(@RequestParam String adminKey,@RequestBody Product product) throws ProductException,LoginException{
		System.out.println(product.toString());
		Product savedProduct = productServices.addProduct(adminKey,product);
		return new ResponseEntity<Product>(savedProduct,HttpStatus.CREATED);
	}
	@GetMapping("/all")
	public ResponseEntity<List<Product>>getAllProduct() throws ProductException,LoginException{
		List<Product>listOfProduct = productServices.getAll();
		return new ResponseEntity<List<Product>>(listOfProduct,HttpStatus.OK);
	}
	@GetMapping("/all/products")
	public ResponseEntity<List<ProductsForFrontEnd>>getAllProductsForFrontEnd() throws ProductException,LoginException{
		List<ProductsForFrontEnd>listOfProductsForFrontEnd = productServices.productForFrontEnd();
		return new ResponseEntity<List<ProductsForFrontEnd>>(listOfProductsForFrontEnd,HttpStatus.OK);
	}
	
	@GetMapping("/uprate/{stringKey}/{productId}")
	public ResponseEntity<Product>upRateAProduct(@RequestParam String userKey,@RequestParam Integer productId) throws ProductException,LoginException{
		Product ratedProduct = productServices.upRateAproduct(userKey, productId);
		return new ResponseEntity<Product>(ratedProduct,HttpStatus.OK);
	}
	@GetMapping("/downrate/{stringKey}/{productId}")
	public ResponseEntity<Product>downRateAProduct(@RequestParam String userKey,@RequestParam Integer productId) throws ProductException,LoginException{
		Product ratedProduct = productServices.downRateAproduct(userKey, productId);
		return new ResponseEntity<Product>(ratedProduct,HttpStatus.OK);
	}
	@GetMapping("/all/upratedList")
	public ResponseEntity<List<Product>>topRatedProductsList() throws ProductException,LoginException{
		List<Product>listOfProduct = productServices.topRatedProducts();
		return new ResponseEntity<List<Product>>(listOfProduct,HttpStatus.OK);
	}
	@GetMapping("/all/brand")
	public ResponseEntity<List<Product>>getProductsByBrand(@RequestParam String brand) throws ProductException,LoginException{
		List<Product>listOfProduct = productServices.getProductsByBrand(brand);
		return new ResponseEntity<List<Product>>(listOfProduct,HttpStatus.OK);
	}
	@GetMapping("/all/topfive")
	public ResponseEntity<List<Product>>getTopFiveProductsByPrice() throws ProductException,LoginException{
		List<Product>listOfProduct = productServices.topFiveHighPricedProducts();
		return new ResponseEntity<List<Product>>(listOfProduct,HttpStatus.OK);
	}
}
