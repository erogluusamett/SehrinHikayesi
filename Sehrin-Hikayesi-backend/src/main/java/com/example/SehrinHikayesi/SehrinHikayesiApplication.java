package com.example.SehrinHikayesi;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@SpringBootApplication

public class SehrinHikayesiApplication {

	public static void main(String[] args) {
		SpringApplication.run(SehrinHikayesiApplication.class, args);

		System.out.println(new BCryptPasswordEncoder().encode("admin123"));
	}

}
