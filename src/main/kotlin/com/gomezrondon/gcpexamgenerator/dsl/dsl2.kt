package com.gomezrondon.gcpexamgenerator.dsl

object work2{

    var build = createTextQuestion2{


        texto(

                """
List all sql instances	| gcloud sql instances list
 List my backend services	| gcloud compute backend-services list
List all my health check endpoints	| gcloud compute http-health-checks list
List all URL maps	| gcloud compute url-maps list
                    
                """.trim()

        )


    }



}


fun createTextQuestion2( c: QuestionBuilder2.()-> Unit) {
    val builder = QuestionBuilder2()
    c(builder)
    return builder.build()
}


class QuestionBuilder2{

    var texto:String= ""

    fun texto(texto: String) {
        this.texto =  texto
    }

    fun build() {
//List all buckets and files |	gsutil ls, gsutil ls -lh gs://<bucket-name>

       val splitHalf = this.texto.split("""\n""".toRegex())
               .filter { it.isNotEmpty() }
                .map { "How to ${it.trim()}" }
                .map { splitList(it) }

        val lista =
                splitHalf
                .map { it.toMutableList() }
                .map { val formula = it.get(1).trim().replace("""\n""".toRegex()," ")
                    val newFormula = randomnizeString(formula)
                    it.set(1, newFormula.joinToString("\n"))
                    it
                }.forEach {
                            val part1  = it.get(0)+"?"
                            val part2 = it.get(1)

                            val listOptions =  part2.split("\n")
                                    .map {
                                        val split = splitList(it)
//A|F|  gs://<bucket-name> | gs://<bucket-name>
                                        val options = split.get(0) + "* " + split.get(2)

                                        options
                                    }.joinToString("\n")


                          val tempStep =  part2.split("\n")
                                    .map {
                                        val split = splitList(it)
                                        val values = split.get(1) + "|" + split.get(0) + "|" + split.get(2)
                                        values

                                    }
                                    .sorted()

                            val correctOpt = tempStep.map {
                                        val split = splitList(it)
                                        val values = split.get(1)
                                        values

                                    }.joinToString(" ")+ "* "

                        val correctAnswer =    tempStep.map {
                                val split = splitList(it)
                                val values = split.get(2)
                                values
                            }.joinToString(" ")


                            println(part1)
                            println(listOptions)
                            println(" ")
                            println(correctOpt +" "+correctAnswer )
                            println(" ")

                        }

/*        val part1  =lista.get(0).get(0)+"?"
        val part2 = lista.get(0).get(1)*/





    }

    private fun splitList(it: String) = it.split("|").map { it.trim() }

}