package com.gomezrondon.gcpexamgenerator.dsl

import org.beryx.textio.TextIO
import org.beryx.textio.TextIoFactory
import java.util.*


enum class Options{
    hola,
    pepe,
    maria
}

/*fun main() {


    process()

}*/

private fun process() {
    //https://text-io.beryx.org/releases/latest/
    val textIO: TextIO = TextIoFactory.getTextIO()
    TextIoFactory.getTextTerminal().getProperties().setPromptColor("cyan")

/*

        val month:Month = textIO.newEnumInputReader(Month::class.java)
        .read("What month were you born in?");

        textIO.getTextTerminal().moveToLineStart()*/
    val terminal = textIO.getTextTerminal();


    terminal.setBookmark("COUNTDOWN");

/*    val menu ="""
        Select
        1) for All regular questions.
          1.1) chapter 1 - Overview of Google Cloud Platform
          1.2) chapter 2 - Google Cloud Computing Services
          1.3) chapter 3 - Projects, Service Accounts, and Billing
          1.4) chapter 4 - Introduction to Computing in Google Cloud
          1.5) chapter 5 - Compute Engine Virtual Machines
          1.6) chapter 6 - Managing Virtual Machines
          1.7) chapter 7 - Computing with Kubernetes
          1.8) chapter 8 - Managing Kubernetes Clusters
          1.9) chapter 9 - Computing with App Engine
          1.10) chapter 10 - Computing with Cloud Functions
          1.11) chapter 11 - Planning Storage in the Cloud
          1.12) chapter 12 - Deploying Storage in Google Cloud Platform
          1.13) chapter 10 - Loading Data into Storages
          1.14) chapter 14 - Networking in the Cloud: Virtual Private Clouds and  Virtual Private Networks
          1.15) chapter 15 - Networking in the Cloud: DNS, Load Balancing,  and IP Addressing
          1.16) chapter 16 - Deploying Applications with Cloud Launcher and  Deployment Manager
          1.17) chapter 17 - Configuring Access and Security
          1.18) chapter 18 - Monitoring, Logging, and Cost Estimating
        2) for Custom Mix questions
        3) for commands questions

        R?:
    """.trimIndent()

    var menuResponse = textIO.newStringInputReader()
            .withDefaultValue("1")
            .read(menu)*/

    val regexPattern = """[a-zA-Z\s]{1,}"""
    terminal.resetToBookmark("COUNTDOWN");

    var question = """
                Which of the following command is used to create buckets in Cloud Storage? 
                A. Gcloud storage buckets create 
                B.  Gcloud storage buckets create 
                C. Gsutil mb 
                D. Gcloud mb
                
                R?:
            """.trimIndent()

    var response = textIO.newStringInputReader()
            .withPattern(regexPattern)

            .read(question)

    terminal.resetToBookmark("COUNTDOWN");

    question = """
                What kind of data model is used by Datastore? 
                A. Relational 
                B. Document 
                C. Wide-column 
                D. Graph
                
                R?: 
            """.trimIndent()
    response = textIO.newStringInputReader()
            .withPattern(regexPattern)
            .read(question)

    terminal.resetToBookmark("COUNTDOWN");


    //  terminal.println("You choose: $user $month")
/*
    val user: String? = textIO.newStringInputReader()
            // .withDefaultValue("admin")
            .read("Select from the list:")*/
    terminal.println("You choose: $response")
}