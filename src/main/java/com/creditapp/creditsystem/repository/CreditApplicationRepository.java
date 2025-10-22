package com.creditapp.creditsystem.repository;

import com.creditapp.creditsystem.entity.ApplicationStatus;
import com.creditapp.creditsystem.entity.CreditApplication;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface CreditApplicationRepository extends JpaRepository<CreditApplication, Long> {

    // Поиск заявок по статусу
    List<CreditApplication> findByStatus(ApplicationStatus status);

    // Поиск заявок по клиенту
    List<CreditApplication> findByClientId(Long clientId);

    // Поиск заявок по сумме (больше указанной)
    List<CreditApplication> findByAmountGreaterThan(BigDecimal amount);

    // Поиск заявок по сумме (между значениями)
    List<CreditApplication> findByAmountBetween(BigDecimal minAmount, BigDecimal maxAmount);

    // Поиск заявок по дате создания
    List<CreditApplication> findByCreationDateBetween(LocalDateTime startDate, LocalDateTime endDate);

    // Поиск заявок по клиенту и статусу
    List<CreditApplication> findByClientIdAndStatus(Long clientId, ApplicationStatus status);

    // Кастомный запрос для подсчета заявок по статусу
    @Query("SELECT COUNT(ca) FROM CreditApplication ca WHERE ca.status = :status")
    long countByStatus(@Param("status") ApplicationStatus status);

    // Поиск заявок с клиентами (жадная загрузка)
    @Query("SELECT ca FROM CreditApplication ca JOIN FETCH ca.client")
    List<CreditApplication> findAllWithClient();
}

