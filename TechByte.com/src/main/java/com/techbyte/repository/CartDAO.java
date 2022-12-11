package com.techbyte.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.techbyte.entity.Cart;

public interface CartDAO extends JpaRepository<Cart, Integer>{
	
	@Query(value="select * from cart_products where cart_cart_id=?1 and products_product_id=?2",nativeQuery = true)
	public Cart checkProductInAlreadyInCart(Integer cartid,Integer products_product_id);
	@Query(value="delete from cart_products  where cart_cart_id=?1 and products_product_id=?2",nativeQuery = true)
	public Cart deleteProductFromCart(Integer cartid,Integer products_product_id);
}
