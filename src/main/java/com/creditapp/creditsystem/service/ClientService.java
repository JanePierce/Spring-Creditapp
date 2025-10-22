package com.creditapp.creditsystem.service;

import com.creditapp.creditsystem.entity.Client;
import com.creditapp.creditsystem.repository.ClientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ClientService {

    @Autowired
    private ClientRepository clientRepository;

    // Создание нового клиента
    public Client createClient(Client client) {
        System.out.println("🆕 Создание клиента: " + client.getFirstName() + " " + client.getLastName());

        // Проверяем, нет ли уже клиента с таким паспортом
        if (clientRepository.existsByPassportData(client.getPassportData())) {
            throw new RuntimeException("❌ Клиент с паспортом " + client.getPassportData() + " уже существует");
        }

        Client savedClient = clientRepository.save(client);
        System.out.println("✅ Клиент создан с ID: " + savedClient.getId());
        return savedClient;
    }

    // Получение клиента по ID
    public Optional<Client> getClientById(Long id) {
        System.out.println("🔍 Поиск клиента по ID: " + id);
        return clientRepository.findById(id);
    }

    // Получение клиента по паспортным данным
    public Optional<Client> getClientByPassport(String passportData) {
        System.out.println("🔍 Поиск клиента по паспорту: " + passportData);
        return clientRepository.findByPassportData(passportData);
    }

    // Получение всех клиентов
    public List<Client> getAllClients() {
        System.out.println("📋 Получение всех клиентов");
        return clientRepository.findAll();
    }

    // Поиск клиентов по фамилии
    public List<Client> searchClientsByLastName(String lastName) {
        System.out.println("🔍 Поиск клиентов по фамилии: " + lastName);
        return clientRepository.findByLastNameContainingIgnoreCase(lastName);
    }

    // Обновление клиента
    public Client updateClient(Long id, Client clientDetails) {
        System.out.println("✏️ Обновление клиента с ID: " + id);

        Client existingClient = clientRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("❌ Клиент с ID " + id + " не найден"));

        // Обновляем поля
        existingClient.setFirstName(clientDetails.getFirstName());
        existingClient.setLastName(clientDetails.getLastName());
        existingClient.setPhoneNumber(clientDetails.getPhoneNumber());
        existingClient.setEmail(clientDetails.getEmail());

        Client updatedClient = clientRepository.save(existingClient);
        System.out.println("✅ Клиент обновлен: " + updatedClient.getFirstName() + " " + updatedClient.getLastName());
        return updatedClient;
    }

    // Удаление клиента
    public void deleteClient(Long id) {
        System.out.println("🗑 Удаление клиента с ID: " + id);

        if (!clientRepository.existsById(id)) {
            throw new RuntimeException("❌ Клиент с ID " + id + " не найден");
        }

        clientRepository.deleteById(id);
        System.out.println("✅ Клиент удален");
    }

    // Получение клиента с его заявками
    public Optional<Client> getClientWithApplications(Long id) {
        System.out.println("📄 Получение клиента с заявками по ID: " + id);
        return clientRepository.findByIdWithApplications(id);
    }
}
