package com.creditapp.creditsystem.controller;

import com.creditapp.creditsystem.entity.Client;
import com.creditapp.creditsystem.service.ClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

/**
 * Контроллер для управления клиентами
 * Обрабатывает HTTP-запросы, связанные с операциями CRUD для клиентов
 */
@Controller
@RequestMapping("/clients") // Базовый URL для всех методов контроллера
public class ClientController {

    @Autowired
    private ClientService clientService; // Сервис для бизнес-логики клиентов

    /**
     * Отображение страницы со списком всех клиентов
     * @param model Модель для передачи данных в представление
     * @return имя шаблона для отображения списка клиентов
     */
    @GetMapping
    public String listClients(Model model) {
        System.out.println("📋 Получение списка клиентов для отображения");
        List<Client> clients = clientService.getAllClients(); // Получаем всех клиентов из сервиса
        model.addAttribute("clients", clients); // Добавляем список клиентов в модель
        model.addAttribute("title", "Список клиентов"); // Заголовок страницы
        return "clients/list"; // Возвращаем имя Thymeleaf шаблона
    }

    /**
     * Отображение формы для создания нового клиента
     * @param model Модель для передачи данных в представление
     * @return имя шаблона формы создания клиента
     */
    @GetMapping("/new")
    public String showCreateForm(Model model) {
        System.out.println("📝 Отображение формы создания клиента");
        model.addAttribute("client", new Client()); // Создаем пустой объект клиента для формы
        model.addAttribute("title", "Новый клиент");
        return "clients/form";
    }

    /**
     * Обработка создания нового клиента
     * @param client Данные клиента из формы
     * @param model Модель для передачи данных в представление
     * @return перенаправление на список клиентов или обратно на форму в случае ошибки
     */
    @PostMapping
    public String createClient(@ModelAttribute Client client, Model model) {
        System.out.println("🆕 Создание клиента: " + client.getFirstName() + " " + client.getLastName());
        try {
            Client savedClient = clientService.createClient(client); // Сохраняем клиента через сервис
            model.addAttribute("successMessage", "Клиент успешно создан!");
            return "redirect:/clients"; // Перенаправляем на список клиентов после успешного создания
        } catch (Exception e) {
            // В случае ошибки возвращаем на форму с сообщением об ошибке
            model.addAttribute("errorMessage", "Ошибка: " + e.getMessage());
            model.addAttribute("client", client); // Сохраняем введенные данные для повторного заполнения
            return "clients/form";
        }
    }

    /**
     * Просмотр подробной информации о конкретном клиенте
     * @param id идентификатор клиента
     * @param model Модель для передачи данных в представление
     * @return шаблон просмотра клиента или перенаправление при отсутствии клиента
     */
    @GetMapping("/{id}")
    public String viewClient(@PathVariable Long id, Model model) {
        System.out.println("👀 Просмотр клиента с ID: " + id);
        Optional<Client> client = clientService.getClientById(id); // Ищем клиента по ID
        if (client.isPresent()) {
            model.addAttribute("client", client.get()); // Добавляем клиента в модель
            model.addAttribute("title", "Клиент: " + client.get().getFirstName() + " " + client.get().getLastName());
            return "clients/view"; // Шаблон для просмотра деталей клиента
        } else {
            model.addAttribute("errorMessage", "Клиент с ID " + id + " не найден");
            return "redirect:/clients"; // Перенаправляем если клиент не найден
        }
    }

    /**
     * Отображение формы редактирования существующего клиента
     * @param id идентификатор клиента для редактирования
     * @param model Модель для передачи данных в представление
     * @return шаблон формы редактирования или перенаправление при отсутствии клиента
     */
    @GetMapping("/{id}/edit")
    public String showEditForm(@PathVariable Long id, Model model) {
        System.out.println("✏️ Отображение формы редактирования клиента: " + id);
        Optional<Client> client = clientService.getClientById(id);
        if (client.isPresent()) {
            model.addAttribute("client", client.get()); // Передаем существующие данные клиента в форму
            model.addAttribute("title", "Редактирование клиента");
            return "clients/form"; // Используем ту же форму что и для создания
        } else {
            model.addAttribute("errorMessage", "Клиент с ID " + id + " не найден");
            return "redirect:/clients";
        }
    }

    /**
     * Обработка обновления данных клиента
     * @param id идентификатор клиента
     * @param client обновленные данные клиента из формы
     * @param model Модель для передачи данных в представление
     * @return перенаправление на страницу клиента или обратно на форму в случае ошибки
     */
    @PostMapping("/{id}")
    public String updateClient(@PathVariable Long id, @ModelAttribute Client client, Model model) {
        System.out.println("🔄 Обновление клиента с ID: " + id);
        try {
            Client updatedClient = clientService.updateClient(id, client); // Обновляем клиента через сервис
            model.addAttribute("successMessage", "Клиент успешно обновлен!");
            return "redirect:/clients/" + id; // Перенаправляем на страницу клиента после успешного обновления
        } catch (Exception e) {
            model.addAttribute("errorMessage", "Ошибка: " + e.getMessage());
            model.addAttribute("client", client);
            return "clients/form";
        }
    }

    /**
     * Удаление клиента
     * @param id идентификатор клиента для удаления
     * @param model Модель для передачи данных в представление
     * @return перенаправление на список клиентов
     */
    @PostMapping("/{id}/delete")
    public String deleteClient(@PathVariable Long id, Model model) {
        System.out.println("🗑️ Удаление клиента с ID: " + id);
        try {
            clientService.deleteClient(id); // Удаляем клиента через сервис
            model.addAttribute("successMessage", "Клиент успешно удален!");
        } catch (Exception e) {
            model.addAttribute("errorMessage", "Ошибка: " + e.getMessage());
        }
        return "redirect:/clients"; // Всегда перенаправляем на список клиентов
    }

    /**
     * Поиск клиентов по фамилии
     * @param lastName фамилия для поиска
     * @param model Модель для передачи данных в представление
     * @return шаблон списка клиентов с результатами поиска
     */
    @GetMapping("/search")
    public String searchClients(@RequestParam String lastName, Model model) {
        System.out.println("🔍 Поиск клиентов по фамилии: " + lastName);
        List<Client> clients = clientService.searchClientsByLastName(lastName); // Поиск через сервис
        model.addAttribute("clients", clients); // Результаты поиска
        model.addAttribute("searchTerm", lastName); // Сохраняем поисковый запрос
        model.addAttribute("title", "Результаты поиска: " + lastName);
        return "clients/list"; // Используем тот же шаблон что и для обычного списка
    }
}