package com.advertis;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

@SpringBootApplication
@EnableWebMvc
public class AdvertisApplication {

	public static void main(String[] args) {
		SpringApplication.run(AdvertisApplication.class, args);
	}

	@Bean
	public RestTemplate restTemplate() {

		return new RestTemplate();

	}

}
