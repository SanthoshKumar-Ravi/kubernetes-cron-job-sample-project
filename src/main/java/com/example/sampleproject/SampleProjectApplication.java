package com.example.sampleproject;

import com.example.sampleproject.service.SampleService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

@SpringBootApplication
public class SampleProjectApplication implements CommandLineRunner {
    private final SampleService service;
    private final ConfigurableApplicationContext context;

    public SampleProjectApplication(SampleService service, ConfigurableApplicationContext context) {
        this.service = service;
        this.context = context;
    }

    public static void main(String[] args) {
        SpringApplication.run(SampleProjectApplication.class, args);
    }

    @Override
    public void run(String... args) {
        System.out.println("Job start to executed");
        service.display();
        System.out.println("Job Executed successfully");
        context.close();
    }
}