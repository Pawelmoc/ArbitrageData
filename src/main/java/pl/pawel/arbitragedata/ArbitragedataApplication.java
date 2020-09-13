package pl.pawel.arbitragedata;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class ArbitragedataApplication {

    public static void main(String[] args) {
        SpringApplication.run(ArbitragedataApplication.class, args);

    }

}
