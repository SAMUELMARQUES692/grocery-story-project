package dev.samuel.projectgrocery;

import org.springframework.boot.SpringApplication;

public class TestProjectGroceryStoryApplication {

	public static void main(String[] args) {
		SpringApplication.from(ProjectGroceryStoryApplication::main).with(TestcontainersConfiguration.class).run(args);
	}

}
