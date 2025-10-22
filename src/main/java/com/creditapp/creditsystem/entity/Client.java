package com.creditapp.creditsystem.entity;

import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * Сущность Клиент (Client)
 * JPA entity для представления таблицы клиентов в базе данных
 * Содержит персональные данные клиента и связь с кредитными заявками
 */
@Entity
@Table(name = "clients") // Указывает имя таблицы в базе данных
public class Client {

    /**
     * Уникальный идентификатор клиента
     * Стратегия IDENTITY использует автоинкремент базы данных
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * Имя клиента
     * Обязательное поле, максимальная длина 100 символов
     */
    @Column(name = "first_name", nullable = false, length = 100)
    private String firstName;

    /**
     * Фамилия клиента
     * Обязательное поле, максимальная длина 100 символов
     */
    @Column(name = "last_name", nullable = false, length = 100)
    private String lastName;

    /**
     * Паспортные данные клиента
     * Обязательное поле, уникальное значение, длина 20 символов
     * Используется для идентификации клиента в системе
     */
    @Column(name = "passport_data", nullable = false, length = 20, unique = true)
    private String passportData;

    /**
     * Номер телефона клиента
     * Необязательное поле, длина 15 символов (международный формат)
     */
    @Column(name = "phone_number", length = 15)
    private String phoneNumber;

    /**
     * Электронная почта клиента
     * Необязательное поле, длина 100 символов
     */
    @Column(length = 100)
    private String email;

    /**
     * Дата и время создания записи о клиенте
     * Автоматически устанавливается при создании объекта
     */
    @Column(name = "created_date")
    private LocalDateTime createdDate;

    /**
     * Связь один ко многим с кредитными заявками
     * Один клиент может иметь несколько кредитных заявок
     * mappedBy указывает поле в сущности CreditApplication, которое владеет связью
     * cascade = CascadeType.ALL - операции сохраняются/удаляются для связанных заявок
     * Инициализируется пустым ArrayList для избежания NullPointerException
     */
    @OneToMany(mappedBy = "client", cascade = CascadeType.ALL)
    private List<CreditApplication> applications = new ArrayList<>();

    // Конструкторы

    /**
     * Конструктор по умолчанию
     * Требуется JPA/Hibernate для создания proxy-объектов
     * Автоматически устанавливает дату создания
     */
    public Client() {
        this.createdDate = LocalDateTime.now(); // Устанавливаем текущую дату и время
    }

    /**
     * Параметризованный конструктор для обязательных полей
     * Используется при создании нового клиента с минимальными данными
     *
     * @param firstName имя клиента
     * @param lastName фамилия клиента
     * @param passportData паспортные данные (уникальный идентификатор)
     */
    public Client(String firstName, String lastName, String passportData) {
        this(); // Вызов конструктора по умолчанию для установки createdDate
        this.firstName = firstName;
        this.lastName = lastName;
        this.passportData = passportData;
    }

    // Геттеры и сеттеры

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

    /**
     * Получить список кредитных заявок клиента
     *
     * @return неизменяемый список заявок (для безопасности)
     */
    public List<CreditApplication> getApplications() {
        return applications;
    }

    /**
     * Установить список кредитных заявок
     * Используется осторожно, обычно предпочтительнее addApplication()
     *
     * @param applications список заявок
     */
    public void setApplications(List<CreditApplication> applications) {
        this.applications = applications;
    }

    // Бизнес-методы

    /**
     * Добавить кредитную заявку для клиента
     * Устанавливает двустороннюю связь между клиентом и заявкой
     *
     * @param application кредитная заявка для добавления
     */
    public void addApplication(CreditApplication application) {
        applications.add(application); // Добавляем заявку в список клиента
        application.setClient(this); // Устанавливаем клиента для заявки
    }

    /**
     * Удалить кредитную заявку у клиента
     *
     * @param application кредитная заявка для удаления
     */
    public void removeApplication(CreditApplication application) {
        applications.remove(application); // Удаляем заявку из списка
        application.setClient(null); // Убираем ссылку на клиента
    }

    /**
     * Полное имя клиента (имя + фамилия)
     *
     * @return полное имя клиента
     */
    public String getFullName() {
        return firstName + " " + lastName;
    }

    // Метод для отладки и логирования

    /**
     * Строковое представление объекта для отладки
     * Не включает список заявок чтобы избежать циклических ссылок
     *
     * @return строковое представление объекта Client
     */
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