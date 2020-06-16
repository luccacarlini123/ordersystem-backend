package com.mouzetech.ordersystem;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.mouzetech.ordersystem.services.S3Service;

@SpringBootApplication
public class OrdersystemApplication implements CommandLineRunner {

	
	@Autowired
	private S3Service s3Client;
	
	public static void main(String[] args) {
		SpringApplication.run(OrdersystemApplication.class, args);
	}
	
	@Override
	public void run(String... args) throws Exception {
		s3Client.uploadFile("C:\\Users\\Ratinho\\Desktop\\Creperia\\bannerAnuncioCrepeBrigadeiroBananaMorango.png");
	}
}
