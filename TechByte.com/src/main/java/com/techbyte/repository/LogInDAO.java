package com.techbyte.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import com.techbyte.entity.LogIn;

@Repository
public interface LogInDAO extends JpaRepository<LogIn, Integer>{
	LogIn findByMobileNo(String mobileNo);
}
