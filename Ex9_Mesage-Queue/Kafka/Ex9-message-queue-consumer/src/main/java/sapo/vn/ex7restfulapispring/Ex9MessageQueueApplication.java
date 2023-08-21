package sapo.vn.ex7restfulapispring;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.kafka.annotation.EnableKafka;

@SpringBootApplication
@EnableKafka
public class Ex9MessageQueueApplication {

    public static void main(String[] args) {
        SpringApplication.run(Ex9MessageQueueApplication.class, args);
    }

}
