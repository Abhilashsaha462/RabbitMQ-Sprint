package com.niit.UserMovieService;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@SpringBootApplication
@EnableEurekaClient
public class UserMovieServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(UserMovieServiceApplication.class, args);
	}

}
