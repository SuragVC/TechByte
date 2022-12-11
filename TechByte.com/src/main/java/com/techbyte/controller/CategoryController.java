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

import com.techbyte.entity.Category;
import com.techbyte.entity.Product;
import com.techbyte.exception.CategoryException;
import com.techbyte.exception.LoginException;
import com.techbyte.exception.ProductException;
import com.techbyte.services.CategoryServices;
import com.techbyte.services.ProductServices;

@RestController
@RequestMapping("/category")
public class CategoryController {
	private ProductServices productServices;
	private CategoryServices categoryServices;
	@Autowired
	public CategoryController(CategoryServices categoryServices,ProductServices productServices) {
		this.categoryServices=categoryServices;
		this.productServices=productServices;
	}
	@PostMapping("/create/{adminKey}")
	public ResponseEntity<Category>createCategory(@RequestParam String adminKey,@RequestBody Category category) throws CategoryException, LoginException{
		Category savedCategory=categoryServices.createCategory(adminKey, category);
		return new ResponseEntity<Category>(savedCategory,HttpStatus.CREATED);
				
	}
	@GetMapping("/cat/{category}")
	public ResponseEntity<List<Product>>getProductByCategory(@RequestParam String category) throws ProductException,LoginException{
		List<Product>listOfProduct = productServices.getAllProductByCategory(category);
		return new ResponseEntity<List<Product>>(listOfProduct,HttpStatus.OK);
	}
}
