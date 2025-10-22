package com.creditapp.creditsystem.dto;

import java.math.BigDecimal;

/**
 * DTO (Data Transfer Object) для создания кредитной заявки
 * Используется для передачи данных между слоями приложения (контроллер ↔ сервис)
 * Содержит только необходимые данные для создания заявки, без бизнес-логики
 */
public class CreditApplicationRequest {
    /**
     * Паспортные данные клиента
     * Используются для идентификации клиента в системе
     * Формат: серия и номер паспорта (например, "4510 123456")
     */
    private String passportData;

    /**
     * Запрашиваемая сумма кредита
     * Используется BigDecimal для точных финансовых расчетов
     * floating-point ошибок при операциях с деньгами
     */
    private BigDecimal amount;

    // Конструкторы

    /**
     * Конструктор по умолчанию
     * Требуется для фреймворков (Spring, Jackson) при десериализации JSON
     */
    public CreditApplicationRequest() {}

    /**
     * Параметризованный конструктор
     * Используется для удобного создания объектов в коде
     *
     * @param passportData паспортные данные клиента
     * @param amount запрашиваемая сумма кредита
     */
    public CreditApplicationRequest(String passportData, BigDecimal amount) {
        this.passportData = passportData;
        this.amount = amount;
    }

    // Геттеры и сеттеры

    /**
     * Получить паспортные данные
     *
     * @return паспортные данные клиента
     */
    public String getPassportData() {
        return passportData;
    }

    /**
     * Установить паспортные данные
     *
     * @param passportData паспортные данные клиента
     */
    public void setPassportData(String passportData) {
        this.passportData = passportData;
    }

    /**
     * Получить запрашиваемую сумму кредита
     *
     * @return сумма кредита как BigDecimal
     */
    public BigDecimal getAmount() {
        return amount;
    }

    /**
     * Установить запрашиваемую сумму кредита
     *
     * @param amount сумма кредита, должна быть положительной
     */
    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    /**
     * Строковое представление объекта
     * Используется для логирования и отладки
     *
     * @return строковое представление объекта с маскированными паспортными данными
     */
    @Override
    public String toString() {
        return "CreditApplicationRequest{" +
                "passportData='" + maskPassportData(passportData) + '\'' +
                ", amount=" + amount +
                '}';
    }

    /**
     * Вспомогательный метод для маскирования конфиденциальных данных
     * Скрывает часть паспортных данных для безопасности в логах
     *
     * @param passportData исходные паспортные данные
     * @return маскированные паспортные данные
     */
    private String maskPassportData(String passportData) {
        if (passportData == null || passportData.length() < 4) {
            return "****";
        }
        // Оставляем только первые 4 символа, остальные заменяем на *
        return passportData.substring(0, 4) + "******";
    }
}