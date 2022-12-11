package com.techbyte.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.techbyte.entity.AdminLogIn;




public interface AdminLogInDAO extends JpaRepository< AdminLogIn, Integer>{
	public Optional<AdminLogIn> findByAdminId(Integer adminId);
}
