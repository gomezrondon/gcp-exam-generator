package com.gomezrondon.gcpexamgenerator.dsl

import com.gomezrondon.gcpexamgenerator.entities.txtQuestion
import java.io.File



object work{
    var build = createTextQuestion{

        question(
                """
What is the difference between IAM primitives roles and IAM predefined roles?
            
            """
        )

        options( """


a* Primitive roles affect all resources in a GCP project. Predeﬁned roles apply to a particular service in a
project.

b* Primitive roles only allow viewing. creating, and deleting resources. Predeﬁned roles allow any
modiﬁcation.

c* Primitive roles can only be granted to single users. Predeﬁned roles can be associated with a group.

d* Primitive roles only apply to the owner olthe GCP project. Predeﬁned roles can be associated with
any user.

e* Primitive roles are changeable once assigned. Predeﬁned roles can never be changed.
 
            """)

        answer( """
                a* Primitive roles affect all resources in a GCP project. Predeﬁned roles apply to a particular service in a
project.
               

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

        val lastCount = getLastQuestionCount() + 1

        question = question?.replace("\n","")
        val options = option.map { it.key.toUpperCase() + ". " + it.value.trim().capitalize()}.joinToString(" ")

        val qText = lastCount.toString() + "~" + question + " "+ options

        val answer = answer.map { it.key.toUpperCase() + "~" + it.value.trim().capitalize()}.joinToString()
        val aText = lastCount.toString() + "~" + answer

        return txtQuestion(qText, aText)
    }

    private fun getLastQuestionCount(): Int {
        val lastCount = File("questions/questions.txt").readLines()
                .filter { !it.startsWith("---") }
                .filter { it.isNotEmpty() }
                .takeLast(1)
                .map { it.split("~").get(0).toInt() }
                .first()
        return lastCount
    }



}







