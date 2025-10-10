package com.creditapp.creditsystem.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
public class PostgresTestController {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @GetMapping("/test-postgres")
    public String testPostgres() {
        try {
            // Проверяем версию PostgreSQL
            String version = jdbcTemplate.queryForObject("SELECT version()", String.class);
            System.out.println("✅ PostgreSQL версия: " + version);

            // Проверяем таблицы
            List<Map<String, Object>> clients = jdbcTemplate.queryForList("SELECT COUNT(*) as count FROM clients");
            List<Map<String, Object>> applications = jdbcTemplate.queryForList("SELECT COUNT(*) as count FROM credit_applications");
            List<Map<String, Object>> rules = jdbcTemplate.queryForList("SELECT COUNT(*) as count FROM scoring_rules");

            long clientCount = (Long) clients.get(0).get("count");
            long applicationCount = (Long) applications.get(0).get("count");
            long ruleCount = (Long) rules.get(0).get("count");

            System.out.println("✅ PostgreSQL база данных проверена:");
            System.out.println("   Клиентов: " + clientCount);
            System.out.println("   Заявок: " + applicationCount);
            System.out.println("   Правил: " + ruleCount);

            return String.format(
                    "<h2>✅ PostgreSQL работает!</h2>" +
                            "<p><strong>Версия:</strong> %s</p>" +
                            "<p><strong>👥 Клиентов:</strong> %d</p>" +
                            "<p><strong>📄 Заявок:</strong> %d</p>" +
                            "<p><strong>📊 Правил:</strong> %d</p>" +
                            "<p>База данных: <strong>credit_db</strong></p>",
                    version, clientCount, applicationCount, ruleCount
            );

        } catch (Exception e) {
            System.out.println("❌ Ошибка подключения к PostgreSQL: " + e.getMessage());
            e.printStackTrace();
            return "<h2>❌ Ошибка подключения к PostgreSQL</h2>" +
                    "<p><strong>Ошибка:</strong> " + e.getMessage() + "</p>" +
                    "<p>Проверьте:</p>" +
                    "<ul>" +
                    "<li>Запущен ли PostgreSQL</li>" +
                    "<li>Правильный ли пароль в application.properties</li>" +
                    "<li>Существует ли база данных 'credit_db'</li>" +
                    "</ul>";
        }
    }

    @GetMapping("/test-data")
    public String testData() {
        try {
            // Показываем пример данных
            List<Map<String, Object>> recentApplications = jdbcTemplate.queryForList(
                    "SELECT c.first_name, c.last_name, ca.amount, ca.status " +
                            "FROM credit_applications ca " +
                            "JOIN clients c ON ca.client_id = c.id " +
                            "ORDER BY ca.creation_date DESC LIMIT 5"
            );

            StringBuilder result = new StringBuilder();
            result.append("<h3>📋 Последние заявки:</h3>");
            result.append("<table border='1' style='border-collapse: collapse;'>");
            result.append("<tr><th>Клиент</th><th>Сумма</th><th>Статус</th></tr>");

            for (Map<String, Object> app : recentApplications) {
                result.append("<tr>")
                        .append("<td>").append(app.get("first_name")).append(" ").append(app.get("last_name")).append("</td>")
                        .append("<td>").append(app.get("amount")).append(" руб.</td>")
                        .append("<td>").append(app.get("status")).append("</td>")
                        .append("</tr>");
            }
            result.append("</table>");

            return result.toString();

        } catch (Exception e) {

            return "❌ Ошибка при получении данных: " + e.getMessage();
        }
    }
}


