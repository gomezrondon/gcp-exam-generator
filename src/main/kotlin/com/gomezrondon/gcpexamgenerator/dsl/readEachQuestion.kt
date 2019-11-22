package com.gomezrondon.gcpexamgenerator.dsl

import com.gomezrondon.gcpexamgenerator.GenerateQuestionService
import com.gomezrondon.gcpexamgenerator.Question

fun main() {
    process()

}

private fun process() {
    val service = GenerateQuestionService()

    val questionsfileName = "questions.txt"
    val answerfileName = "answers.txt"

    val loadQuestions = service.loadQuestions(questionsfileName, answerfileName)
    //126..146 done
    //400..420 done
    val charRange = 400..420
    val alphabet = charRange.map { it }.toList()


    var questions = service.getSpecificQuestions(loadQuestions as MutableList<Question>, alphabet as MutableList<Int>)
    val askQuestions = service.askQuestions(questions)

    questions.forEachIndexed { index, question ->
        println("Q: ${question.id} level: ${askQuestions.get(index)}")
    }
}