package com.bruno.projectMc.services;

import java.util.Calendar;
import java.util.Date;

import org.springframework.stereotype.Service;

import com.bruno.projectMc.domain.PaymentBankSlip;

@Service
public class BankSlipService {

	public void fillPaymentBankSlip(PaymentBankSlip pay, Date orderInstant) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(orderInstant);
		cal.add(Calendar.DAY_OF_MONTH, 7);
		pay.setExpirationDate(cal.getTime());
	}
}
