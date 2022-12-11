package com.techbyte.services;

import com.techbyte.entity.Cart;
import com.techbyte.entity.Category;
import com.techbyte.exception.CategoryException;
import com.techbyte.exception.LoginException;

public interface CategoryServices {
	public Category createCategory(String adminKey,Category category)throws CategoryException,LoginException;
}
