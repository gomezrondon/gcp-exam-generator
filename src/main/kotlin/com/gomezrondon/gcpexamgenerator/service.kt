package com.gomezrondon.gcpexamgenerator

import org.springframework.stereotype.Service
import java.io.File


@Service
class GenerateQuestionService{

    fun readFile(): List<Question> {
        val readLines = File("""questions${File.separator}questions.txt""").readLines()
                .takeWhile { it.isNotEmpty() }
                .map { it.split("~") }
                .map { Question(it[0].toInt(),it[1].trim())}


        File("""questions${File.separator}answers.txt""").readLines()
                .filter { it.isNotEmpty() }
                .forEach {
                    val split = it.split("~")
                    val index = split[0].toInt()
                    readLines.first { it.id ==index }.answer = split[1].toLowerCase().trim()
                    readLines.first { it.id ==index }.explanation = split[2].trim()
                   // println(readLines.get(index))
                }

        return readLines
    }

    fun generateQuestion(listOfQuestions: MutableList<Question>, numberOption: Int) {

        val optionsList =  listOf<String>("A. ","B. ","C. ","D. ","E. ","F. ","G. ")
        var tempList = optionsList.toMutableList()
        var randomOptionList = mutableListOf<String>()

        while(tempList.size > 0) {
            val temp = tempList.random()
            randomOptionList.add(temp)
            tempList.remove(temp)
        }


        var question = mutableListOf<Question>()
        var responses = mutableListOf<String>()
        var count = 1
        while (count <= numberOption && listOfQuestions.isNotEmpty()){
            val random = listOfQuestions.random()

            val split = random.question.split("""\s(?=[A-Z]\.\s)""" .toRegex()) //.flatMap { it.split("""\\n""".toRegex()) }

            print("$count) Question: ")
            split.slice(0..0).flatMap { it.split("""\\n""".toRegex()) }.forEach { println(it) }

            split.slice(1..split.size -1).forEach { println(it) }
           // split.forEach { println(it) }
            println("\n")
            count++

            print("Response?: ")
            question.add(random)
            responses.add(readLine().toString().toLowerCase())
            println("\n")

            listOfQuestions.remove(random)

        }

        var wrongList = mutableListOf<Question>()
        var correct = 0
        var wrong = 0
        responses.forEachIndexed { index, response ->
            if (question.get(index).answer == response) {
                correct ++
                wrongList.add(question.get(index).copy(explanation = "OK"))
            }else{
                wrong ++
                wrongList.add(question.get(index))
            }
        }

        val total = correct + wrong
        println("Total questions: $total")
        println("Correct: $correct")
        println("wrong: $wrong")
        val temp = correct.toDouble()*100
        val porcentage:Double = temp/total.toDouble()
        println("Score: $porcentage%")
        println("============ Answers ============")
        wrongList.forEachIndexed{index, it -> println("${index+1}. ${it.answer.toUpperCase()}. ${it.explanation}")}
        println("\n")

    }




}


data class Question(val id:Int, val question: String, var answer:String="N/A", var explanation:String="N/A")