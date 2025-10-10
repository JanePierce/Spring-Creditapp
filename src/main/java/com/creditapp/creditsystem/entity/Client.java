package com.creditapp.creditsystem.entity;

import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "clients")
public class Client {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "first_name", nullable = false, length = 100)
    private String firstName;

    @Column(name = "last_name", nullable = false, length = 100)
    private String lastName;

    @Column(name = "passport_data", nullable = false, length = 20, unique = true)
    private String passportData;

    @Column(name = "phone_number", length = 15)
    private String phoneNumber;

    @Column(length = 100)
    private String email;

    @Column(name = "created_date")
    private LocalDateTime createdDate;

    // Связь один ко многим с заявками
    @OneToMany(mappedBy = "client", cascade = CascadeType.ALL)
    private List<CreditApplication> applications = new ArrayList<>();

    //Конструкторы
    public Client(){
        this.createdDate = LocalDateTime.now();
    }

    public Client(String firstName, String lastName, String passportData) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.passportData = passportData;
    }

    //Геттеры и сеттеры

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getPassportData() {
        return passportData;
    }

    public void setPassportData(String passportData) {
        this.passportData = passportData;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public LocalDateTime getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(LocalDateTime createdDate) {
        this.createdDate = createdDate;
    }

    public List<CreditApplication> getApplications() {
        return applications;
    }

    public void setApplications(List<CreditApplication> applications) {
        this.applications = applications;
    }

    //Метод для добавления заявки
    public void addApplication(CreditApplication application){
        applications.add(application);
        application.setClient(this);
    }

    // Метод для отладки
    @Override
    public String toString() {
        return "Client{" +
                "id=" + id +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", passportData='" + passportData + '\'' +
                ", phoneNumber='" + phoneNumber + '\'' +
                ", email='" + email + '\'' +
                ", createdDate=" + createdDate +
                '}';
    }


}
