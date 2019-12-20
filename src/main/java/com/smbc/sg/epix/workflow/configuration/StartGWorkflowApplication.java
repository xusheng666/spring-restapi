package com.smbc.sg.epix.workflow.configuration;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication(scanBasePackages = "com.smbc")
@EntityScan(basePackages = "com.smbc.sg.epix.workflow.entity")
@EnableJpaRepositories("com.smbc.sg.epix.workflow.dao")
public class StartGWorkflowApplication {

  // start the application
  public static void main(String[] args) {
    SpringApplication.run(StartGWorkflowApplication.class, args);
  }
  
}
