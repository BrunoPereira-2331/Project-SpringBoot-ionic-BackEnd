package com.bruno.projectMc.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bruno.projectMc.domain.State;
import com.bruno.projectMc.repositories.StateRepository;

@Service
public class StateService {

	@Autowired
	private StateRepository repoState;

	public List<State> findAll() {
		return repoState.findAllByOrderByName();
	}

}
