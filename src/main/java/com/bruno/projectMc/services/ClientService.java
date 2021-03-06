package com.bruno.projectMc.services;

import java.awt.image.BufferedImage;
import java.net.URI;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.bruno.projectMc.domain.Adress;
import com.bruno.projectMc.domain.City;
import com.bruno.projectMc.domain.Client;
import com.bruno.projectMc.domain.enums.ClientType;
import com.bruno.projectMc.domain.enums.Profile;
import com.bruno.projectMc.dto.ClientDTO;
import com.bruno.projectMc.dto.ClientNewDTO;
import com.bruno.projectMc.repositories.AdressRepository;
import com.bruno.projectMc.repositories.ClientRepository;
import com.bruno.projectMc.security.UserSS;
import com.bruno.projectMc.services.exceptions.AuthorizationException;
import com.bruno.projectMc.services.exceptions.DataIntegrityException;
import com.bruno.projectMc.services.exceptions.ObjectNotFoundException;

@Service
public class ClientService {

	@Autowired
	private BCryptPasswordEncoder pe;

	@Autowired
	private ClientRepository repo;

	@Autowired
	private AdressRepository repoAdress;

	@Autowired
	private S3Service s3Service;

	@Autowired
	private ImageService imageService;

	@Value("${img.prefix.client.profile}")
	private String prefix;

	@Value("${img.profile.size}")
	private Integer size;

	public Client find(Integer id) {
		UserSS user = UserService.authenticated();

		if (user == null || !user.hasRole(Profile.ADMIN) && !id.equals(user.getId())) {
			throw new AuthorizationException("Acesso negado");
		}
		Optional<Client> obj = repo.findById(id);
		return obj.orElseThrow(() -> new ObjectNotFoundException(
				"Objeto não encontrado! Id: " + id + ", Tipo: " + Client.class.getName()));
	}

	@Transactional
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
	
	public Client findByEmail(String email) {
		UserSS user = UserService.authenticated();
		if (user == null || !user.hasRole(Profile.ADMIN) && !email.equals(user.getUsername())) {
			throw new AuthorizationException("Acesso negado");
		}
		Client obj = repo.findByEmail(email);
		if (obj == null) {
			throw new ObjectNotFoundException("Objeto não encontrado! id: " + user.getId());
		}
		return obj;
	}

	public Page<Client> findPage(Integer page, Integer linesPerPage, String orderBy, String direction) {
		PageRequest pageRequest = PageRequest.of(page, linesPerPage, Direction.valueOf(direction), orderBy);
		return repo.findAll(pageRequest);
	}

	public Client fromDTO(ClientDTO objDto) {
		return new Client(objDto.getId(), objDto.getName(), objDto.getEmail(), null, null, null);
	}

	public Client fromDTO(ClientNewDTO objDto) {
		Client cli = new Client(null, objDto.getName(), objDto.getEmail(), objDto.getCpfOrCnpj(),
				ClientType.toEnum(objDto.getType()), pe.encode(objDto.getPassword()));
		City city = new City(objDto.getCityId(), null, null);
		Adress adress = new Adress(null, objDto.getLogradouro(), objDto.getNumber(), objDto.getComplement(),
				objDto.getNeighborhood(), objDto.getZipCode(), cli, city);
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

	public URI uploadProfilePicture(MultipartFile multiPartFile) {
		UserSS user = UserService.authenticated();
		if (user == null) {
			throw new AuthorizationException("Acesso negado");
		}
		BufferedImage jpgImage = imageService.getJpgImageFromFile(multiPartFile);
		jpgImage = imageService.cropSquare(jpgImage);
		jpgImage = imageService.resize(jpgImage, size);

		String fileName = prefix + user.getId() + ".jpg";

		return s3Service.uploadFile(imageService.getInputStream(jpgImage, "jpg"), fileName, "image");
	}
}
