package com.bruno.projectMc.services;

import org.springframework.mail.SimpleMailMessage;

import com.bruno.projectMc.domain.Order;

public interface EmailService {

	void sendOrderConfirmationEmail(Order order);
	
	void sendEmail(SimpleMailMessage msg);
	
}
