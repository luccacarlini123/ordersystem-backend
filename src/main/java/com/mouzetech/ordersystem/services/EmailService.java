package com.mouzetech.ordersystem.services;

import org.springframework.mail.SimpleMailMessage;

import com.mouzetech.ordersystem.domain.Pedido;

public interface EmailService {

	void sendOrderConfirmationEmail(Pedido obj);
	
	void sendEmail(SimpleMailMessage sm);
	
}
