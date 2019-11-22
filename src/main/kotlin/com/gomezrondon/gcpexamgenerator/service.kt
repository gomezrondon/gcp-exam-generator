package com.gomezrondon.gcpexamgenerator

import org.beryx.textio.TextIO
import org.beryx.textio.TextIoFactory
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service
import java.io.File


@Service
class GenerateQuestionService{

    val LOG: Logger = LoggerFactory.getLogger(GenerateQuestionService::class.java);

     fun getNumfromPorcentage(percentage: Int, level: String,listOfQuestions: List<Question>): Int {
        val totalNumQuestions = listOfQuestions.filter { it.level == level }.size
        val numQuestions = totalNumQuestions.times(percentage).div(100)
        return numQuestions
    }

     fun getRandomQuestionsByLevel(numQuestion: Int, level: String, listOfQuestions: List<Question>): List<Question> {
        //  val listOfQuestions: List<Question> = service.listOfQuestions(questionsfileName, answerfileName)
         val filterByLevel = listOfQuestions.filter { it.level == level }

         val charRange = 1..numQuestion
         val resultList  = charRange.map { filterByLevel.random()}.toList()

        return resultList
    }


    fun loadQuestions(questionsfileName:String, answerfileName:String): List<Question> {
        val readLines = File("""questions${File.separator}$questionsfileName""").readLines()
                .filter { it.isNotEmpty() }
                .filter { !it.startsWith("---") }
                .map { it.split("~") }
                .map { Question(it[0].toInt(),it[1].trim(),it[2].trim())}


        addAnswersToQuestions(answerfileName, readLines)

        return readLines
    }



    fun loadSubSetQuestions(questionsfileName:String, answerfileName:String, start:String, ends:String): List<Question> {
        val readLines = File("""questions${File.separator}$questionsfileName""").readLines()
                .filter { it.isNotEmpty() }

        val indexOfFirst = readLines.indexOfFirst { it.startsWith(start) }
        val indexOfLast = readLines.indexOfLast { it.startsWith(ends) }

        val take = readLines.drop(indexOfFirst)
                .take(indexOfLast - indexOfFirst)
                .filter { it.isNotEmpty() }
                .filter { !it.startsWith("---") }
                .map { it.split("~") }
                .map { Question(it[0].toInt(),it[1].trim(),it[2].trim())}

        addAnswersToQuestions(answerfileName, take)

        return take
    }


    private fun addAnswersToQuestions(answerfileName: String, readLines: List<Question>) {

        val questIdList = readLines.map { it.id }

        File("""questions${File.separator}$answerfileName""").readLines()
                .filter { it.isNotEmpty() }
                .filter { it.length > 0 }
                .map {
                    val split = it.split("~")
                    val index = split[0].toInt()
                    Pair(index, split)
                }
                .filter { questIdList.contains(it.first) }
                .forEach { it ->
                    val index = it.first
                    val split = it.second

                    readLines.first { question -> question.id == index }.answer = split[1].toLowerCase().trim()
                    readLines.first { question -> question.id == index }.explanation = split[2].trim()
                    // println(readLines.get(index))

                }
    }


    fun getSpecificQuestions(listOfQuestions: MutableList<Question>, espeQuestList: MutableList<Int>): MutableList<Question> {

        var question: MutableList<Question> = mutableListOf()

        if (listOfQuestions.isNotEmpty()){
            espeQuestList.forEach { eqId ->
                val firstQuestion = listOfQuestions.filter { it.id == eqId }.first()
                question.add(firstQuestion)
            }
        }
        return question
    }

    fun generateQuestion(listOfQuestions: MutableList<Question>, numberOption: Int, isRandom:Boolean = true) : MutableList<Question>{


        var question: MutableList<Question> = mutableListOf()
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
        val optionsList = getAlfabetList()

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

        val textIO: TextIO = TextIoFactory.getTextIO()

        questions.forEachIndexed { index,  qton ->
            var text = StringBuilder()
            val tempIndex = index + 1

            //LOG.info("$tempIndex) Question: ")
            text.append("$tempIndex) Question: ")

            qton.question.split("|").forEach {
               it.split("""\\n""".toRegex()).forEach {
                    it.split(" ").chunked(20).map { it.joinToString(" ") }.forEach {
                       // LOG.info(it + "\n")
                        text.append(it + "\n")
                    }
                }
            }

            //LOG.info("\n")
            //LOG.info("Response?: ")
            text.append("\n")
            val question = text.append("Response?: ").toString()
           // var response = readLine()?.let { formatAnswers(it) }


            var response = textIO.newStringInputReader()
                    //.withDefaultValue("1")
                    //.withPattern(regexPattern)
                    .read(question)


            response = formatAnswers(response)

            LOG.info("$question \n")
            LOG.info("Your Response: ($response) \n")


            if (response != null) {
                responses.add(response)
            }
            println("")
            LOG.info("\n")
        }

        textIO.dispose()

        return responses
    }

    fun formatAnswers(response:String):String {
        val replace = response.toLowerCase().trim()
                .replace("""\s""".toRegex(), "").split("").map { it.toLowerCase() }.joinToString(" ")
        return replace.trim()
    }

    fun evaluateResults( questions: MutableList<Question>, responses:MutableList<String>, keepOrder:Boolean=false): List<String> {
        var printResult = mutableListOf<String>()
        var wrongList = mutableListOf<Question>()
        var correct = 0
        var wrong = 0
        responses.forEachIndexed { index, response ->
            var correctAnwers = questions.get(index).answer.trim()
            var userAnswers = response.trim()

            if (!keepOrder) {
                correctAnwers = correctAnwers.split(" ").sorted().joinToString(" ")
                userAnswers = userAnswers.split(" ").sorted().joinToString(" ")
            }

            if (correctAnwers == userAnswers) {
                correct ++
                wrongList.add(questions.get(index).copy(explanation = "OK"))
            }else{
                wrong ++
                wrongList.add(questions.get(index))
            }
        }

        val total = correct + wrong
        printResult.add("Total questions: $total \n")
        printResult.add("Correct: $correct \n")
        printResult.add("wrong: $wrong \n")
        val temp = correct.toDouble()*100
        val porcentage:Double = temp/total.toDouble()
        printResult.add("Score: $porcentage%\n")
        printResult.add("============ Answers ============ \n")
        wrongList.forEachIndexed{index, it ->
            printResult.add("${index+1}. ${it.answer.toUpperCase()}. ${it.explanation}  \n")
        }

        printResult.add("\n")

        return printResult
    }


}


data class Question(val id:Int, var level: String, var question: String, var answer:String="N/A", var explanation:String="N/A"){

    fun remapOptions(randList: List<String>) {
        // "new position A|C old position "
        val mapResponse = this.answer.split(" ").map { value ->
            val newPosition = randList.map { it.toLowerCase() }
                    .filter { valor -> valor.split("|").get(1) == value }
                    .map { valor -> valor.split("|").get(0) }
                    .first()
            newPosition
        }.joinToString(" ")

        this.answer = mapResponse
    }
}
