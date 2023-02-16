package com.spring;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.web.bind.annotation.CrossOrigin;

@SpringBootApplication(exclude = {
	SecurityAutoConfiguration.class,
})
@EntityScan("com.spring.entity")
@EnableJpaRepositories("com.spring.repository")
@CrossOrigin("http://localhost")
public class GoslingApplication {

	public static void main(String[] args) {
		SpringApplication.run(GoslingApplication.class, args);
	}

}
