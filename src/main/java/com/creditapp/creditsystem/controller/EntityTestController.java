package com.creditapp.creditsystem.controller;

import com.creditapp.creditsystem.entity.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;

@RestController
public class EntityTestController {

    @GetMapping("/test-entities")
    public String testEntities() {
        System.out.println("=== ТЕСТИРУЕМ СУЩНОСТИ ===");

//        // Создаем тестового клиента
//        Client client = new Client("Тест", "Тестовый", "9999888877");
//        client.setPhoneNumber("+79990001122");
//        client.setEmail("test@test.ru");
//
//        System.out.println("✅ Создан клиент: " + client);
//
//        // Создаем тестовую заявку
//        CreditApplication application = new CreditApplication();
//        application.setClient(client);
//        application.setAmount(new BigDecimal("150000.00"));
//        application.setStatus(ApplicationStatus.NEW);
//
//        System.out.println("✅ Создана заявка: " + application);
//
//        // Создаем тестовое правило
//        ScoringRule rule = new ScoringRule("Тестовое правило", "Сумма > 100000", 15);
//        System.out.println("✅ Создано правило: " + rule);
//
//        // Проверяем enum
//        System.out.println("✅ Статусы заявок:");
//        for (ApplicationStatus status : ApplicationStatus.values()) {
//            System.out.println("   - " + status);
//        }
//
//        return "<h2>✅ Entity классы работают!</h2>" +
//                "<p>Проверьте консоль для подробной информации</p>" +
//                "<p>Созданы:</p>" +
//                "<ul>" +
//                "<li>✅ Клиент: " + client.getFirstName() + " " + client.getLastName() + "</li>" +
//                "<li>✅ Заявка: " + application.getAmount() + " руб.</li>" +
//                "<li>✅ Правило: " + rule.getRuleName() + "</li>" +
//                "</ul>";
        return "Brodyaga";
    }
}
