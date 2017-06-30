package com.tektrill.vaadin.boot;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.support.SpringBootServletInitializer;

@SpringBootApplication
public class VaadinBootApplication extends SpringBootServletInitializer {
	private static Class<VaadinBootApplication> applicationClass = VaadinBootApplication.class;

	public static void main(String[] args) {
		SpringApplication.run(VaadinBootApplication.class, args);
	}

	@Override
	protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
		return application.sources(applicationClass);
	}
}
