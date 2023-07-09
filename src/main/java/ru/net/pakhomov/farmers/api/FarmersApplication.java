package ru.net.pakhomov.farmers.api;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;

@SpringBootApplication
public class FarmersApplication extends SpringBootServletInitializer {

    public static void main(String[] args) {
        SpringApplication.run(FarmersApplication.class, args);
    }

}
