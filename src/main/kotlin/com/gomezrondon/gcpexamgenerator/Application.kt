package com.gomezrondon.gcpexamgenerator

import org.springframework.boot.CommandLineRunner
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication


@SpringBootApplication
class Application(private val service:GenerateQuestionService):CommandLineRunner{

    override fun run(vararg args: String?) {
        print("Select number of Questions: 5, 10, 20: ");
        val numberOption = readLine().toString().toLowerCase()
        val strings = service.readFile()
        service.generateQuestion(strings as MutableList<Question>, numberOption.toInt());
    }

}

fun main(args: Array<String>) {
	runApplication<Application>(*args)
}


