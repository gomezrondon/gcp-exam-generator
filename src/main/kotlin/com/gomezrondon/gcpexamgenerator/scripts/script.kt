package com.gomezrondon.gcpexamgenerator.scripts

import com.gomezrondon.gcpexamgenerator.GenerateQuestionService
import com.gomezrondon.gcpexamgenerator.Question
import com.gomezrondon.gcpexamgenerator.dsl.work
import java.io.File



/**
 * This script helps assemble the questions and answers.
 */
fun main() {
    mainProcess()

}

private fun mainProcess() {



    val build = work.build
    val qtemp = build.question
    val atemp = build.answer

    println(qtemp)
    println(atemp)

    File("questions${File.separator}q-temp.txt").writeText(qtemp)
    File("questions${File.separator}a-temp.txt").writeText(atemp)

    print("Append question to file? [y/n] :")
    var yesOrNo = readLine()


    when (yesOrNo) {
        "n" -> {
            val service = GenerateQuestionService()
            val loadQuestions = service.loadQuestions("q-temp.txt", "a-temp.txt")
            var questions = service.generateQuestion(loadQuestions as MutableList<Question>, 1, false);
            service.askQuestions(questions)
        }

        "y" -> addQuestionToFile(qtemp, atemp)
    }
}




fun addQuestionToFile(question: String, answer: String) {
    File("questions${File.separator}questions.txt").appendText("\n"+question+"\n")
    File("questions${File.separator}answers.txt").appendText("\n"+answer+"\n")
}