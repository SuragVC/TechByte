package com.techbyte.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.techbyte.entity.CurrentSessionAdmin;




@Repository
public interface AdminSessionDAO extends JpaRepository<CurrentSessionAdmin, Integer>{
	public Optional<CurrentSessionAdmin> findByAdminId(Integer adminId);
	
	public Optional<CurrentSessionAdmin> findByUuid(String uuid);
	
	public Optional<CurrentSessionAdmin> findByMobileNo(String uuid);
	
	
	
}
