package com.gomezrondon.gcpexamgenerator.dsl

import com.gomezrondon.gcpexamgenerator.entities.txtQuestion
import java.io.File



object work{
    var build = createTextQuestion{

        question(
                """
How to  list types of deployment managed resources ? 
, set the correct order of each command: \n
(example:      X Y Z  list | grep [resource name] ")
            """
        )

/*        options( """
 a* true.
 b* false.
            """)*/


        options( """
A* gcloud
B*   deployment-manager
C*   types


            """)

        answer( """
           
           a b c* gcloud deployment-manager types list | grep [network |firewall|instance]
           
            """)
    }

}



fun createTextQuestion( c: QuestionBuilder.()-> Unit): txtQuestion {
    val builder = QuestionBuilder()
    c(builder)
    return builder.build()
}

class QuestionBuilder{

    var question:String?= null
    var option: Map<String, String> = LinkedHashMap()
    var answer: Map<String, String> = LinkedHashMap()

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


    fun build():txtQuestion {

        val lastCount = getLastQuestionCount("commands-questions.txt") + 1

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







