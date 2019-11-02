package com.gomezrondon.gcpexamgenerator.entities

data class txtQuestion(val question: String, val answer: String)



data class ExamSubject(val name:String, var questionPriority: List<String> = mutableListOf())


