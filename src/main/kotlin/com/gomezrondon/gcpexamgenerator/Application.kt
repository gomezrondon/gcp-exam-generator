package com.gomezrondon.gcpexamgenerator

import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Value
import org.springframework.boot.CommandLineRunner
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import java.io.File
import java.nio.file.Path
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter


@SpringBootApplication
class Application(private val service:GenerateQuestionService):CommandLineRunner{

    val LOG: Logger = LoggerFactory.getLogger(Application::class.java);

    @Value("\${test.generator.execute-at-start:true}")
    val executeAtStart:Boolean = true

    override fun run(vararg args: String?) {

        if (executeAtStart) {
            val questionsList = service.loadQuestions("questions.txt", "answers.txt")
/*
            println("There are ${questionsList.size} Questions!")
            println("")
            print("Select number of Questions: 5, 10, 20 ...: ");
*/

            LOG.info("There are ${questionsList.size} Questions!")
            LOG.info("")
            LOG.info("Select number of Questions: 5, 10, 20 ...: ");

            val numberOption = readLine().toString().toLowerCase()
            LOG.info("Number Of questions: $numberOption \n")

            var questions = service.generateQuestion(questionsList as MutableList<Question>, numberOption.toInt(), true);
            val responses = service.askQuestions(questions)
            val results = service.evaluateResults(questions, responses)

            //printing results

            val output = results.joinToString("\n")

           // println(output)
            LOG.info(output)

        }


    }

    private fun getFileName(numberOption: String): String {
        val formatter: DateTimeFormatter? = DateTimeFormatter.ofPattern("yyyy-MM-dd HHmmss")
        val localDateTime = LocalDateTime.now().format(formatter)
        val fileName = "gcp_exam_${numberOption}_$localDateTime.txt"
        return fileName
    }

}

fun main(args: Array<String>) {
	runApplication<Application>(*args)
}


