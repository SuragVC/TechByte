package com.techbyte.repository;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.techbyte.entity.User;

@Repository
public interface UserDAO extends JpaRepository<User, Integer>{
	
	public Optional<User> findByUserName(String userName);
	
	Optional<User> findByMobileNo(String mobileNo);
	Optional<User> findByEmail(String email);
	
}
