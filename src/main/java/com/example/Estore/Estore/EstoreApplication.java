package com.example.Estore.Estore;

import com.example.Estore.Estore.Security.AppProperties;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

/**
 * Initiates Java-based Spring configuration, component scanning, and enable auto-configuration feature.
 */
@SpringBootApplication
public class EstoreApplication {

	/**
	 * Starts up the Spring ApplicationContext
	 * @param args args
	 */
	public static void main(String[] args) {
		SpringApplication.run(EstoreApplication.class, args);
	}

	/**
	 * Method used to encrypt the password using BCryptPasswordEncoder encoder.
	 * @return BCryptPasswordEncoder
	 */
	@Bean
	public BCryptPasswordEncoder bCryptPasswordEncoder(){
		return new BCryptPasswordEncoder();
	}

	/**
	 * Method used to create SpringApplicationContext.
	 * @return SpringApplicationContext.
	 */
	@Bean
	public SpringApplicationContext springApplicationContext(){
		return new SpringApplicationContext();
	}

	/**
	 * Methods runs AppProperties class to get the token secret used in spring security.
	 * @return AppProperties
	 */
	@Bean(name = "AppProperties")
	public AppProperties getAppProperties(){
		return new AppProperties();
	}
}
