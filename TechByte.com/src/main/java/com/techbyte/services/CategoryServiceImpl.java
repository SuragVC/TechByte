package com.techbyte.services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.techbyte.entity.Cart;
import com.techbyte.entity.Category;
import com.techbyte.entity.CurrentSessionAdmin;
import com.techbyte.entity.RandomIdGenerator;
import com.techbyte.exception.CategoryException;
import com.techbyte.exception.LoginException;
import com.techbyte.repository.AdminSessionDAO;
import com.techbyte.repository.CategoryDAO;

@Service
public class CategoryServiceImpl implements CategoryServices{
	private CategoryDAO categoryDao;
	private AdminSessionDAO adminDao;
	@Autowired
	public CategoryServiceImpl(CategoryDAO categoryDao,AdminSessionDAO adminDao) {
		this.categoryDao=categoryDao;
		this.adminDao=adminDao;
	}
	@Override
	public Category createCategory(String adminKey, Category category) throws CategoryException,LoginException {
		Optional<CurrentSessionAdmin> admin= adminDao.findByUuid(adminKey);
		if(admin.isEmpty()) {
			throw new LoginException("Admin needs to login for adding category!");
		}
		Optional<Category> categoryOpt=categoryDao.findByCategory(category.getCategory());
		if(categoryOpt.isPresent()) {
			throw new CategoryException("Category Already Exsists!");
		}
		category.setCategory_Id(RandomIdGenerator.getRandomInteger());
		return categoryDao.save(category);
	}
	
}
