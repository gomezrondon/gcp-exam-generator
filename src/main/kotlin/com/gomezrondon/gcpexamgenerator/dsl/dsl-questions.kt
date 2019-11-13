package com.gomezrondon.gcpexamgenerator.dsl

import com.gomezrondon.gcpexamgenerator.entities.txtQuestion
import java.io.File



object work{
    var build = createCommandQuestion{

        fileName(
              //  "questions.txt"
       "commands-questions.txt"
        )

        question(
                """
how to list regions ? \n
example: gcloud X Y Z
            """
        )

        options( """
A* regions
B* compute
C* list

             """)

        answer( """
B A C* gcloud compute regions list
         
            """)
    }

}



fun createCommandQuestion( c: QuestionBuilder.()-> Unit): txtQuestion {
    val builder = QuestionBuilder()
    c(builder)
    return builder.build(builder.file)
}

class QuestionBuilder{

    var file:String= "questions.txt"
    var question:String?= null
    var option: Map<String, String> = LinkedHashMap()
    var answer: Map<String, String> = LinkedHashMap()

    fun fileName(file: String) {
        this.file = file
    }

    fun question(question:String){
        this.question = question.trim()
                .replace("""\n""".toRegex(), " ")
                .replace("""(\s){1,}""".toRegex(), " ")
                .capitalize()
    }

    fun options(options: String) {
        var temp = mutableMapOf<String, String>()
         options.trim()
                .replace("""\n""".toRegex(), " ")
                .replace("""(\s){1,}""".toRegex(), " ")
                .split("""(?=[a-z-A-z]\*)""".toRegex(RegexOption.MULTILINE))
                .filter { !it.isNullOrEmpty() }
                .map { it.capitalize() }
                .forEach {
                    val split = it.split("*")
                    temp.put(split.get(0), split.get(1))
                }

        this.option = temp
    }

    fun answer(answer: String) {
        var temp = mutableMapOf<String, String>()

        val split = answer.trim()
                .replace("""\n""".toRegex(), " ")
                .replace("""(\s){1,}""".toRegex(), " ")
                .split("*")

        temp.put(split.get(0).toUpperCase(), split.get(1).capitalize())

        this.answer = temp
    }


    fun build(q_file: String):txtQuestion {

        val lastCount = getLastQuestionCount(q_file) + 1

        question = question?.replace("\n","")
        val options = option.map { it.key.toUpperCase() + ". " + it.value.trim().capitalize()}.joinToString(" ")

        val qText = lastCount.toString() + "~" + question + " "+ options

        val answer = answer.map { it.key.toUpperCase() + "~" + it.value.trim().capitalize()}.joinToString()
        val aText = lastCount.toString() + "~" + answer

        return txtQuestion(qText, aText)
    }

    private fun getLastQuestionCount( q_file:String): Int {
        val lastCount = File("questions/"+q_file).readLines()
                .filter { !it.startsWith("---") }
                .filter { it.isNotEmpty() }
                .takeLast(1)
                .map { it.split("~").get(0).toInt() }
                .first()
        return lastCount
    }



}







