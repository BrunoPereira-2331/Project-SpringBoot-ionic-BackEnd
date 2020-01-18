package com.bruno.projectMc;

import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.bruno.projectMc.domain.Adress;
import com.bruno.projectMc.domain.Category;
import com.bruno.projectMc.domain.City;
import com.bruno.projectMc.domain.Client;
import com.bruno.projectMc.domain.Product;
import com.bruno.projectMc.domain.State;
import com.bruno.projectMc.domain.enums.ClientType;
import com.bruno.projectMc.repositories.AdressRepository;
import com.bruno.projectMc.repositories.CategoryRepository;
import com.bruno.projectMc.repositories.CityRepository;
import com.bruno.projectMc.repositories.ClientRepository;
import com.bruno.projectMc.repositories.ProductRepository;
import com.bruno.projectMc.repositories.StateRepository;

@SpringBootApplication
public class ProjectMcApplication implements CommandLineRunner {

	@Autowired
	private CategoryRepository catRepository;

	@Autowired
	private ProductRepository prodRepository;

	@Autowired
	private StateRepository stateRepository;

	@Autowired
	private CityRepository cityRepository;

	@Autowired
	private ClientRepository cliRepository;

	@Autowired
	private AdressRepository adressRepository;

	public static void main(String[] args) {
		SpringApplication.run(ProjectMcApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		Category cat1 = new Category(null, "informatica");
		Category cat2 = new Category(null, "escritorio");

		Product p1 = new Product(null, "computador", 2000.00);
		Product p2 = new Product(null, "impressora", 800.00);
		Product p3 = new Product(null, "mouse", 80.00);

		cat1.getProducts().addAll(Arrays.asList(p1, p2, p3));
		cat2.getProducts().addAll(Arrays.asList(p2));

		p1.getCategories().addAll(Arrays.asList(cat1));
		p2.getCategories().addAll(Arrays.asList(cat1, cat2));
		p3.getCategories().addAll(Arrays.asList(cat1));

		catRepository.saveAll(Arrays.asList(cat1, cat2));
		prodRepository.saveAll(Arrays.asList(p1, p2, p3));

		State state1 = new State(null, "Minas Gerais");
		State state2 = new State(null, "São Paulo");

		City c1 = new City(null, "Uberlandia", state1);
		City c2 = new City(null, "São Paulo", state2);
		City c3 = new City(null, "Campinas", state2);

		state1.getCities().addAll(Arrays.asList(c1));
		state2.getCities().addAll(Arrays.asList(c2, c3));

		stateRepository.saveAll(Arrays.asList(state1, state2));

		cityRepository.saveAll(Arrays.asList(c1, c2, c3));

		Client cli1 = new Client(null, "Maria Silva", "maria@gmail.com", "36378912377", ClientType.PESSOAFISICA);

		cli1.getPhones().addAll(Arrays.asList("21334261", "99328452"));
		Adress adress = new Adress(null, "Rua Flores", "300", "Apto 303", "Jardim", "38220834", cli1, c1);
		Adress adress1 = new Adress(null, "Avenida Matos", "105", "Sala 800", "Centro", "38777012", cli1, c2);

		cli1.getAdresses().addAll(Arrays.asList(adress, adress1));
		
		cliRepository.saveAll(Arrays.asList(cli1));
		adressRepository.saveAll(Arrays.asList(adress, adress1));

		
	}

}
