package com.techbyte.services;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.techbyte.entity.CategorisedProduct;
import com.techbyte.entity.Category;
import com.techbyte.entity.CurrentSessionAdmin;
import com.techbyte.entity.CurrentSessionUser;
import com.techbyte.entity.Product;
import com.techbyte.entity.ProductsForFrontEnd;
import com.techbyte.entity.RandomIdGenerator;
import com.techbyte.exception.LoginException;
import com.techbyte.exception.ProductException;
import com.techbyte.repository.AdminSessionDAO;
import com.techbyte.repository.CategoryDAO;
import com.techbyte.repository.ProductDAO;
import com.techbyte.repository.SessionDAO;

@Service
public class ProductServiceImpl implements ProductServices{
	private ProductDAO productDao;
	private AdminSessionDAO adminDao;
	private CategoryDAO categoryDao;
	private SessionDAO sessionDao;
	@Autowired
	public void setSessionDao(SessionDAO sessionDao) {
		this.sessionDao = sessionDao;
	}
	@Autowired
	public void setCategoryDao(CategoryDAO categoryDao) {
		this.categoryDao = categoryDao;
	}
	public AdminSessionDAO getAdminDao() {
		return adminDao;
	}
	@Autowired
	public void setAdminDao(AdminSessionDAO adminDao) {
		this.adminDao = adminDao;
	}
	public ProductDAO getProductDao() {
		return productDao;
	}
	@Autowired
	public void setProductDao(ProductDAO productDao) {
		this.productDao = productDao;
	}
	@Override
	public Product addProduct(String adminKey,Product product) throws ProductException,LoginException {
		Optional<CurrentSessionAdmin> admin= adminDao.findByUuid(adminKey);
		if(admin.isEmpty()){
			throw new LoginException("Admin needs to login for adding product");
		}
		Optional<Product> productOpt = productDao.findById(product.getProductId());
		if(productOpt.isPresent()) {
			throw new ProductException("Product Already in the Database");
		}
		Optional<Category> category=categoryDao.findByCategory(product.getCategory());
		if(category.isEmpty()) {
			throw new ProductException("There is no category registered as "+product.getCategory());
		}
		product.setProductId(RandomIdGenerator.getRandomProductId());
		product.setRating(1.0);
		product.setUpRatedPeopleCount(1);
		product.setDownRatedPeopleCount(1);
		return productDao.save(product);
	}
	@Override
	public List<Product> getAll() throws ProductException {
		List<Product>list=productDao.findAll();
		return list;
	}
	@Override
	public List<Product> getAllProductByCategory(String category) throws ProductException {
		Optional<Category> categoryOpt=categoryDao.findByCategory(category);
		if(categoryOpt.isEmpty()) {
			throw new ProductException("There is no category as "+category);
		}
		List<Product>list=productDao.getProductByCategory(category);
		return list;
	}
	@Override
	public Product upRateAproduct(String key, Integer productId) throws ProductException,LoginException {
		Optional<CurrentSessionUser> user= sessionDao.findByUuid(key);
		if(user.isEmpty()) {
			throw new LoginException("User needs to login to rate a product!");
		}
		Optional<Product> productOpt = productDao.findById(productId);
		if(productOpt.isEmpty()) {
			throw new ProductException("Product not found with id "+productId);
		}
		Product product=productOpt.get();
		product.setRating(product.getRating()+1);
		product.setUpRatedPeopleCount(product.getUpRatedPeopleCount()+1);
		return productDao.save(product);
	}
	@Override
	public Product downRateAproduct(String key, Integer productId) throws ProductException, LoginException {
		Optional<CurrentSessionUser> user= sessionDao.findByUuid(key);
		if(user.isEmpty()) {
			throw new LoginException("User needs to login to rate a product!");
		}
		Optional<Product> productOpt = productDao.findById(productId);
		if(productOpt.isEmpty()) {
			throw new ProductException("Product not found with id "+productId);
		}
		Product product=productOpt.get();
		product.setRating(product.getRating()-1);
		product.setDownRatedPeopleCount(product.getDownRatedPeopleCount()+1);
		return productDao.save(product);
	}
	@Override
	public List<Product> topRatedProducts() {
		List<Product>list=productDao.topRatedProducts();
		return list;
	}
	@Override
	public List<Product> getProductsByBrand(String brand) throws ProductException {
		List<Product>list=productDao.getProductsByBrand(brand);
		if(list.isEmpty()) {
			throw new ProductException("There is no product with brand as "+brand);
		}
		return list;
	}
	@Override
	public List<Product> topFiveHighPricedProducts() throws ProductException {
		List<Product>list=productDao.getHighPricedFiveProducts();
		return list;
	}
	@Override
	public List<ProductsForFrontEnd> productForFrontEnd() throws ProductException {
		List<Product>list=productDao.findAll();
		List<ProductsForFrontEnd>returnList=new ArrayList<>();
		for(int i=0;i<list.size();i++) {
			ProductsForFrontEnd productsForFrontEnd=new ProductsForFrontEnd();
			productsForFrontEnd.setProduct_name(list.get(i).getProductName());
			productsForFrontEnd.setThumbnail(list.get(i).getImage());
			returnList.add(productsForFrontEnd);
		}
		return returnList;
	}
	
	
}
