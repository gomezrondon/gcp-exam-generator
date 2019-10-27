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


        var question = mutableListOf<Question>()
        var responses = mutableListOf<String>()
        var count = 1
        while (count <= numberOption && listOfQuestions.isNotEmpty()){
            val random = listOfQuestions.random()

            val split = random.question.split("""\s(?=[A-Z]\.\s)""" .toRegex()) //.flatMap { it.split("""\\n""".toRegex()) }

            print("$count) Question: ")
            split.slice(0..0).flatMap { it.split("""\\n""".toRegex()) }.forEach { println(it) }

            val list = split.slice(1..split.size - 1)

            val randList = randomizeList(list)

/*            println("\n")
            randList.forEach { println(it) }
            println(random.answer)
            println("\n")
            split.forEach { println(it) }
            println("\n")*/

            listOfQuestions.remove(random)
            //we remap the options randomly
            random.remapOptions(randList)

  /*          println(random.answer + " respuesta correcta")
            println("\n")*/


            list.map {
                val oldOption = it.split(". ").get(0)

                val newOption = randList.filter { it.split("|").get(1) == oldOption }.map { it.split("|").get(0) }.first()

                val newPosition =   it.replace("""[A-Z]\.\s""".toRegex(),"$newOption. ")
                newPosition

            }.sorted().forEach { println(it) }

            println("\n")
            count++

            print("Response?: ")



            question.add(random)
            var response = readLine().toString().toLowerCase()

            responses.add(response)
            println("\n")



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

    private fun randomizeList(list: List<String>):List<String>  {
        val optionsList =  listOf<String>("A","B","C","D","E","F","G")

        var tempList = list.map { it.split(".").get(0) }.toMutableList()
        var randomOptionList = list.toMutableList()

        var index = 0
        while(tempList.size > 0) {
            val random = tempList.random()
            randomOptionList.set(index, optionsList.get(index)+"|"+random)
            tempList.remove(random)
            index++
        }

        return randomOptionList
    }


}


data class Question(val id:Int, val question: String, var answer:String="N/A", var explanation:String="N/A"){

    fun remapOptions(randList: List<String>) {
        // "new position A|C old position "
        val mapResponse = this.answer.split(" ").map { pepe ->
            val newPosition = randList.map { it.toLowerCase() }
                    .filter { valor -> valor.split("|").get(1) == pepe }
                    .map { valor -> valor.split("|").get(0) }
                    .first()
            newPosition
        }.sorted().joinToString(" ")

        this.answer = mapResponse
    }
}
