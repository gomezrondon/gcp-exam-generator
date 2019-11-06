package com.gomezrondon.gcpexamgenerator.dsl

object work2{

    var build = createTextQuestion2{


        texto(

                """
                    Download file	| gsutil cp gs://<bucket-name>/<dir-path>/package-1.1.tgz .
                    List all files in a buckets |	 gsutil ls -lh gs://<bucket-name>
                    List all buckets | gsutil ls
                    Upload file	 |gsutil cp <filename> gs://<bucket-name>/<directory>/
                    Cat file	 |gsutil cat gs://<bucket-name>/<filepath>/
                    Delete file |	gsutil rm gs://<bucket-name>/<filepath>
                    Move file	 |gsutil mv <src-filepath> gs://<bucket-name>/<directory>/<dest-filepath>
                    Copy folder |	gsutil cp -r ./conf gs://<bucket-name>/
                    Show disk usage |	gsutil du -h gs://<bucket-name/<directory>
                    Create bucket |	gsutil mb gs://<bucket-name>
                    
                    
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