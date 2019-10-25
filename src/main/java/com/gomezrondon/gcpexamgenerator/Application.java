package com.gomezrondon.gcpexamgenerator;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellComponent;

import java.util.List;


@SpringBootApplication
public class Application {

	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}

}

@ShellComponent
class ShellCommand{

	private final GenerateQuestionService service;


	ShellCommand(GenerateQuestionService service) {
		this.service = service;
	}

	private boolean connected;
	@ShellMethod(value = "Select number of Questions: 5, 10, 20",  key = "test")
	public void gcp_questions(int numberOption) {

//		System.out.println(numberOption);
		List<Question> strings = service.readFile();

			service.generateQuestion(strings, numberOption);

	}




}

