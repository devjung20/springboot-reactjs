package sapo.vn.ex5dbspringboot;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
public class Ex5dbspringbootApplication {

    public static void main(String[] args) {
        SpringApplication.run(Ex5dbspringbootApplication.class, args);

        MenuApplication menuApplication = new MenuApplication();
        try {
            menuApplication.run();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
