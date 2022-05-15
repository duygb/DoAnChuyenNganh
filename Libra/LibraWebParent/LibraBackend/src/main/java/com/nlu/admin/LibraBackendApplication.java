package com.nlu.admin;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;

@SpringBootApplication
@EntityScan({"com.nlu.common.entity", "com.nlu.admin.user"})
public class LibraBackendApplication {

    public static void main(String[] args) {
        SpringApplication.run(LibraBackendApplication.class, args);
    }

}
