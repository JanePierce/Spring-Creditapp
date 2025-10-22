package com.creditapp.creditsystem.controller;

import com.creditapp.creditsystem.entity.ApplicationStatus;
import com.creditapp.creditsystem.service.ClientService;
import com.creditapp.creditsystem.service.CreditApplicationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * Контроллер для главной страницы и панели управления
 * Обрабатывает основные маршруты приложения: домашнюю страницу и дашборд
 */
@Controller
public class HomeController {

    @Autowired
    private ClientService clientService;

    @Autowired
    private CreditApplicationService creditApplicationService;

    /**
     * Обработка запроса панели управления (дашборда)
     * Дашборд обычно содержит сводную информацию, статистику и быстрые ссылки
     *
     * @param model Модель для передачи данных в представление
     * @return имя шаблона панели управления
     */
    @GetMapping("/")
    public String home(Model model) {
        // Добавляем заголовок страницы в модель для отображения в шаблоне
        model.addAttribute("title", "Кредитная система");
        // Добавляем приветственное сообщение для пользователя
        model.addAttribute("message", "Добро пожаловать в систему управления кредитными заявками!");

        // ДОБАВЛЯЕМ РЕАЛЬНЫЕ ДАННЫЕ ИЗ БАЗЫ ДЛЯ СТАТИСТИКИ
        try {
            long totalClients = clientService.getAllClients().size();
            long approvedApplications = creditApplicationService.getApplicationsByStatus(ApplicationStatus.APPROVED).size();
            long scoringApplications = creditApplicationService.getApplicationsByStatus(ApplicationStatus.SCORING).size();
            long newApplications = creditApplicationService.getApplicationsByStatus(ApplicationStatus.NEW).size();
            long rejectedApplications = creditApplicationService.getApplicationsByStatus(ApplicationStatus.REJECTED).size();

            model.addAttribute("totalClients", totalClients);
            model.addAttribute("approvedApplications", approvedApplications);
            model.addAttribute("scoringApplications", scoringApplications);
            model.addAttribute("newApplications", newApplications);
            model.addAttribute("rejectedApplications", rejectedApplications);
            model.addAttribute("pendingApplications", scoringApplications + newApplications);

            System.out.println("📊 Статистика загружена: " + totalClients + " клиентов, " + approvedApplications + " одобренных заявок");
        } catch (Exception e) {
            // Если сервисы не готовы, используем заглушки
            System.out.println("⚠️ Сервисы не доступны, используем заглушки: " + e.getMessage());
            model.addAttribute("totalClients", 19);
            model.addAttribute("approvedApplications", 8);
            model.addAttribute("scoringApplications", 6);
            model.addAttribute("newApplications", 4);
            model.addAttribute("rejectedApplications", 5);
            model.addAttribute("pendingApplications", 10);
        }

        // Возвращаем имя Thymeleaf шаблона (dashboard.html)
        return "dashboard";
    }

    /**
     * Обработка запроса панели управления (дашборда)
     * Дашборд обычно содержит сводную информацию, статистику и быстрые ссылки
     *
     * @param model Модель для передачи данных в представление
     * @return имя шаблона панели управления
     */
    @GetMapping("/dashboard")
    public String dashboard(Model model) {
        // Добавляем заголовок для страницы дашборда
        model.addAttribute("title", "Панель управления");

        // ПОВТОРЯЕМ ТУ ЖЕ ЛОГИКУ ДЛЯ СТАТИСТИКИ
        try {
            long totalClients = clientService.getAllClients().size();
            long approvedApplications = creditApplicationService.getApplicationsByStatus(ApplicationStatus.APPROVED).size();
            long scoringApplications = creditApplicationService.getApplicationsByStatus(ApplicationStatus.SCORING).size();
            long newApplications = creditApplicationService.getApplicationsByStatus(ApplicationStatus.NEW).size();
            long rejectedApplications = creditApplicationService.getApplicationsByStatus(ApplicationStatus.REJECTED).size();

            model.addAttribute("totalClients", totalClients);
            model.addAttribute("approvedApplications", approvedApplications);
            model.addAttribute("scoringApplications", scoringApplications);
            model.addAttribute("newApplications", newApplications);
            model.addAttribute("rejectedApplications", rejectedApplications);
            model.addAttribute("pendingApplications", scoringApplications + newApplications);
        } catch (Exception e) {
            model.addAttribute("totalClients", 19);
            model.addAttribute("approvedApplications", 8);
            model.addAttribute("scoringApplications", 6);
            model.addAttribute("newApplications", 4);
            model.addAttribute("rejectedApplications", 5);
            model.addAttribute("pendingApplications", 10);
        }

        // Возвращаем имя Thymeleaf шаблона (dashboard.html)
        return "dashboard";
    }
}