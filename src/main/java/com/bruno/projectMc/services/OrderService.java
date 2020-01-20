package com.bruno.projectMc.services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bruno.projectMc.domain.Order;
import com.bruno.projectMc.repositories.OrderRepository;
import com.bruno.projectMc.services.exceptions.ObjectNotFoundException;

@Service
public class OrderService{

	@Autowired
	private OrderRepository repo;
	
	public Order find(Integer id) {
		Optional<Order> obj = repo.findById(id);
		return obj.orElseThrow(() -> new ObjectNotFoundException(
		"Objeto não encontrado! Id: " + id + ", Tipo: " + Order.class.getName()));
		}
}
