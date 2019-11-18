package com.gomezrondon.gcpexamgenerator

fun getAlfabetList(): List<String> {
    val charRange = 'A'..'Z'
    val optionsList = charRange.map { it.toString() }.toList()
    return optionsList
}
