package com.techbyte.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.techbyte.entity.Admin;


public interface AdminDAO extends JpaRepository<Admin, Integer> {
	public Optional<Admin> findByAdminName(String userName);
	public Optional<Admin>findByMobileNo(String mobileNo);
}
