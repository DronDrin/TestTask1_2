package ru.drondrin;

import lombok.SneakyThrows;

import java.sql.Connection;
import java.sql.DriverManager;

import static ru.drondrin.Main.CONFIG;

public class DB {
    @SneakyThrows
    public static Connection createNewConnection() {
        var connection = DriverManager.getConnection(CONFIG.stringProperty("db.url"),
                CONFIG.stringProperty("db.user"), CONFIG.stringProperty("db.password"));
        connection.createStatement().executeUpdate("CREATE SCHEMA IF NOT EXISTS " + CONFIG.stringProperty("db.name"));
        return connection;
    }
}
