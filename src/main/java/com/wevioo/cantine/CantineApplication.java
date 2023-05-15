package com.wevioo.cantine;

import com.wevioo.cantine.config.LocalDateConverter;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;


@SpringBootApplication
public class CantineApplication {

    public static void main(String[] args) {
        SpringApplication.run(CantineApplication.class, args);
    }

}
