package com.gomezrondon.gcpexamgenerator

import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service
import java.io.File


@Service
class GenerateQuestionService{

    val LOG: Logger = LoggerFactory.getLogger(GenerateQuestionService::class.java);

    fun loadQuestions(questionsfileName:String, answerfileName:String): List<Question> {
        val readLines = File("""questions${File.separator}$questionsfileName""").readLines()
                .takeWhile { it.isNotEmpty() }
                .filter { !it.startsWith("---") }
                .map { it.split("~") }
                .map { Question(it[0].toInt(),it[1].trim())}


        File("""questions${File.separator}$answerfileName""").readLines()
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

    fun generateQuestion(listOfQuestions: MutableList<Question>, numberOption: Int, isRandom:Boolean = true) : MutableList<Question>{


        var question: MutableList<Question> = mutableListOf<Question>()
     //   var responses = mutableListOf<String>()
        var count = 1
        while (count <= numberOption && listOfQuestions.isNotEmpty()){
            var random = listOfQuestions.random()

            val split = random.question.split("""\s(?=[A-Z]\.\s)""" .toRegex())

      //      print("$count) Question: ")
            val temp = split.slice(0..0).joinToString(" ") + "\n"

            val list = split.slice(1..split.size - 1)

            listOfQuestions.remove(random)

            val randList = randomizeList(list, isRandom)

            if (isRandom) {
                //we remap the options randomly
                random.remapOptions(randList)
            }

            val reMappedOptions = list.map {
                val oldOption = it.split(". ").get(0)

                val newOption = randList.filter { it.split("|").get(1) == oldOption }.map { it.split("|").get(0) }.first()

                val newPosition = it.replace("""[A-Z]\.\s""".toRegex(), "$newOption. ")
                newPosition

            }.sorted().joinToString("|")

            val newQuestion = temp +"|"+ reMappedOptions
            random.question = newQuestion

            count++

            question.add(random)

        }

        return question

    }

    private fun randomizeList(list: List<String>, randonize: Boolean):List<String>  {
        val optionsList =  listOf<String>("A","B","C","D","E","F","G")

        var tempList = list.map { it.split(".").get(0) }.toMutableList()
        var randomOptionList = list.toMutableList()

        var index = 0
        if (randonize) {
            while(tempList.size > 0) {
                val random = tempList.random()
                randomOptionList.set(index, optionsList.get(index)+"|"+random)
                tempList.remove(random)
                index++
            }
        }else{
            randomOptionList = tempList.map { it +"|"+ it }.toMutableList()
        }


        return randomOptionList
    }

    fun askQuestions(questions: MutableList<Question>):MutableList<String> {
        var responses: MutableList<String> = mutableListOf<String>()


        questions.forEachIndexed { index,  qton ->

            val tempIndex = index + 1

            LOG.info("$tempIndex) Question: ")


            qton.question.split("|").forEach {
               it.split("""\\n""".toRegex()).forEach {
                    it.split(" ").chunked(20).map { it.joinToString(" ") }.forEach {
                        LOG.info(it + "\n")
                    }
                }
            }

            LOG.info("\n")
            LOG.info("Response?: ")
            var response = readLine()?.let { formatAnswers(it) }
            LOG.info("Response ( $response ) \n")


            if (response != null) {
                responses.add(response)
            }
            LOG.info("\n")
        }

        return responses
    }

    fun formatAnswers(response:String):String {
        val replace = response.toLowerCase().trim()
                .replace("""\s""".toRegex(), "").split("").map { it.toLowerCase() }.joinToString(" ")
        return replace.trim()
    }

    fun evaluateResults( questions: MutableList<Question>, responses:MutableList<String>): List<String> {
        var printResult = mutableListOf<String>()
        var wrongList = mutableListOf<Question>()
        var correct = 0
        var wrong = 0
        responses.forEachIndexed { index, response ->
            if (questions.get(index).answer == response) {
                correct ++
                wrongList.add(questions.get(index).copy(explanation = "OK"))
            }else{
                wrong ++
                wrongList.add(questions.get(index))
            }
        }

        val total = correct + wrong
        printResult.add("Total questions: $total")
        printResult.add("Correct: $correct")
        printResult.add("wrong: $wrong")
        val temp = correct.toDouble()*100
        val porcentage:Double = temp/total.toDouble()
        printResult.add("Score: $porcentage%")
        printResult.add("============ Answers ============")
        wrongList.forEachIndexed{index, it ->
            printResult.add("${index+1}. ${it.answer.toUpperCase()}. ${it.explanation}")
        }

        printResult.add("\n")

        return printResult
    }


}


data class Question(val id:Int, var question: String, var answer:String="N/A", var explanation:String="N/A"){

    fun remapOptions(randList: List<String>) {
        // "new position A|C old position "
        val mapResponse = this.answer.split(" ").map { pepe ->
            val newPosition = randList.map { it.toLowerCase() }
                    .filter { valor -> valor.split("|").get(1) == pepe }
                    .map { valor -> valor.split("|").get(0) }
                    .first()
            newPosition
        }.joinToString(" ")

        this.answer = mapResponse
    }
}
