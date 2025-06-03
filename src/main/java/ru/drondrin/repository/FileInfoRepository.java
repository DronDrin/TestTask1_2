package ru.drondrin.repository;

import java.sql.Connection;

public class FileInfoRepository extends Repository {
    public FileInfoRepository(Connection connection) {
        super(connection, "Files", """
                CREATE TABLE IF NOT EXISTS __TABLE_NAME__\
                (id VARCHAR(10) PRIMARY KEY,\
                 fileName VARCHAR(64) NOT NULL,\
                 created TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP(),\
                 lastDownload TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP())""");
    }


}
