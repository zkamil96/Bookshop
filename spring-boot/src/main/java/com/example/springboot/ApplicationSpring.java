package com.example.springboot;


import com.example.javafx.BookshopApplication;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;


@SpringBootApplication
public class ApplicationSpring {

    public static void main(String[] args) {
        SpringApplication.run(ApplicationSpring.class, args);
        BookshopApplication.main(null);
    }

}
