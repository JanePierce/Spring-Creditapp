package com.creditapp.creditsystem.dto;

import java.math.BigDecimal;

public class CreditApplicationRequest {
    private String passportData;
    private BigDecimal amount;

    // Конструкторы
    public CreditApplicationRequest() {}

    public CreditApplicationRequest(String passportData, BigDecimal amount) {
        this.passportData = passportData;
        this.amount = amount;
    }

    // Геттеры и сеттеры
    public String getPassportData() {
        return passportData;
    }

    public void setPassportData(String passportData) {
        this.passportData = passportData;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    @Override
    public String toString() {
        return "CreditApplicationRequest{" +
                "passportData='" + passportData + '\'' +
                ", amount=" + amount +
                '}';
    }
}
