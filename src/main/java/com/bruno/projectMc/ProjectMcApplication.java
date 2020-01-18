package com.bruno.projectMc;

import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.bruno.projectMc.domain.Category;
import com.bruno.projectMc.domain.City;
import com.bruno.projectMc.domain.Product;
import com.bruno.projectMc.domain.State;
import com.bruno.projectMc.repositories.CategoryRepository;
import com.bruno.projectMc.repositories.CityRepository;
import com.bruno.projectMc.repositories.ProductRepository;
import com.bruno.projectMc.repositories.StateRepository;

@SpringBootApplication
public class ProjectMcApplication implements CommandLineRunner{

	@Autowired
	private CategoryRepository catRepository;
	
	@Autowired
	private ProductRepository prodRepository;
	
	@Autowired
	private StateRepository stateRepository;
	
	@Autowired
	private CityRepository cityRepository;
	
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
	
	}

}
