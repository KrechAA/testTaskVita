package com.krech.vita;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;


@SpringBootApplication (scanBasePackages = {"com.krech.vita.repository", "com.krech.vita.service", "com.krech.vita.controller", "com.krech.vita.config"})

public class App {



    public static void main(String[] args) {

        SpringApplication.run(App.class);

    }

}
