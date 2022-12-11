package com.techbyte.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.techbyte.entity.Category;
import com.techbyte.entity.Product;

public interface CategoryDAO extends JpaRepository<Category, Integer> {
	Optional<Category> findByCategory(String category);
}
