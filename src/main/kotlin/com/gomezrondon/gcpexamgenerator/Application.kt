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

            LOG.info(" \n");
            LOG.info("Select  1) for All regular questions. \n");
            LOG.info("          1.1) chapter 6 - Managing Virtual Machines \n");
            LOG.info("          1.2) chapter 7 - Computing with Kubernetes \n");
            LOG.info("        2) for commands questions: ");

            val numberOption = readLine().toString().toLowerCase()
            LOG.info("Option selected: $numberOption \n")
            when (numberOption) {
                "1" -> {
                    val isRandom = false
                    val keepOrder = false
                    val questionsfileName = "questions.txt"
                    val answerfileName = "answers.txt"
                    val questionsList = service.loadQuestions(questionsfileName, answerfileName)
                    val output = generateQuestions(questionsList, questionsfileName, answerfileName, isRandom, keepOrder)
                    LOG.info(output)
                }
                "1.1" -> {
                    val isRandom = false
                    val keepOrder = false
                    val questionsfileName = "questions.txt"
                    val answerfileName = "answers.txt"
                    val questionsList = service.loadSubSetQuestions(questionsfileName, answerfileName,"--- chapter 6","--- chapter 7")
                    val output = generateQuestions(questionsList,questionsfileName, answerfileName, isRandom, keepOrder)
                    LOG.info(output)
                }
                "1.2" -> {
                    val isRandom = false
                    val keepOrder = false
                    val questionsfileName = "questions.txt"
                    val answerfileName = "answers.txt"
                    val questionsList = service.loadSubSetQuestions(questionsfileName, answerfileName,"--- chapter 7","--- chapter 8")
                    val output = generateQuestions(questionsList,questionsfileName, answerfileName, isRandom, keepOrder)
                    LOG.info(output)
                }
                "2" -> {
                    val isRandom = true
                    val questionsfileName = "commands-questions.txt"
                    val answerfileName = "commands-answers.txt"
                    val questionsList: List<Question> = service.loadQuestions(questionsfileName, answerfileName)
                    val output = generateQuestions(questionsList, questionsfileName, answerfileName, isRandom)
                    LOG.info(output)
                }
            }



        }


    }

    private fun generateQuestions(questionsList: List<Question>, questionsfileName: String, answerfileName: String, isRandom:Boolean, keepOrder:Boolean=true): String {

        LOG.info("There are ${questionsList.size} Questions! \n")
        LOG.info(" \n")
        LOG.info("Select number of Questions: 5, 10, 20 ...: ");

        val numberOption = readLine().toString().toLowerCase()
        LOG.info("Number Of questions: $numberOption \n")

        var questions = service.generateQuestion(questionsList as MutableList<Question>, numberOption.toInt(), isRandom);
        val responses = service.askQuestions(questions)
        val results = service.evaluateResults(questions, responses, keepOrder)

        //printing results

        val output = results.joinToString("\n")
        return output
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


