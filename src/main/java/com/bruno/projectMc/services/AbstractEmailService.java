package com.bruno.projectMc.services;

import java.util.Date;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;

import com.bruno.projectMc.domain.Order;

public abstract class AbstractEmailService implements EmailService{

	@Value("${default.sender}")
	private String sender;
	
	@Override
	public void sendOrderConfirmationEmail(Order order) {
		SimpleMailMessage sm = prepareSimpleMailMessageFromOrder(order);
		sendEmail(sm);
	}

	protected SimpleMailMessage prepareSimpleMailMessageFromOrder(Order order) {
		SimpleMailMessage sm = new SimpleMailMessage();
		sm.setTo(order.getClient().getEmail());
		sm.setFrom(sender);
		sm.setSubject("Pedido Confirmado! Cod: " + order.getId());
		sm.setSentDate(new Date(System.currentTimeMillis()));
		sm.setText(order.toString());
		return sm;
	}
	
}
