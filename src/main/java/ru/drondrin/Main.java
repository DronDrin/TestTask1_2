package ru.drondrin;

import org.apache.catalina.Context;
import org.apache.catalina.LifecycleException;
import org.apache.catalina.startup.Tomcat;

import java.io.File;

public class Main {
    public static void main(String[] args) {
        var tomcat = new Tomcat();
        tomcat.setPort(8095);
        tomcat.getConnector().setPort(8095);
        Context context = tomcat.addWebapp("", new File(".").getAbsolutePath());

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