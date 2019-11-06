package com.gomezrondon.gcpexamgenerator.dsl

/*
fun main() {
    mainProcess()

}
*/


private fun mainProcess() {


    val build = work2.build

/*    val randomnizeString = randomnizeString("gsutil ls -lh gs://<bucket-name>")
    println(randomnizeString)*/


}


fun randomnizeString(text: String): List<String> {

    val charRange = 'A'..'Z'
    val tempList = text.split("""\s""".toRegex()).toMutableList()
    val alphabet = charRange.map { it }.take(tempList.size).toList()

    var tempOrder = alphabet.map { it.toString() }.toMutableList()

    val normalList = text.split("""\s""".toRegex())
            .mapIndexed { index, word ->
             //   val random = randomWithOutRepetition(tempList)
                val newOrder = randomWithOutRepetition(tempOrder)
              // "$newOrder | $random"+ alphabet.get(index) + "|  $word "
                "$newOrder | "+ alphabet.get(index) + "|  $word "
            }.sorted()

    return normalList
}

private fun randomWithOutRepetition(tempList: MutableList<String>): String {
    val random = tempList.random()
    tempList.remove(random)
    return random
}