package com.bruno.projectMc.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;

import com.bruno.projectMc.domain.Adress;
import com.bruno.projectMc.domain.City;
import com.bruno.projectMc.domain.Client;
import com.bruno.projectMc.domain.enums.ClientType;
import com.bruno.projectMc.dto.ClientDTO;
import com.bruno.projectMc.dto.ClientNewDTO;
import com.bruno.projectMc.repositories.AdressRepository;
import com.bruno.projectMc.repositories.ClientRepository;
import com.bruno.projectMc.services.exceptions.DataIntegrityException;
import com.bruno.projectMc.services.exceptions.ObjectNotFoundException;

@Service
public class ClientService {

	@Autowired
	private ClientRepository repo;
	
	@Autowired
	private AdressRepository repoAdress;
	

	public Client find(Integer id) {
		Optional<Client> obj = repo.findById(id);
		return obj.orElseThrow(() -> new ObjectNotFoundException(
				"Objeto não encontrado! Id: " + id + ", Tipo: " + Client.class.getName()));
	}
	
	public Client Insert(Client obj) {
		obj.setId(null);
		obj = repo.save(obj);
		repoAdress.saveAll(obj.getAdresses());
		return obj;
	}

	public Client update(Client obj) {
		Client newObj = find(obj.getId());
		updateData(newObj, obj);
		return repo.save(newObj);
	}

	public void delete(Integer id) {
		find(id);
		try {
			repo.deleteById(id);
		} catch (DataIntegrityViolationException e) {
			throw new DataIntegrityException("Não é possivel excluir um cliente que possui pedidos");
		}
	}

	public List<Client> findAll() {
		return repo.findAll();
	}

	public Page<Client> findPage(Integer page, Integer linesPerPage, String orderBy, String direction) {
		PageRequest pageRequest = PageRequest.of(page, linesPerPage, Direction.valueOf(direction), orderBy);
		return repo.findAll(pageRequest);
	}

	public Client fromDTO(ClientDTO objDto) {
		return new Client(objDto.getId(), objDto.getName(), objDto.getEmail(), null, null);
	}
	
	public Client fromDTO(ClientNewDTO objDto) {
		Client cli = new Client(null, objDto.getName(), objDto.getEmail(), objDto.getCpfOrCnpj(), ClientType.toEnum(objDto.getType()));
		City city = new City(objDto.getCityId(), null, null);
		Adress adress = new Adress(null, objDto.getLogradouro(), objDto.getNumber(), objDto.getComplement(), objDto.getNeighborhood(), objDto.getZipCode(), cli, city);
		cli.getAdresses().add(adress);
		cli.getPhones().add(objDto.getPhone1());
		if (objDto.getPhone2() != null) {
			cli.getPhones().add(objDto.getPhone2());
		}
		if (objDto.getPhone3() != null) {
			cli.getPhones().add(objDto.getPhone3());
		}
		return cli;
	}
	
	private void updateData(Client newObj, Client obj) {
		newObj.setName(obj.getName());
		newObj.setEmail(obj.getEmail());
	}
}
 