package ru.drondrin.repository;

import java.sql.Connection;
import java.sql.SQLException;

public abstract class Repository {
    protected final Connection connection;
    private final String tableName;

    protected Repository(Connection connection, String tableName, String tableCreationSQL) {
        this.connection = connection;
        this.tableName = tableName;
        try {
            //noinspection SqlSourceToSinkFlow       REASON:   called without user input
            connection.createStatement().executeUpdate(tableCreationSQL.replaceAll("__TABLE_NAME__", tableName));
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    protected String getTableName() {
        return tableName;
    }
}
