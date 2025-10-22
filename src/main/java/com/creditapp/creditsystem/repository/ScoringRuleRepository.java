package com.creditapp.creditsystem.repository;

import com.creditapp.creditsystem.entity.ScoringRule;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ScoringRuleRepository extends JpaRepository<ScoringRule, Long> {

    // Поиск активных правил
    List<ScoringRule> findByActiveTrue();

    // Поиск по названию правила
    List<ScoringRule> findByRuleNameContainingIgnoreCase(String ruleName);

    // Поиск правил с минимальным количеством баллов
    List<ScoringRule> findByPointsGreaterThanEqual(Integer minPoints);
}
