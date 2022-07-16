package com.nlu.libra;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;

@SpringBootApplication
@EntityScan({"com.nlu.common.entity"})
public class LibraFrontendApplication {

    public static void main(String[] args) {
        SpringApplication.run(LibraFrontendApplication.class, args);
    }

}
