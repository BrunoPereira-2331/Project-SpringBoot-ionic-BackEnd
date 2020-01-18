package com.bruno.projectMc.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.bruno.projectMc.domain.State;

@Repository
public interface StateRepository extends JpaRepository<State, Integer>{

}
