package com.project.qrdrive.starter;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EnableJpaRepositories("com.project")
@ComponentScan(basePackages = "com.project.qrdrive")
@EntityScan("com.project")
public class QrDriveApplication {

    public static void main(String[] args) {
        SpringApplication.run(QrDriveApplication.class, args);
    }

}
