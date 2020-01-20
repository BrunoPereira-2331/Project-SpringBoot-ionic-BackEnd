package com.bruno.projectMc.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.bruno.projectMc.domain.Payment;

@Repository
public interface PaymentRepository extends JpaRepository<Payment, Integer>{

}
