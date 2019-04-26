package org.activiti.cloud.definitiondeployer.testapp;

import org.activiti.cloud.starter.rb.configuration.ActivitiRuntimeBundle;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@ActivitiRuntimeBundle
public class TestApp {

    public static void main(String[] args) {
        SpringApplication.run(TestApp.class, args);
    }
}
