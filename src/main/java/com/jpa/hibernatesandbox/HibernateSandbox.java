package com.jpa.hibernatesandbox;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.orm.jpa.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@ComponentScan(basePackages = "com.jpa.hibernatesandbox")
@EntityScan(basePackages = {"com.jpa.hibernatesandbox.domain"})
@EnableJpaRepositories(basePackages = {"com.jpa.hibernatesandbox.repositories"})
public class HibernateSandbox {
    public static void main(String[] args) {
        SpringApplication.run(HibernateSandbox.class, args);
    }
}
