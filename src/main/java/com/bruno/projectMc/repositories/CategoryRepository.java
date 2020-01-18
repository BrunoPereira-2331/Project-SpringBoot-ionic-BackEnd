package com.bruno.projectMc.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.bruno.projectMc.domain.Category;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Integer>{

}
