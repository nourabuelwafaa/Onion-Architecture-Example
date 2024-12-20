package com.example;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;
import org.springframework.modulith.core.ApplicationModules;

@SpringBootApplication
@ConfigurationPropertiesScan(basePackageClasses = OnionArchitectureExample.class)
public class OnionArchitectureExample {

    public static void main(String[] args) {
        ApplicationModules.of(OnionArchitectureExample.class).verify();
        SpringApplication.run(OnionArchitectureExample.class, args);
    }
}
