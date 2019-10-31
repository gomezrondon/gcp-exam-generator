package com.gomezrondon.gcpexamgenerator

import org.springframework.beans.factory.annotation.Value
import org.springframework.boot.CommandLineRunner
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication


@SpringBootApplication
class Application(private val service:GenerateQuestionService):CommandLineRunner{

    @Value("\${test.generator.execute-at-start:true}")
    val executeAtStart:Boolean = true

    override fun run(vararg args: String?) {

        if (executeAtStart) {
            val questionsList = service.loadQuestions("questions.txt","answers.txt")
            println("There are ${questionsList.size} Questions!")
            println("")
            print("Select number of Questions: 5, 10, 20 ...: ");
            val numberOption = readLine().toString().toLowerCase()

            var questions = service.generateQuestion(questionsList as MutableList<Question>, numberOption.toInt(), true);
            val responses = service.askQuestions(questions)
            val results = service.evaluateResults(questions, responses)

            //printing results
            results.forEach { println(it) }
        }


    }

}

fun main(args: Array<String>) {
	runApplication<Application>(*args)
}


