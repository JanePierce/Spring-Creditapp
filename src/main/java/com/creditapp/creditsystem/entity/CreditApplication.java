package com.creditapp.creditsystem.entity;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "credit_applications")
public class CreditApplication {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Связь с клиентом (многие к одному)
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "client_id", nullable = false)
    private Client client;

    @Column(nullable = false, precision = 15, scale = 2)
    private BigDecimal amount;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private ApplicationStatus status = ApplicationStatus.NEW;

    @Column(name = "creation_date")
    private LocalDateTime creationDate;

    @Column(name = "decision_date")
    private LocalDateTime decisionDate;

    private Integer score;

    // Конструкторы
    public CreditApplication() {
        this.creationDate = LocalDateTime.now();
    }

    public CreditApplication(Client client, BigDecimal amount) {
        this();
        this.client = client;
        this.amount = amount;
        this.status = ApplicationStatus.NEW;
    }

    // Геттеры и сеттеры
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Client getClient() {
        return client;
    }

    public void setClient(Client client) {
        this.client = client;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public ApplicationStatus getStatus() {
        return status;
    }

    public void setStatus(ApplicationStatus status) {
        this.status = status;
        if ((status == ApplicationStatus.APPROVED || status == ApplicationStatus.REJECTED)
                && this.decisionDate == null) {
            this.decisionDate = LocalDateTime.now();
        }
    }

    public LocalDateTime getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(LocalDateTime creationDate) {
        this.creationDate = creationDate;
    }

    public LocalDateTime getDecisionDate() {
        return decisionDate;
    }

    public void setDecisionDate(LocalDateTime decisionDate) {
        this.decisionDate = decisionDate;
    }

    public Integer getScore() {
        return score;
    }

    public void setScore(Integer score) {
        this.score = score;
    }

    // Вспомогательные методы
    public boolean isNew() {
        return status == ApplicationStatus.NEW;
    }

    public boolean isApproved() {
        return status == ApplicationStatus.APPROVED;
    }

    public boolean isRejected() {
        return status == ApplicationStatus.REJECTED;
    }

    @Override
    public String toString() {
        return "CreditApplication{" +
                "id=" + id +
                ", client=" + (client != null ? client.getFirstName() + " " + client.getLastName() : "null") +
                ", amount=" + amount +
                ", status=" + status +
                ", creationDate=" + creationDate +
                ", decisionDate=" + decisionDate +
                ", score=" + score +
                '}';
    }
}

