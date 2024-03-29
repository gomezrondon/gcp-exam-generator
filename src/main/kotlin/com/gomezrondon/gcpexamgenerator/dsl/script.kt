package com.gomezrondon.gcpexamgenerator.dsl

import com.gomezrondon.gcpexamgenerator.GenerateQuestionService
import com.gomezrondon.gcpexamgenerator.Question
import java.io.File



/**
 * This script helps assemble the questions and answers.
 */
/*fun main() {
    mainProcess()

}*/

private fun mainProcess(prfijo:String="") {

    val build = work.build
    val qtemp = build.question
    val atemp = build.answer

    println(qtemp)
    println(atemp)

    File("questions${File.separator}q-temp.txt").writeText(qtemp)
    File("questions${File.separator}a-temp.txt").writeText(atemp)

    val service = GenerateQuestionService()
    val loadQuestions = service.loadQuestions("q-temp.txt", "a-temp.txt")
    var questions = service.generateQuestion(loadQuestions as MutableList<Question>, 1, false);
    val askQuestions = service.askQuestions(questions)
    val results = service.evaluateResults(questions, askQuestions)

    //printing results
    results.forEach { println(it) }


    print("Append question to file? [y/n] :")
    var yesOrNo = readLine()


    when (yesOrNo) {
        "y" -> addQuestionToFile(qtemp, atemp,"${prfijo}questions.txt","${prfijo}answers.txt")
    }

    val rangeTo = 'A'..'G'
    val tToZ = 'T'..'Z'

    val lastLetters = tToZ.joinToString(" ")
    val indexOption =  rangeTo.map { it + "*   " }.joinToString("\n") + "\n" +lastLetters



    File("questions${File.separator}temp1.txt").writeText(indexOption)

}

/*
fun addQuestionToFile(question: String, answer: String) {
    File("questions${File.separator}commands-questions.txt").appendText("\n"+question+"\n")
    File("questions${File.separator}commands-answers.txt").appendText("\n"+answer+"\n")
}
*/


fun addQuestionToFile(question: String, answer: String, fileQ:String, fileA:String) {
    File("questions${File.separator}$fileQ").appendText("\n"+question+"\n")
    File("questions${File.separator}$fileA").appendText("\n"+answer+"\n")
}
