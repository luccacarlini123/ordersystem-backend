package com.mouzetech.ordersystem.services;

import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.mouzetech.ordersystem.domain.Cliente;
import com.mouzetech.ordersystem.repositories.ClienteRepository;
import com.mouzetech.ordersystem.services.exceptions.ObjectNotFoundException;

@Service
public class AuthService {

	@Autowired
	private ClienteRepository clienteRepo;
	
	@Autowired
	private BCryptPasswordEncoder pe;
	
	@Autowired EmailService emailService;
	
	private Random random = new Random();
	
	public void sendNewPassword(String email) {
		Cliente obj = clienteRepo.findByEmail(email);
		if(obj==null) {
			throw new ObjectNotFoundException("Email não encontrado");
		}
		String newPassword = newPassword();
		obj.setSenha(pe.encode(newPassword));
		
		clienteRepo.save(obj);
		emailService.sendNewPasswordEmail(obj, newPassword);
	}

	private String newPassword() {
		char[] vet = new char[10];
		for(int i=0;i<10;i++) {
			vet[i] = randomChar();
		}
		return new String(vet);
	}

	private char randomChar() {
		int opt = random.nextInt(3);
		if(opt==0) { //gera um digito
			return (char) (random.nextInt(10) + 48);
		}else if(opt==1) { //gera letra maiuscula
			return (char) (random.nextInt(26) + 65);
		}else { //gera letra minuscula
			return (char) (random.nextInt(26) + 97);
		}
	}
	
}
