package dev.sayem.selis;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EnableJpaRepositories
public class SelisApplication {

	public static void main(String[] args) {
		SpringApplication.run(SelisApplication.class, args);
	}

}
