package com.bruno.projectMc.services;

import java.util.Date;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.bruno.projectMc.domain.Category;
import com.bruno.projectMc.domain.Client;
import com.bruno.projectMc.domain.Order;
import com.bruno.projectMc.domain.OrderItem;
import com.bruno.projectMc.domain.PaymentBankSlip;
import com.bruno.projectMc.domain.enums.PaymentState;
import com.bruno.projectMc.repositories.OrderItemRepository;
import com.bruno.projectMc.repositories.OrderRepository;
import com.bruno.projectMc.repositories.PaymentRepository;
import com.bruno.projectMc.security.UserSS;
import com.bruno.projectMc.services.exceptions.AuthorizationException;
import com.bruno.projectMc.services.exceptions.ObjectNotFoundException;

@Service
public class OrderService {

	@Autowired
	private OrderRepository repo;

	@Autowired
	private PaymentRepository repoPay;

	@Autowired
	private BankSlipService bankSlipService;

	@Autowired
	private ProductService productService;

	@Autowired
	private ClientService clientService;

	@Autowired
	private OrderItemRepository orderItemRepo;

	@Autowired
	private EmailService emailService;

	public Order find(Integer id) {
		Optional<Order> obj = repo.findById(id);
		return obj.orElseThrow(() -> new ObjectNotFoundException(
				"Objeto não encontrado! Id: " + id + ", Tipo: " + Order.class.getName()));
	}

	@Transactional
	public Order insert(Order obj) {
		obj.setId(null);
		obj.setInstant(new Date());
		obj.setClient(clientService.find(obj.getClient().getId()));
		obj.getPayment().setState(PaymentState.PENDENTE);
		obj.getPayment().setOrder(obj);
		if (obj.getPayment() instanceof PaymentBankSlip) {
			PaymentBankSlip pay = (PaymentBankSlip) obj.getPayment();
			bankSlipService.fillPaymentBankSlip(pay, obj.getInstant());
		}
		obj = repo.save(obj);
		repoPay.save(obj.getPayment());
		for (OrderItem oi : obj.getItems()) {
			oi.setDescount(0.0);
			oi.setProduct(productService.find(oi.getProduct().getId()));
			oi.setPrice(oi.getProduct().getPrice());
			oi.setOrder(obj);
		}
		orderItemRepo.saveAll(obj.getItems());
		emailService.sendOrderConfirmationHtmlEmail(obj);
		return obj;
	}

	public Page<Order> findPage(Integer page, Integer linesPerPage, String orderBy, String direction) {
		UserSS user = UserService.authenticated();
		if (user == null) {
			throw new AuthorizationException("Acesso negado");
		}
		PageRequest pageRequest = PageRequest.of(page, linesPerPage, Direction.valueOf(direction), orderBy);
		Client client = clientService.find(user.getId());
		return repo.findByClient(client, pageRequest);
	}

}
