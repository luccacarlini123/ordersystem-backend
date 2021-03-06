package com.mouzetech.ordersystem.config;

import java.io.InputStream;
import java.text.ParseException;

import javax.mail.internet.MimeMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessagePreparator;

import com.mouzetech.ordersystem.services.DBService;
import com.mouzetech.ordersystem.services.EmailService;
import com.mouzetech.ordersystem.services.SmtpEmailService;

@Configuration
@Profile("test")
public class TestConfig {

	@Autowired
	private DBService service;
	
	@Bean
	public boolean instantiateDatabase() throws ParseException {
		service.instantiateTestDatabse();
		return true;
	}
	
	@Bean
	public EmailService emailService() {
		return new SmtpEmailService();
	}
	
	@Bean
	public JavaMailSender javaMailSender() {
		return new JavaMailSender() {
			
			@Override
			public void send(SimpleMailMessage... simpleMessages) throws MailException {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void send(SimpleMailMessage simpleMessage) throws MailException {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void send(MimeMessagePreparator... mimeMessagePreparators) throws MailException {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void send(MimeMessagePreparator mimeMessagePreparator) throws MailException {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void send(MimeMessage... mimeMessages) throws MailException {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void send(MimeMessage mimeMessage) throws MailException {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public MimeMessage createMimeMessage(InputStream contentStream) throws MailException {
				// TODO Auto-generated method stub
				return null;
			}
			
			@Override
			public MimeMessage createMimeMessage() {
				// TODO Auto-generated method stub
				return null;
			}
		};
	}
}
