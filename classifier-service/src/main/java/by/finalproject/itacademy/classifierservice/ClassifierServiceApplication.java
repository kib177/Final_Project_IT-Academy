package by.finalproject.itacademy.classifierservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients("by.finalproject.itacademy.classifierservice.feign")
public class ClassifierServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(ClassifierServiceApplication.class, args);
    }

}
