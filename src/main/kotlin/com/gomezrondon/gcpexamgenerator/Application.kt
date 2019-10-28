package com.gomezrondon.gcpexamgenerator

import org.springframework.boot.CommandLineRunner
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication


@SpringBootApplication
class Application(private val service:GenerateQuestionService):CommandLineRunner{

    override fun run(vararg args: String?) {

        val strings = service.readFile()
        println("There are ${strings.size} Questions!")
        println("")
        print("Select number of Questions: 5, 10, 20 ...: ");
        val numberOption = readLine().toString().toLowerCase()

        var questions = service.generateQuestion(strings as MutableList<Question>, numberOption.toInt());
        val responses = service.askQuestions(questions)
        val results = service.evaluateResults(questions, responses)

        //printing results
        results.forEach { println(it) }
    }

}

fun main(args: Array<String>) {
	runApplication<Application>(*args)
}


