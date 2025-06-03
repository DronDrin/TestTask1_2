package ru.drondrin;

import org.apache.catalina.LifecycleException;
import org.apache.catalina.startup.Tomcat;

import java.io.File;

public class Main {
    public static final ConfigReader CONFIG = new ConfigReader("src/main/resources/config.properties");

    public static void main(String[] args) {
        var tomcat = new Tomcat();
        tomcat.setPort(CONFIG.intProperty("server.port"));
        tomcat.getConnector().setPort(CONFIG.intProperty("server.port"));
        tomcat.addWebapp("", new File("src/main/resources/").getAbsolutePath());

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