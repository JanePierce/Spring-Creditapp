package com.creditapp.creditsystem.service;

import com.creditapp.creditsystem.dto.CreditApplicationRequest;
import com.creditapp.creditsystem.entity.ApplicationStatus;
import com.creditapp.creditsystem.entity.Client;
import com.creditapp.creditsystem.entity.CreditApplication;
import com.creditapp.creditsystem.repository.ClientRepository;
import com.creditapp.creditsystem.repository.CreditApplicationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class CreditApplicationService {

    @Autowired
    private CreditApplicationRepository creditApplicationRepository;

    @Autowired
    private ClientRepository clientRepository;

    @Autowired
    private ScoringService scoringService;

    // Создание новой кредитной заявки
    public CreditApplication createApplication(CreditApplicationRequest request) {
        System.out.println("🆕 Создание кредитной заявки: " + request);

        // Находим клиента по паспорту
        Client client = clientRepository.findByPassportData(request.getPassportData())
                .orElseThrow(() -> new RuntimeException("❌ Клиент с паспортом " + request.getPassportData() + " не найден"));

        // Создаем заявку
        CreditApplication application = new CreditApplication();
        application.setClient(client);
        application.setAmount(request.getAmount());
        application.setStatus(ApplicationStatus.NEW);
        application.setCreationDate(LocalDateTime.now());

        CreditApplication savedApplication = creditApplicationRepository.save(application);
        System.out.println("✅ Заявка создана с ID: " + savedApplication.getId());

        return savedApplication;
    }

    // Получение заявки по ID
    public Optional<CreditApplication> getApplicationById(Long id) {
        System.out.println("🔍 Поиск заявки по ID: " + id);
        return creditApplicationRepository.findById(id);
    }

    // Получение всех заявок
    public List<CreditApplication> getAllApplications() {
        System.out.println("📋 Получение всех заявок");
        return creditApplicationRepository.findAll();
    }

    // Получение заявок по статусу
    public List<CreditApplication> getApplicationsByStatus(ApplicationStatus status) {
        System.out.println("🔍 Поиск заявок по статусу: " + status);
        return creditApplicationRepository.findByStatus(status);
    }

    // Получение заявок клиента
    public List<CreditApplication> getClientApplications(Long clientId) {
        System.out.println("📄 Получение заявок клиента с ID: " + clientId);
        return creditApplicationRepository.findByClientId(clientId);
    }

    // Обновление статуса заявки
    public CreditApplication updateApplicationStatus(Long id, ApplicationStatus newStatus) {
        System.out.println("✏️ Обновление статуса заявки " + id + " на: " + newStatus);

        CreditApplication application = creditApplicationRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("❌ Заявка с ID " + id + " не найдена"));

        application.setStatus(newStatus);

        CreditApplication updatedApplication = creditApplicationRepository.save(application);
        System.out.println("✅ Статус заявки обновлен");
        return updatedApplication;
    }

    // Запуск скоринга для заявки
    public CreditApplication runScoring(Long applicationId) {
        System.out.println("🎯 Запуск скоринга для заявки: " + applicationId);

        CreditApplication application = creditApplicationRepository.findById(applicationId)
                .orElseThrow(() -> new RuntimeException("❌ Заявка с ID " + applicationId + " не найдена"));

        // Меняем статус на SCORING
        application.setStatus(ApplicationStatus.SCORING);
        creditApplicationRepository.save(application);

        // Запускаем скоринг
        Integer score = scoringService.calculateScore(application);
        application.setScore(score);

        // Определяем результат на основе скоринга
        if (score >= 60) {
            application.setStatus(ApplicationStatus.APPROVED);
            System.out.println("✅ Заявка одобрена! Скор: " + score);
        } else {
            application.setStatus(ApplicationStatus.REJECTED);
            System.out.println("❌ Заявка отклонена! Скор: " + score);
        }

        CreditApplication scoredApplication = creditApplicationRepository.save(application);
        return scoredApplication;
    }

    // Получение статистики
    public String getStatistics() {
        long total = creditApplicationRepository.count();
        long newCount = creditApplicationRepository.countByStatus(ApplicationStatus.NEW);
        long approvedCount = creditApplicationRepository.countByStatus(ApplicationStatus.APPROVED);
        long rejectedCount = creditApplicationRepository.countByStatus(ApplicationStatus.REJECTED);
        long scoringCount = creditApplicationRepository.countByStatus(ApplicationStatus.SCORING);

        return String.format(
                "📊 Статистика заявок:\n" +
                        "Всего: %d\n" +
                        "Новых: %d\n" +
                        "На скоринге: %d\n" +
                        "Одобрено: %d\n" +
                        "Отклонено: %d",
                total, newCount, scoringCount, approvedCount, rejectedCount
        );
    }

    // Поиск заявок по сумме
    public List<CreditApplication> getApplicationsByAmountRange(BigDecimal minAmount, BigDecimal maxAmount) {
        System.out.println("🔍 Поиск заявок по сумме от " + minAmount + " до " + maxAmount);
        return creditApplicationRepository.findByAmountBetween(minAmount, maxAmount);
    }
}