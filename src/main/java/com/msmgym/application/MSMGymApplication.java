package com.msmgym.application;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
@EnableJpaRepositories(basePackages = {"com.msmgym.application"})
@ComponentScan(basePackages = {"com.msmgym.application"})
public class MSMGymApplication {

    public static void main(String[] args) {
        SpringApplication.run(MSMGymApplication.class, args);
    }

}
