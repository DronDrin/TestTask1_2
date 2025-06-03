package ru.drondrin.repository;

import lombok.SneakyThrows;
import ru.drondrin.entity.FileInfo;

import java.sql.Connection;
import java.util.Optional;
import java.util.Random;

import static java.lang.System.nanoTime;

public class FileInfoRepository extends Repository {
    private static final char[] idChars;

    static {
        idChars = new char[63];
        int j = 0;
        for (int i = 0; i < 10; i++)
            idChars[j++] = (char) (i + '0');
        for (int i = 0; i < 26; i++)
            idChars[j++] = (char) (i + 'a');
        for (int i = 0; i < 26; i++)
            idChars[j++] = (char) (i + 'A');
        idChars[j] = '_';
    }

    public FileInfoRepository(Connection connection) {
        super(connection, "Files", """
                CREATE TABLE IF NOT EXISTS __TABLE_NAME__\
                (id VARCHAR(10) PRIMARY KEY,\
                 fileName VARCHAR(64) NOT NULL,\
                 created TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP(),\
                 lastDownload TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP())""");
    }

    public String generateId() {
        StringBuilder id;
        do {
            id = new StringBuilder();
            var rnd = new Random(nanoTime());
            for (int i = 0; i < 10; i++)
                id.append(idChars[rnd.nextInt(idChars.length)]);
        } while (exists(id.toString()));
        return id.toString();
    }

    @SneakyThrows
    public boolean exists(String id) {
        String sql = "SELECT COUNT(*) AS cnt FROM " + getTableName() + " WHERE id = ?";
        var statement = connection.prepareStatement(sql);
        statement.setString(1, id);
        var result = statement.executeQuery();
        var cnt = result.next() ? result.getInt("cnt") : 0;
        return cnt > 0;
    }

    @SneakyThrows
    public Optional<FileInfo> findById(String id) {
        String sql = "SELECT * FROM " + getTableName() + " WHERE id = ?";
        var statement = connection.prepareStatement(sql);
        statement.setString(1, id);
        var resultSet = statement.executeQuery();
        if (resultSet.next()) {
            return Optional.of(FileInfo.builder()
                    .id(resultSet.getString("id"))
                    .name(resultSet.getString("fileName"))
                    .createdTimestamp(resultSet.getTimestamp("created"))
                    .lastDownloadTimestamp(resultSet.getTimestamp("lastDownload"))
                    .build());
        } else
            return Optional.empty();
    }

    @SneakyThrows
    public FileInfo save(String name) {
        String sql = "INSERT INTO " + getTableName() + " (id, fileName) VALUES (?,?)";
        var statement = connection.prepareStatement(sql);
        var id = generateId();
        statement.setString(1, id);
        statement.setString(2, name);
        statement.executeUpdate();
        connection.commit();
        var fileInfo = findById(id);
        return fileInfo.orElseThrow(() -> new RuntimeException("Just saved file wasn't found"));
    }
}
