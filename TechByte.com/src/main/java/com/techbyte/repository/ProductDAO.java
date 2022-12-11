package com.techbyte.repository;



import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.techbyte.entity.Product;

public interface ProductDAO extends JpaRepository<Product, Integer> {
	@Query(value="select * from product order by rating desc",nativeQuery = true)
	public List<Product>topRatedProducts();
	@Query(value="select * from product where Category=?",nativeQuery = true)
	public List<Product>getProductByCategory(String category);
	@Query(value="select * from product where brand=?",nativeQuery = true)
	public List<Product>getProductsByBrand(String brand);
	@Query(value="select * from product order by price desc limit 5",nativeQuery = true)
	public List<Product>getHighPricedFiveProducts();
}
;