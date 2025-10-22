package com.creditapp.creditsystem.entity;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * Сущность Кредитная заявка (CreditApplication)
 * JPA entity для представления таблицы кредитных заявок в базе данных
 * Содержит информацию о кредитной заявке, ее статусе и результатах скоринга
 */
@Entity
@Table(name = "credit_applications") // Указывает имя таблицы в базе данных
public class CreditApplication {

    /**
     * Уникальный идентификатор кредитной заявки
     * Стратегия IDENTITY использует автоинкремент базы данных
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * Связь многие к одному с клиентом
     * Многие заявки могут принадлежать одному клиенту
     * FetchType.LAZY - загрузка клиента только при обращении (оптимизация производительности)
     * nullable = false - у заявки всегда должен быть клиент
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "client_id", nullable = false)
    private Client client;

    /**
     * Запрашиваемая сумма кредита
     * Обязательное поле, precision = 15 (общее количество цифр), scale = 2 (знаки после запятой)
     * Подходит для хранения сумм до 999 миллиардов с копейками
     */
    @Column(nullable = false, precision = 15, scale = 2)
    private BigDecimal amount;

    /**
     * Статус кредитной заявки
     * EnumType.STRING - хранит строковое представление enum в базе данных
     * По умолчанию устанавливается в NEW при создании
     */
    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private ApplicationStatus status = ApplicationStatus.NEW;

    /**
     * Дата и время создания заявки
     * Автоматически устанавливается при создании объекта
     */
    @Column(name = "creation_date")
    private LocalDateTime creationDate;

    /**
     * Дата и время принятия решения по заявке
     * Заполняется автоматически при изменении статуса на APPROVED или REJECTED
     */
    @Column(name = "decision_date")
    private LocalDateTime decisionDate;

    /**
     * Кредитный скоринг (рейтинг) заявки
     * Рассчитывается в процессе скоринга, обычно от 0 до 1000
     * Может быть null если скоринг еще не проводился
     */
    private Integer score;

    // Конструкторы

    /**
     * Конструктор по умолчанию
     * Требуется JPA/Hibernate для создания proxy-объектов
     * Автоматически устанавливает дату создания
     */
    public CreditApplication() {
        this.creationDate = LocalDateTime.now(); // Устанавливаем текущую дату и время
    }

    /**
     * Параметризованный конструктор
     * Используется при создании новой кредитной заявки
     *
     * @param client клиент, подающий заявку
     * @param amount запрашиваемая сумма кредита
     */
    public CreditApplication(Client client, BigDecimal amount) {
        this(); // Вызов конструктора по умолчанию для установки creationDate
        this.client = client;
        this.amount = amount;
        this.status = ApplicationStatus.NEW; // Явно устанавливаем начальный статус
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

    /**
     * Установка статуса заявки с бизнес-логикой
     * При установке статуса APPROVED или REJECTED автоматически устанавливается decisionDate
     *
     * @param status новый статус заявки
     */
    public void setStatus(ApplicationStatus status) {
        this.status = status;
        // Автоматически устанавливаем дату решения при одобрении или отклонении
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

    // Вспомогательные методы (бизнес-логика)

    /**
     * Проверяет, является ли заявка новой (еще не обработанной)
     *
     * @return true если статус NEW
     */
    public boolean isNew() {
        return status == ApplicationStatus.NEW;
    }

    /**
     * Проверяет, одобрена ли заявка
     *
     * @return true если статус APPROVED
     */
    public boolean isApproved() {
        return status == ApplicationStatus.APPROVED;
    }

    /**
     * Проверяет, отклонена ли заявка
     *
     * @return true если статус REJECTED
     */
    public boolean isRejected() {
        return status == ApplicationStatus.REJECTED;
    }

    /**
     * Строковое представление объекта для отладки
     * Включает только имя клиента чтобы избежать циклических ссылок
     *
     * @return строковое представление объекта CreditApplication
     */
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