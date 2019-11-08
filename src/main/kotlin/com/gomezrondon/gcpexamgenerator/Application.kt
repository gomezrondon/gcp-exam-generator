package com.gomezrondon.gcpexamgenerator

import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Value
import org.springframework.boot.CommandLineRunner
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter


@SpringBootApplication
class Application(private val service:GenerateQuestionService):CommandLineRunner{

    val LOG: Logger = LoggerFactory.getLogger(Application::class.java);

    @Value("\${test.generator.execute-at-start:true}")
    val executeAtStart:Boolean = true

    override fun run(vararg args: String?) {

        if (executeAtStart) {

            LOG.info("Select  1) for All regular questions. \n");
            LOG.info("          1.1) chapter 1 - Overview of Google Cloud Platform \n");
            LOG.info("          1.2) chapter 2 - Google Cloud Computing Services \n");
            LOG.info("          1.3) chapter 3 - Projects, Service Accounts, and Billing \n");
            LOG.info("          1.4) chapter 4 - Introduction to Computing in Google Cloud \n");
            LOG.info("          1.5) chapter 5 - Compute Engine Virtual Machines \n");
            LOG.info("          1.6) chapter 6 - Managing Virtual Machines \n");
            LOG.info("          1.7) chapter 7 - Computing with Kubernetes \n");
            LOG.info("          1.8) chapter 8 - Managing Kubernetes Clusters \n");
            LOG.info("          1.9) chapter 9 - Computing with App Engine \n");
            LOG.info("          1.10) chapter 10 - Computing with Cloud Functions \n");
            LOG.info("          1.11) chapter 11 - Planning Storage in the Cloud \n");
            LOG.info("          1.12) chapter 12 - Deploying Storage in Google Cloud Platform \n");
            LOG.info("          1.13) chapter 10 - Loading Data into Storages \n");
            LOG.info("          1.14) chapter 14 - Networking in the Cloud: Virtual Private Clouds and  Virtual Private Networks \n");
            LOG.info("          1.15) chapter 15 - Networking in the Cloud: DNS, Load Balancing,  and IP Addressing \n");
            LOG.info("          1.16) chapter 16 - Deploying Applications with Cloud Launcher and  Deployment Manager \n");
            LOG.info("          1.17) chapter 17 - Configuring Access and Security \n");
            LOG.info("          1.18) chapter 18 - Monitoring, Logging, and Cost Estimating \n");
            LOG.info("        2) for Custom Mix questions \n");
            LOG.info("        3) for commands questions: ");

            val numberOption = readLine().toString().toLowerCase()
            LOG.info("Option selected: $numberOption \n")

            val isRandom = true
            val keepOrder = false
            val questionsfileName = "questions.txt"
            val answerfileName = "answers.txt"
            var start = "--- chapter "
            var ends = "--- chapter "

            when (numberOption) {
                "1" -> {
                    val questionsList = service.loadQuestions(questionsfileName, answerfileName)
                    val output = generateQuestions(questionsList, questionsfileName, answerfileName, isRandom, keepOrder)
                    LOG.info(output)
                }
                "1.1" -> {
                    start += 1
                    ends += 2
                    generateChapterQuestions(questionsfileName, answerfileName, start, ends, isRandom, keepOrder)
                }
                "1.2" -> {
                    start += 2
                    ends += 3
                    generateChapterQuestions(questionsfileName, answerfileName, start, ends, isRandom, keepOrder)
                }

                "1.3" -> {
                    start += 3
                    ends += 4
                    generateChapterQuestions(questionsfileName, answerfileName, start, ends, isRandom, keepOrder)
                }
                "1.4" -> {
                    start += 5
                    ends += 6
                    generateChapterQuestions(questionsfileName, answerfileName, start, ends, isRandom, keepOrder)
                }
                "1.5" -> {
                    start += 5
                    ends += 6
                    generateChapterQuestions(questionsfileName, answerfileName, start, ends, isRandom, keepOrder)
                }
                "1.6" -> {
                    start += 6
                    ends += 7
                    generateChapterQuestions(questionsfileName, answerfileName, start, ends, isRandom, keepOrder)
                }
                "1.7" -> {
                    start += 7
                    ends += 8
                    generateChapterQuestions(questionsfileName, answerfileName, start, ends, isRandom, keepOrder)
                }
                "1.8" -> {
                    start += 8
                    ends += 9
                    generateChapterQuestions(questionsfileName, answerfileName, start, ends, isRandom, keepOrder)
                }
                "1.9" -> {
                    start += 9
                    ends += 10
                    generateChapterQuestions(questionsfileName, answerfileName, start, ends, isRandom, keepOrder)
                }
                "1.10" -> {
                    start += 10
                    ends += 11
                    generateChapterQuestions(questionsfileName, answerfileName, start, ends, isRandom, keepOrder)
                }
                "1.11" -> {
                    start += 11
                    ends += 12
                    generateChapterQuestions(questionsfileName, answerfileName, start, ends, isRandom, keepOrder)
                }
                "1.12" -> {
                    start += 12
                    ends += 13
                    generateChapterQuestions(questionsfileName, answerfileName, start, ends, isRandom, keepOrder)
                }
                "1.13" -> {
                    start += 13
                    ends += 14
                    generateChapterQuestions(questionsfileName, answerfileName, start, ends, isRandom, keepOrder)
                }
                "1.14" -> {
                    start += 14
                    ends += 15
                    generateChapterQuestions(questionsfileName, answerfileName, start, ends, isRandom, keepOrder)
                }
                "1.15" -> {
                    start += 15
                    ends += 16
                    generateChapterQuestions(questionsfileName, answerfileName, start, ends, isRandom, keepOrder)
                }
                "1.16" -> {
                    start += 16
                    ends += 17
                    generateChapterQuestions(questionsfileName, answerfileName, start, ends, isRandom, keepOrder)
                }
                "1.17" -> {
                    start += 17
                    ends += 18
                    generateChapterQuestions(questionsfileName, answerfileName, start, ends, isRandom, keepOrder)
                }
                "1.18" -> {
                    start += 18
                    ends += "custom questions"
                    generateChapterQuestions(questionsfileName, answerfileName, start, ends, isRandom, keepOrder)
                }

                "2" -> {
                    start += "custom questions"
                    ends += "continuara"
                    generateChapterQuestions(questionsfileName, answerfileName, start, ends, isRandom, keepOrder)
                }

                "3" -> {
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

    private fun generateChapterQuestions(questionsfileName: String, answerfileName: String, start: String, ends: String, isRandom: Boolean, keepOrder: Boolean) {
        val questionsList = service.loadSubSetQuestions(questionsfileName, answerfileName, start, ends)
        val output = generateQuestions(questionsList, questionsfileName, answerfileName, isRandom, keepOrder)
        LOG.info(output)
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


