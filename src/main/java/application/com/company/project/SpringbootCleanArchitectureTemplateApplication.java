package com.company.project;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.company.project.services.product.ProductService;

@SpringBootApplication
public class SpringbootCleanArchitectureTemplateApplication {
	
	public static void main(String[] args) {
		SpringApplication.run(SpringbootCleanArchitectureTemplateApplication.class, args);
	}

}
