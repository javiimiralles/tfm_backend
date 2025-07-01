package com.backend.backend;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;

import com.backend.backend.initializers.DotenvInitializer;

@SpringBootApplication
public class BackendApplication {

	public static void main(String[] args) {
		new SpringApplicationBuilder(BackendApplication.class)
			.initializers(new DotenvInitializer())
			.run(args);
	}

}
