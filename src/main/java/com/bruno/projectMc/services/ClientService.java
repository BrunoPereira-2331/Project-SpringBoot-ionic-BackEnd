package com.bruno.projectMc.services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bruno.projectMc.domain.Client;
import com.bruno.projectMc.repositories.ClientRepository;
import com.bruno.projectMc.services.exceptions.ObjectNotFoundException;

@Service
public class ClientService{

	@Autowired
	private ClientRepository repo;
	
	public Client find(Integer id) {
		Optional<Client> obj = repo.findById(id);
		return obj.orElseThrow(() -> new ObjectNotFoundException(
		"Objeto não encontrado! Id: " + id + ", Tipo: " + Client.class.getName()));
		}
}
