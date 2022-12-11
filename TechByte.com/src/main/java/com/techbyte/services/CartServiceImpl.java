package com.techbyte.services;

import java.util.List;
import java.util.Optional;

import org.hibernate.exception.SQLGrammarException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.InvalidDataAccessResourceUsageException;
import org.springframework.stereotype.Service;

import com.techbyte.entity.Cart;
import com.techbyte.entity.CurrentSessionUser;
import com.techbyte.entity.Product;
import com.techbyte.entity.User;
import com.techbyte.exception.LoginException;
import com.techbyte.exception.ProductException;
import com.techbyte.repository.CartDAO;
import com.techbyte.repository.ProductDAO;
import com.techbyte.repository.SessionDAO;
import com.techbyte.repository.UserDAO;

@Service
public class CartServiceImpl implements CartServices{
		private CartDAO cartDao;
		private UserDAO userDao;
		private ProductDAO productDao;
		private SessionDAO sessionDao;

		
		public SessionDAO getSessionDao() {
			return sessionDao;
		}
		@Autowired
		public void setSessionDao(SessionDAO sessionDao) {
			this.sessionDao = sessionDao;
		}
		public ProductDAO getProductDao() {
			return productDao;
		}
		@Autowired
		public void setProductDao(ProductDAO productDao) {
			this.productDao = productDao;
		}
		public UserDAO getUserDao() {
			return userDao;
		}
		@Autowired
		public void setUserDao(UserDAO userDao) {
			this.userDao = userDao;
		}
		public CartDAO getCartDao() {
			return cartDao;
		}
		@Autowired
		public void setCartDao(CartDAO cartDao) {
			this.cartDao = cartDao;
		}
		@Override
		public List<Product> getProductfromCart(String key) throws ProductException{
			Optional<CurrentSessionUser> currentUser=  sessionDao.findByUuid(key);
			
			Integer userID=currentUser.get().getUserId();
			Optional<User> user=userDao.findById(userID);
			Cart cart =user.get().getCart();
			List<Product>productList=cart.getProducts();
			if(productList.size()==0) {
				throw new ProductException("Cart is empty");
			}
			return productList;
		}
		@Override
		public String addProductToCart(String key,Integer productID) throws ProductException,LoginException{
			Optional<CurrentSessionUser> currentUser=  sessionDao.findByUuid(key);
			if(currentUser.isEmpty()){
				throw new LoginException("User Needs to login");
			}
			Integer userID=currentUser.get().getUserId();
			Optional<User> user=userDao.findById(userID);
			Cart cart =user.get().getCart();
			List<Product>productList=cart.getProducts();
			Cart cart2=null;
			try {
				cart2=cartDao.checkProductInAlreadyInCart(cart.getCartId(), productID);			
			}catch(InvalidDataAccessResourceUsageException e) {
				throw new ProductException("Product Already in the cart");
			}
			if(cart2!=null) {
				throw new ProductException("Product Already in the cart 2");
			}
			Optional<Product> productOpt=productDao.findById(productID);
			if(productOpt.isEmpty()) {
				throw new ProductException("Product Id is wrong");
			}
			productList.add(productOpt.get());
			cartDao.save(cart);
			return "Product Added to the Cart";
		}
		@Override
		public String removeProductFromList(String key,Integer productID) throws ProductException,LoginException {
			Optional<CurrentSessionUser> currentUser=  sessionDao.findByUuid(key);
			if(currentUser.isEmpty()){
				throw new LoginException("User Needs to login");
			}
			Integer userID=currentUser.get().getUserId();
			Optional<User> user=userDao.findById(userID);
			Cart cart =user.get().getCart();
			List<Product>productList=cart.getProducts();
			Product product=null;
			for(Product p:productList) {
				System.out.println(p.toString());
				int p_id=p.getProductId();
				if(p_id==productID) {
					product=p;
					break;
				}
			}
			if(product==null) {
				throw new ProductException("Product is not in the cart");
			}
			productList.remove(product);
			cartDao.save(cart);
			return "Product Removed from cart";
		}
		
		
}
