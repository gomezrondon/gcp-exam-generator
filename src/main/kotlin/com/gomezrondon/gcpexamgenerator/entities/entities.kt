package com.gomezrondon.gcpexamgenerator.entities

data class txtQuestion(var question: String?= null, var option: Map<String, String> = LinkedHashMap()
                       , var answer: Map<String, String> = LinkedHashMap()){

    var counter: Int = 0
        set(value) {
            field =  value + 1
        }

    fun getQ():String {
         question = question?.replace("\n","")

         val options = option.map { it.key.toUpperCase() + ". " + it.value.trim().capitalize()}.joinToString(" ")
         return counter.toString() + "~" + question + " "+ options
     }

    fun getA():String{
        val answer = answer.map { it.key.toUpperCase() + "~" + it.value.trim().capitalize()}.joinToString()
        return counter.toString() + "~" + answer
    }
}




