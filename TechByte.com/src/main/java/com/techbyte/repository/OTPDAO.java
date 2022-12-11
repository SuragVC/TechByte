package com.techbyte.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.techbyte.entity.OneTimePassword;

public interface OTPDAO extends JpaRepository<OneTimePassword, Integer>{

}
