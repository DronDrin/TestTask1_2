package ru.drondrin;

import org.apache.catalina.LifecycleException;
import org.apache.catalina.startup.Tomcat;
import ru.drondrin.repository.FileInfoRepository;

import java.io.File;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Main {
    public static final ConfigReader CONFIG = new ConfigReader("src/main/resources/config.properties");
    public static FileInfoRepository fileInfoRepository;

    public static void main(String[] args) {
        var tomcat = new Tomcat();
        tomcat.setPort(CONFIG.intProperty("server.port"));
        tomcat.getConnector().setPort(CONFIG.intProperty("server.port"));
        tomcat.addWebapp("", new File("src/main/resources/").getAbsolutePath());

        try {
            var connection = DriverManager.getConnection(CONFIG.stringProperty("db.url"),
                    CONFIG.stringProperty("db.user"), CONFIG.stringProperty("db.password"));
            connection.createStatement().executeUpdate("CREATE SCHEMA IF NOT EXISTS " + CONFIG.stringProperty("db.name"));
            fileInfoRepository = new FileInfoRepository(connection);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        try {
            tomcat.init();
            tomcat.start();
            System.out.println("Tomcat running.");
            tomcat.getServer().await();
        } catch (LifecycleException e) {
            System.err.println("Tomcat failed to initialize.");
            System.err.println(e.getMessage());
        }
    }
}