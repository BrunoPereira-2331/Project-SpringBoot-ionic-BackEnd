package com.bruno.projectMc.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.bruno.projectMc.domain.Order;

@Repository
public interface OrderRepository extends JpaRepository<Order, Integer>{

}
