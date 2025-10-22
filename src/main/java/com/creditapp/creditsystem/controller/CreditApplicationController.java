package com.creditapp.creditsystem.controller;

import com.creditapp.creditsystem.dto.CreditApplicationRequest;
import com.creditapp.creditsystem.entity.ApplicationStatus;
import com.creditapp.creditsystem.entity.CreditApplication;
import com.creditapp.creditsystem.service.CreditApplicationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

/**
 * Контроллер для управления кредитными заявками
 * Обрабатывает все операции с заявками на кредит: создание, просмотр, скоринг, изменение статуса
 */
@Controller
@RequestMapping("/applications") // Базовый URL для всех endpoints связанных с заявками
public class CreditApplicationController {

    @Autowired
    private CreditApplicationService creditApplicationService; // Сервис для бизнес-логики кредитных заявок

    /**
     * Отображение страницы со списком всех кредитных заявок
     * @param model Модель для передачи данных в представление
     * @return имя шаблона для отображения списка заявок
     */
    @GetMapping
    public String listApplications(Model model) {
        System.out.println("📋 Получение списка заявок для отображения");
        List<CreditApplication> applications = creditApplicationService.getAllApplications(); // Получаем все заявки
        model.addAttribute("applications", applications); // Передаем список заявок в представление
        model.addAttribute("title", "Список заявок"); // Заголовок страницы
        return "applications/list"; // Шаблон для отображения списка
    }

    /**
     * Отображение формы для создания новой кредитной заявки
     * @param model Модель для передачи данных в представление
     * @return имя шаблона формы создания заявки
     */
    @GetMapping("/new")
    public String showCreateForm(Model model) {
        System.out.println("📝 Отображение формы создания заявки");
        // Создаем пустой DTO объект для заполнения в форме
        model.addAttribute("applicationRequest", new CreditApplicationRequest());
        model.addAttribute("title", "Новая заявка");
        return "applications/form"; // Шаблон формы создания/редактирования
    }

    /**
     * Обработка создания новой кредитной заявки
     * @param request DTO с данными заявки из формы
     * @param model Модель для передачи данных и сообщений
     * @return перенаправление на страницу заявки или обратно на форму в случае ошибки
     */
    @PostMapping
    public String createApplication(@ModelAttribute CreditApplicationRequest request, Model model) {
        System.out.println("🆕 Создание заявки: " + request);
        try {
            // Создаем заявку через сервис, передавая DTO
            CreditApplication application = creditApplicationService.createApplication(request);
            model.addAttribute("successMessage", "Заявка успешно создана!");
            // Перенаправляем на страницу созданной заявки
            return "redirect:/applications/" + application.getId();
        } catch (Exception e) {
            // В случае ошибки сохраняем введенные данные и сообщение об ошибке
            model.addAttribute("errorMessage", "Ошибка: " + e.getMessage());
            model.addAttribute("applicationRequest", request);
            return "applications/form"; // Возвращаем на форму с сохраненными данными
        }
    }

    /**
     * Просмотр детальной информации о конкретной кредитной заявке
     * @param id идентификатор заявки
     * @param model Модель для передачи данных в представление
     * @return шаблон просмотра заявки или перенаправление при отсутствии заявки
     */
    @GetMapping("/{id}")
    public String viewApplication(@PathVariable Long id, Model model) {
        System.out.println("👀 Просмотр заявки с ID: " + id);
        Optional<CreditApplication> application = creditApplicationService.getApplicationById(id);
        if (application.isPresent()) {
            model.addAttribute("app", application.get()); // Передаем заявку в модель
            model.addAttribute("title", "Заявка #" + application.get().getId());
            return "applications/view"; // Шаблон для детального просмотра
        } else {
            model.addAttribute("errorMessage", "Заявка с ID " + id + " не найдена");
            return "redirect:/applications"; // Перенаправляем если заявка не найдена
        }
    }

    /**
     * Запуск процесса скоринга для кредитной заявки
     * Автоматически рассчитывает кредитный рейтинг и обновляет статус заявки
     * @param id идентификатор заявки для скоринга
     * @param model Модель для передачи сообщений
     * @return перенаправление на страницу заявки с результатом скоринга
     */
    @PostMapping("/{id}/scoring")
    public String runScoring(@PathVariable Long id, Model model) {
        System.out.println("🎯 Запуск скоринга для заявки: " + id);
        try {
            // Запускаем процесс скоринга через сервис
            CreditApplication application = creditApplicationService.runScoring(id);
            model.addAttribute("successMessage",
                    "Скоринг завершен! Результат: " + application.getStatus() +
                            " (Скор: " + application.getScore() + ")");
            return "redirect:/applications/" + id;
        } catch (Exception e) {
            model.addAttribute("errorMessage", "Ошибка скоринга: " + e.getMessage());
            return "redirect:/applications/" + id;
        }
    }

    /**
     * Ручное изменение статуса кредитной заявки
     * Используется для административного управления статусами (например, одобрение/отклонение)
     * @param id идентификатор заявки
     * @param status новый статус из enum ApplicationStatus
     * @param model Модель для передачи сообщений
     * @return перенаправление на страницу заявки
     */
    @PostMapping("/{id}/status")
    public String updateStatus(@PathVariable Long id,
                               @RequestParam ApplicationStatus status,
                               Model model) {
        System.out.println("✏️ Изменение статуса заявки " + id + " на: " + status);
        try {
            // Обновляем статус заявки через сервис
            CreditApplication application = creditApplicationService.updateApplicationStatus(id, status);
            model.addAttribute("successMessage", "Статус заявки изменен на: " + status);
            return "redirect:/applications/" + id;
        } catch (Exception e) {
            model.addAttribute("errorMessage", "Ошибка: " + e.getMessage());
            return "redirect:/applications/" + id;
        }
    }

    /**
     * Фильтрация заявок по определенному статусу
     * @param status статус для фильтрации (из enum ApplicationStatus)
     * @param model Модель для передачи данных в представление
     * @return шаблон списка заявок с отфильтрованными результатами
     */
    @GetMapping("/status/{status}")
    public String applicationsByStatus(@PathVariable ApplicationStatus status, Model model) {
        System.out.println("🔍 Поиск заявок по статусу: " + status);
        // Получаем заявки с указанным статусом через сервис
        List<CreditApplication> applications = creditApplicationService.getApplicationsByStatus(status);
        model.addAttribute("applications", applications); // Список отфильтрованных заявок
        model.addAttribute("status", status); // Текущий статус фильтра
        model.addAttribute("title", "Заявки со статусом: " + status);
        return "applications/list"; // Используем тот же шаблон что и для полного списка
    }

    /**
     * Отображение статистики по кредитным заявкам
     * Показывает аналитику: количество заявок по статусам, средние показатели и т.д.
     * @param model Модель для передачи статистических данных
     * @return шаблон страницы статистики
     */
    @GetMapping("/statistics")
    public String showStatistics(Model model) {
        System.out.println("📊 Отображение статистики");
        // Получаем статистику в формате строки (может быть JSON, HTML или простой текст)
        String statistics = creditApplicationService.getStatistics();
        model.addAttribute("statistics", statistics); // Статистические данные
        model.addAttribute("title", "Статистика заявок");
        return "applications/statistics"; // Шаблон для отображения статистики
    }
}