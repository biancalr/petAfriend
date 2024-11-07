package com.pets.petAfriend;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableJpaRepositories
@EnableScheduling
@SpringBootApplication
public class PetAfriendApplication {

	public static void main(String[] args) {
		SpringApplication.run(PetAfriendApplication.class, args);
	}

}
