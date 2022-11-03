package com.mino.springlab;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableScheduling
@SpringBootApplication
public class RabbitMQApplication {
//public class RabbitMQApplication implements CommandLineRunner {

//    @Autowired
//    private EmployeeJsonProducer producer;

    public static void main(String[] args) {
        SpringApplication.run(RabbitMQApplication.class, args);
    }

//    @Override
//    public void run(String... args) throws Exception {
//        for (int i = 0; i < 5; i++) {
//            var employee = new Employee("emp" + i, "Employee " + i, LocalDate.now());
//            producer.sendMessage(employee);
//        }
//    }
}
