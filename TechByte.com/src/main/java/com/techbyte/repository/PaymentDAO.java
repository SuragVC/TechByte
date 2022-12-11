package com.techbyte.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.techbyte.entity.Payment;

public interface PaymentDAO extends JpaRepository<Payment, Integer>{

}
