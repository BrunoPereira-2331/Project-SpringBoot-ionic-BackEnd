package com.bruno.projectMc;

import java.text.SimpleDateFormat;
import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.bruno.projectMc.domain.Adress;
import com.bruno.projectMc.domain.Category;
import com.bruno.projectMc.domain.City;
import com.bruno.projectMc.domain.Client;
import com.bruno.projectMc.domain.Order;
import com.bruno.projectMc.domain.OrderItem;
import com.bruno.projectMc.domain.Payment;
import com.bruno.projectMc.domain.PaymentBankSlip;
import com.bruno.projectMc.domain.PaymentCreditCard;
import com.bruno.projectMc.domain.Product;
import com.bruno.projectMc.domain.State;
import com.bruno.projectMc.domain.enums.ClientType;
import com.bruno.projectMc.domain.enums.PaymentState;
import com.bruno.projectMc.repositories.AdressRepository;
import com.bruno.projectMc.repositories.CategoryRepository;
import com.bruno.projectMc.repositories.CityRepository;
import com.bruno.projectMc.repositories.ClientRepository;
import com.bruno.projectMc.repositories.OrderItemRepository;
import com.bruno.projectMc.repositories.OrderRepository;
import com.bruno.projectMc.repositories.PaymentRepository;
import com.bruno.projectMc.repositories.ProductRepository;
import com.bruno.projectMc.repositories.StateRepository;

@SpringBootApplication
public class ProjectSpringBootApplication implements CommandLineRunner {

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
	
	@Autowired
	private OrderRepository orderRepository;
	
	@Autowired
	private PaymentRepository paymentRepository;
	
	@Autowired
	private OrderItemRepository orderItemRepository;

	public static void main(String[] args) {
		SpringApplication.run(ProjectSpringBootApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		Category cat1 = new Category(null, "Informatica");
		Category cat2 = new Category(null, "Escritorio");
		Category cat3 = new Category(null, "Cama mesa e banho");
		Category cat4 = new Category(null, "Eletronicos");
		Category cat5 = new Category(null, "Jardinagem");
		Category cat6 = new Category(null, "Decoração");
		Category cat7 = new Category(null, "Perfumaria");

		Product product1 = new Product(null, "computador", 2000.00);
		Product product2 = new Product(null, "impressora", 800.00);
		Product product3 = new Product(null, "mouse", 80.00);

		cat1.getProducts().addAll(Arrays.asList(product1, product2, product3));
		cat2.getProducts().addAll(Arrays.asList(product2));

		product1.getCategories().addAll(Arrays.asList(cat1));
		product2.getCategories().addAll(Arrays.asList(cat1, cat2));
		product3.getCategories().addAll(Arrays.asList(cat1));

		catRepository.saveAll(Arrays.asList(cat1, cat2, cat3, cat4, cat5, cat6, cat7));
		prodRepository.saveAll(Arrays.asList(product1, product2, product3));

		State state1 = new State(null, "Minas Gerais");
		State state2 = new State(null, "São Paulo");

		City city1 = new City(null, "Uberlandia", state1);
		City city2 = new City(null, "São Paulo", state2);
		City city3 = new City(null, "Campinas", state2);

		state1.getCities().addAll(Arrays.asList(city1));
		state2.getCities().addAll(Arrays.asList(city2, city3));

		stateRepository.saveAll(Arrays.asList(state1, state2));

		cityRepository.saveAll(Arrays.asList(city1, city2, city3));

		Client cli1 = new Client(null, "Maria Silva", "maria@gmail.com", "36378912377", ClientType.PESSOAFISICA);

		cli1.getPhones().addAll(Arrays.asList("21334261", "99328452"));
		Adress adress = new Adress(null, "Rua Flores", "300", "Apto 203", "Jardim", "38220834", cli1, city1);
		Adress adress1 = new Adress(null, "Avenida Matos", "105", "Sala 800", "Centro", "38777012", cli1, city2);

		cli1.getAdresses().addAll(Arrays.asList(adress, adress1));
		
		cliRepository.saveAll(Arrays.asList(cli1));
		adressRepository.saveAll(Arrays.asList(adress, adress1));

		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm");
		Order order1 = new Order(null, sdf.parse("30/09/2017 10:32"), cli1, adress);
		Order order2 = new Order(null, sdf.parse("10/10/2017 19:35"), cli1, adress1);
		
		Payment pay1 = new PaymentCreditCard(null, PaymentState.QUITADO, order1, 6);
		order1.setPayment(pay1);
	
		Payment pay2 = new PaymentBankSlip(null, PaymentState.PENDENTE, order2 , sdf.parse("20/10/2017 00:00"), null);
		order2.setPayment(pay2);
		cli1.getOrders().addAll(Arrays.asList(order1, order2));
		
		orderRepository.saveAll(Arrays.asList(order1, order2));
		paymentRepository.saveAll(Arrays.asList(pay1, pay2));
	
		OrderItem orderItem1 = new OrderItem(order1, product1, 0.0, 1, product1.getPrice());
		OrderItem orderItem2 = new OrderItem(order1, product3, 0.0, 2, product3.getPrice());
		OrderItem orderItem3 = new OrderItem(order2, product2 , 100.0, 1, product2.getPrice());
		
		order1.getItems().addAll(Arrays.asList(orderItem1, orderItem2));
		order1.getItems().addAll(Arrays.asList(orderItem3));
		
		product1.getItems().addAll(Arrays.asList(orderItem1));
		product2.getItems().addAll(Arrays.asList(orderItem3));
		product3.getItems().addAll(Arrays.asList(orderItem2));
		
		orderItemRepository.saveAll(Arrays.asList(orderItem1, orderItem2, orderItem3));
		
	}

}
