package com.webapp.arvand.arvandback;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@EnableCaching
@SpringBootApplication
public class ArvandBackApplication {

    public static void main(String[] args) {
        SpringApplication.run(ArvandBackApplication.class, args);
    }

}
