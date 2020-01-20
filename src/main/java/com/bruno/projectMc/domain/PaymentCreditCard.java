package com.bruno.projectMc.domain;

import javax.persistence.Entity;

import com.bruno.projectMc.domain.enums.PaymentState;

@Entity
public class PaymentCreditCard extends Payment{

	private static final long serialVersionUID = 1L;
	
	private Integer numberInstallments;
	
	public PaymentCreditCard() {
	}

	public PaymentCreditCard(Integer id, PaymentState state, Order order, Integer numberInstallments) {
		super(id, state, order);
		this.numberInstallments = numberInstallments;
	}

	public Integer getNumberInstallments() {
		return numberInstallments;
	}

	public void setNumberInstallments(Integer numberInstallments) {
		this.numberInstallments = numberInstallments;
	}
	
	
	
}
