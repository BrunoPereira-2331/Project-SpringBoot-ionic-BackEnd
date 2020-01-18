package com.bruno.projectMc.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.bruno.projectMc.domain.Category;
import com.bruno.projectMc.domain.Product;

@Repository
public interface ProductRepository extends JpaRepository<Product, Integer>{

}
