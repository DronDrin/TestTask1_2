package ru.drondrin.test.db;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.drondrin.DB;
import ru.drondrin.entity.FileInfo;
import ru.drondrin.test.ConfiguredTest;
import ru.drondrin.repository.FileInfoRepository;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

public class FileInfoRepositoryTests extends ConfiguredTest {
    private Connection connection;
    private FileInfoRepository fileInfoRepository;

    @BeforeEach
    public void setUp() {
        connection = DB.createNewConnection();
        fileInfoRepository = new FileInfoRepository(connection);
    }

    @AfterEach
    public void cleanUp() throws SQLException {
        connection.close();
    }

    @Test
    public void simpleSaveLoadTest() {
        var testName = "testName";
        FileInfo savedFileInfo = fileInfoRepository.save(testName);
        Optional<FileInfo> fileInfo = fileInfoRepository.findById(savedFileInfo.getId());

        assertTrue(fileInfo.isPresent());
        assertEquals(savedFileInfo, fileInfo.get());
        assertEquals(testName, savedFileInfo.getName());
        System.out.println(savedFileInfo);
    }

}
