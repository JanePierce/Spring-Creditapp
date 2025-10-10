package com.creditapp.creditsystem;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class CreditSystemApplication {

	public static void main(String[] args) {
		SpringApplication.run(CreditSystemApplication.class, args);
        System.out.println("приложение запущено");
	}

}
