package com.creditapp.creditsystem.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import com.creditapp.creditsystem.entity.Client;

import java.util.List;
import java.util.Optional;

/**
 * Репозиторий для работы с сущностью Client (Клиент)
 * Расширяет JpaRepository для получения стандартных CRUD операций
 * Содержит специализированные методы для поиска клиентов по различным критериям
 */
@Repository
public interface ClientRepository extends JpaRepository<Client, Long> {

    /**
     * Поиск клиента по паспортным данным
     *
     * @param passportData паспортные данные для поиска
     * @return Optional с клиентом, если найден, иначе Optional.empty()
     */
    Optional<Client> findByPassportData(String passportData);

    /**
     * Поиск клиента по email адресу
     *
     * @param email email адрес для поиска
     * @return Optional с клиентом, если найден, иначе Optional.empty()
     */
    Optional<Client> findByEmail(String email);

    /**
     * Поиск клиентов по фамилии (без учета регистра)
     * Использует частичное совпадение (LIKE %lastName%)
     *
     * @param lastName фамилия или часть фамилии для поиска
     * @return список клиентов с подходящей фамилией
     */
    List<Client> findByLastNameContainingIgnoreCase(String lastName);

    /**
     * Поиск клиентов по имени и фамилии (без учета регистра)
     * Использует частичное совпадение для обоих полей
     *
     * @param firstName имя или часть имени для поиска
     * @param lastName фамилия или часть фамилии для поиска
     * @return список клиентов с подходящими именем и фамилией
     */
    List<Client> findByFirstNameContainingIgnoreCaseAndLastNameContainingIgnoreCase(String firstName, String lastName);

    /**
     * Поиск клиента по ID с загрузкой связанных заявок (жадная загрузка)
     * LEFT JOIN FETCH предотвращает проблему N+1 запросов при доступе к заявкам
     *
     * @param id идентификатор клиента
     * @return Optional с клиентом и его заявками, если найден
     */
    @Query("SELECT c FROM Client c LEFT JOIN FETCH c.applications WHERE c.id = :id")
    Optional<Client> findByIdWithApplications(@Param("id") Long id);

    /**
     * Проверка существования клиента с указанными паспортными данными
     * Используется для валидации при создании нового клиента
     *
     * @param passportData паспортные данные для проверки
     * @return true если клиент с такими паспортными данными уже существует
     */
    boolean existsByPassportData(String passportData);
}