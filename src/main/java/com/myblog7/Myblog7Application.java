package com.myblog7;

import org.modelmapper.ModelMapper;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication//this is where execution of project begins,it also make this as configuration class
public class Myblog7Application {

	public static void main(String[] args) {
		SpringApplication.run(Myblog7Application.class, args);
	}
	@Bean
public ModelMapper modelMapper(){
	return new ModelMapper();
}
}
