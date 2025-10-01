package com.example.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.example.demo.model.User;
import com.example.demo.repository.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class DemoApplication {

	public static void main(String[] args) {
		SpringApplication.run(DemoApplication.class, args);
	}

	@Bean
	public CommandLineRunner demo(UserRepository userRepository) {
		return (args) -> {
			// Save a few users
			userRepository.save(new User("Alice", "alice@example.com" ));
			userRepository.save(new User("Bob", "bob@example.com"));

			// Fetch all users
			System.out.println("Users found with findAll():");
			for (User user : userRepository.findAll()) {
				System.out.println(user.getName() + " - " + user.getEmail());
			}
		};
	}

}
