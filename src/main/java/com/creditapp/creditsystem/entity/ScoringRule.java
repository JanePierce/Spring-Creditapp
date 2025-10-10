package com.creditapp.creditsystem.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "scoring_rules")
public class ScoringRule {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "rule_name", nullable = false, length = 200)
    private String ruleName;

    @Column(name = "condition_description", nullable = false, length = 500)
    private String conditionDescription;

    @Column(nullable = false)
    private Integer points;

    @Column(nullable = false)
    private Boolean active = true;

    // Конструкторы
    public ScoringRule() {}

    public ScoringRule(String ruleName, String conditionDescription, Integer points) {
        this.ruleName = ruleName;
        this.conditionDescription = conditionDescription;
        this.points = points;
        this.active = true;
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

