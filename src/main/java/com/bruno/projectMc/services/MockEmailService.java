package com.bruno.projectMc.services;

import javax.mail.internet.MimeMessage;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.mail.SimpleMailMessage;

import com.bruno.projectMc.domain.Client;
import com.bruno.projectMc.domain.Order;

public class MockEmailService extends AbstractEmailService{

	private static final Logger LOG = LoggerFactory.getLogger(MockEmailService.class);
	
	@Override
	public void sendEmail(SimpleMailMessage msg) {
		LOG.info("simulating email");
		LOG.info(msg.toString());
		LOG.info("Email sent");
	}

	@Override
	public void sendHtmlEmail(MimeMessage msg) {
		LOG.info("simulating html email");
		LOG.info(msg.toString());
		LOG.info("Email sent");
		
	}

	@Override
	public void sendOrderConfirmationHtmlEmail(Order order) {
		// TODO Auto-generated method stub
		
	}
}
