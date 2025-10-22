package com.creditapp.creditsystem;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class CreditSystemApplication {

	public static void main(String[] args) {
		SpringApplication.run(CreditSystemApplication.class, args);
        System.out.println("=".repeat(50));
        System.out.println("✅ Кредитное приложение ЗАПУЩЕНО!");
        System.out.println("✅ Сервер работает на: http://localhost:8080");
        System.out.println("✅ Проверьте в браузере эту ссылку");
        System.out.println("=".repeat(50));

    }

}
