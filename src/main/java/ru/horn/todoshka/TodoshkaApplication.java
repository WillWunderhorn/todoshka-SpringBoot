package ru.horn.todoshka;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

@SpringBootApplication
public class TodoshkaApplication {

    public static void main(String[] args) {
        SpringApplication.run(TodoshkaApplication.class, args);
    }

}
