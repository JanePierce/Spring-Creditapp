package com.creditapp.creditsystem.entity;

import jakarta.persistence.*;

/**
 * Сущность Правило скоринга (ScoringRule)
 * JPA entity для представления таблицы правил кредитного скоринга в базе данных
 * Хранит правила и условия для автоматического расчета кредитного рейтинга заявок
 */
@Entity
@Table(name = "scoring_rules") // Указывает имя таблицы в базе данных
public class ScoringRule {

    /**
     * Уникальный идентификатор правила скоринга
     * Стратегия IDENTITY использует автоинкремент базы данных
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * Название правила скоринга
     * Обязательное поле, максимальная длина 200 символов
     * Используется для идентификации правила в системе
     * Пример: "Высокий доход", "Хорошая кредитная история"
     */
    @Column(name = "rule_name", nullable = false, length = 200)
    private String ruleName;

    /**
     * Описание условия правила
     * Обязательное поле, максимальная длина 500 символов
     * Детально описывает условие применения правила
     * Пример: "Ежемесячный доход превышает 100 000 рублей"
     */
    @Column(name = "condition_description", nullable = false, length = 500)
    private String conditionDescription;

    /**
     * Количество баллов за выполнение правила
     * Обязательное поле, целое число
     * Может быть как положительным (увеличивает рейтинг), так и отрицательным (уменьшает)
     */
    @Column(nullable = false)
    private Integer points;

    /**
     * Флаг активности правила
     * Обязательное поле, по умолчанию true
     * Если false - правило не учитывается при расчете скоринга
     * Позволяет временно отключать правила без удаления из базы
     */
    @Column(nullable = false)
    private Boolean active = true;

    // Конструкторы

    /**
     * Конструктор по умолчанию
     * Требуется JPA/Hibernate для создания proxy-объектов
     */
    public ScoringRule() {}

    /**
     * Параметризованный конструктор
     * Используется при создании нового правила скоринга
     * Автоматически устанавливает active = true
     *
     * @param ruleName название правила
     * @param conditionDescription описание условия
     * @param points количество баллов
     */
    public ScoringRule(String ruleName, String conditionDescription, Integer points) {
        this.ruleName = ruleName;
        this.conditionDescription = conditionDescription;
        this.points = points;
        this.active = true; // По умолчанию правило активно
    }

    // Геттеры и сеттеры

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getRuleName() {
        return ruleName;
    }

    public void setRuleName(String ruleName) {
        this.ruleName = ruleName;
    }

    public String getConditionDescription() {
        return conditionDescription;
    }

    public void setConditionDescription(String conditionDescription) {
        this.conditionDescription = conditionDescription;
    }

    public Integer getPoints() {
        return points;
    }

    public void setPoints(Integer points) {
        this.points = points;
    }

    public Boolean getActive() {
        return active;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }

    /**
     * Строковое представление объекта для отладки
     * Показывает все поля правила скоринга
     *
     * @return строковое представление объекта ScoringRule
     */
    @Override
    public String toString() {
        return "ScoringRule{" +
                "id=" + id +
                ", ruleName='" + ruleName + '\'' +
                ", conditionDescription='" + conditionDescription + '\'' +
                ", points=" + points +
                ", active=" + active +
                '}';
    }
}