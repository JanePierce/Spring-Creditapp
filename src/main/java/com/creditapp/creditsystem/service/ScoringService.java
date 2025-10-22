package com.creditapp.creditsystem.service;

import com.creditapp.creditsystem.entity.CreditApplication;
import com.creditapp.creditsystem.entity.ScoringRule;
import com.creditapp.creditsystem.repository.ScoringRuleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service
public class ScoringService {

    @Autowired
    private ScoringRuleRepository scoringRuleRepository;

    // Расчет скоринга для заявки
    public Integer calculateScore(CreditApplication application) {
        System.out.println("🎯 Расчет скоринга для заявки: " + application.getId());

        int totalScore = 0;

        // Получаем все активные правила
        List<ScoringRule> activeRules = scoringRuleRepository.findByActiveTrue();

        // Применяем каждое правило
        for (ScoringRule rule : activeRules) {
            int rulePoints = applyRule(rule, application);
            totalScore += rulePoints;

            System.out.println("📊 Правило '" + rule.getRuleName() + "': +" + rulePoints + " баллов");
        }

        // Ограничиваем счет 0-100
        totalScore = Math.max(0, Math.min(100, totalScore));

        System.out.println("🎯 Итоговый скор: " + totalScore);
        return totalScore;
    }

    // Применение конкретного правила
    private int applyRule(ScoringRule rule, CreditApplication application) {
        String condition = rule.getConditionDescription().toLowerCase();
        BigDecimal amount = application.getAmount();

        // Простая логика применения правил (в реальном приложении это было бы сложнее)
        if (condition.contains("сумма заявки > 500000") && amount.compareTo(new BigDecimal("500000")) > 0) {
            return rule.getPoints();
        }
        else if (condition.contains("сумма заявки между 100000 и 500000") &&
                amount.compareTo(new BigDecimal("100000")) > 0 &&
                amount.compareTo(new BigDecimal("500000")) <= 0) {
            return rule.getPoints();
        }
        else if (condition.contains("сумма заявки < 100000") && amount.compareTo(new BigDecimal("100000")) <= 0) {
            return rule.getPoints();
        }
        else if (condition.contains("первый кредит")) {
            // Проверяем, есть ли у клиента другие заявки
            long previousApplications = application.getClient().getApplications().stream()
                    .filter(app -> app.getCreationDate().isBefore(application.getCreationDate()))
                    .count();
            if (previousApplications == 0) {
                return rule.getPoints();
            }
        }
        else if (condition.contains("второй и более кредит")) {
            // Проверяем, есть ли у клиента другие заявки
            long previousApplications = application.getClient().getApplications().stream()
                    .filter(app -> app.getCreationDate().isBefore(application.getCreationDate()))
                    .count();
            if (previousApplications > 0) {
                return rule.getPoints();
            }
        }

        return 0;
    }
}