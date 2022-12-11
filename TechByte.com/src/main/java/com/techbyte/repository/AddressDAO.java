package com.techbyte.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.techbyte.entity.Address;


public interface AddressDAO extends JpaRepository<Address, Integer> {

}
