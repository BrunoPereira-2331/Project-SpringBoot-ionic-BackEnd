package com.bruno.projectMc.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.bruno.projectMc.domain.State;

@Repository
public interface StateRepository extends JpaRepository<State, Integer>{

	@Transactional(readOnly=true)
	public List<State> findAllByOrderByName();
}
