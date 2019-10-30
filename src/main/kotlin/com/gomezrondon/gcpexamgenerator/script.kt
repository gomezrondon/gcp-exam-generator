package com.gomezrondon.gcpexamgenerator

import java.io.File

/**
 * This script helps assemble the questions and answers.
 */
fun main() {

     val lastCount = File("questions/questions.txt").readLines()
            .filter { !it.startsWith("---") }
            .filter { it.isNotEmpty() }
            .takeLast(1)
            .map { it.split("~").get(0).toInt() }
            .first()


    val options = File("questions/temp.txt").readText().split("\r\n").joinToString(" ")

    val regExpQuest = """(?<=q:)(.*)(?=o:)""".toRegex(RegexOption.MULTILINE)
    val first = regExpQuest.findAll(options).map { it.value }.first()

    val regExpOpt = """(?<=o:)(.*)(?=a:)""".toRegex(RegexOption.MULTILINE)
    val second = regExpOpt.findAll(options).map { it.value }.first()

    val regExpAnsW = """(?<=a:)(.*)""".toRegex(RegexOption.MULTILINE)
    val third = regExpAnsW.findAll(options).map { it.value }.first()

   // println(options)
    val qtemp = "${lastCount + 1}~$first $second"
    val atemp =  "${lastCount + 1}~$third"
    println(qtemp)
    println(atemp)

    File("questions${File.separator}q-temp.txt").writeText(qtemp)
    File("questions${File.separator}a-temp.txt").writeText(atemp)

    val service = GenerateQuestionService()
    val loadQuestions = service.loadQuestions("q-temp.txt", "a-temp.txt")
    var questions = service.generateQuestion(loadQuestions as MutableList<Question>, 1);
    val responses = service.askQuestions(questions)

}

