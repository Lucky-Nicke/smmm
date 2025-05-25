package club.lanxige.smmm;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class SmmmApplication {
    public static void main(String[] args) {
        SpringApplication.run(SmmmApplication.class, args);
    }
}