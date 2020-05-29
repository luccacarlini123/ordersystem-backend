package com.mouzetech.ordersystem;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class OrdersystemApplication implements CommandLineRunner {

	public static void main(String[] args) {
		SpringApplication.run(OrdersystemApplication.class, args);
	}
	
	@Override
	public void run(String... args) throws Exception {
	}
}
