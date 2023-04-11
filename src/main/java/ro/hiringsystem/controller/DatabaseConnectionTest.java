package ro.hiringsystem.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
public class DatabaseConnectionTest {
    @Autowired
    private JdbcTemplate jdbcTemplate;

    @GetMapping("/testDatabase")
    public String testDatabaseConnection() {
        try {
            List<Map<String, Object>> result = jdbcTemplate.queryForList("SELECT 1");
            return "Database connection successful!";
        } catch (Exception ex) {
            return "Database connection failed: " + ex.getMessage();
        }
    }
}