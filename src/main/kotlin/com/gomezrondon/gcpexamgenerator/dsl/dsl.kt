package com.gomezrondon.gcpexamgenerator.dsl

import com.gomezrondon.gcpexamgenerator.entities.txtQuestion

object work{
    var build = Qbuilder {

        question = """
           Can IAM policies that are implemented higher in the resource hierarchy take away access that is
granted by lower-level policies?

""".trim()

        option= hashMapOf(
                "a" to " yes."
                ,"b" to "no."
              //  ,"c" to " Predeﬁned roles, custom roles, primitive roles     "
               // ,"d" to "          There is no choice; organization nodes are mandatory."
        )

        answer = hashMapOf(
                "b " to """
                    no.
                    """.trim()
        )
    }

}
















fun Qbuilder(init: txtQuestion.()-> Unit): txtQuestion {
    val p = txtQuestion()
    p.init()
    return p
}