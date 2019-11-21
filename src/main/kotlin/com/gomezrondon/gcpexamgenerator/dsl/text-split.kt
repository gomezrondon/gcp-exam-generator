package com.gomezrondon.gcpexamgenerator.dsl

val texto = """
    Here's a slide we've seen a few times throughout our architecting courses to depict GCP compute and processing options. Google Kubernetes Engine is really that blend of both Infrastructure as a Service and Platform as a Service. Benefits include automatic scaling of the cluster with a lot of the flexibility of being able to deploy preconfigured containers. Your developers can package up their applications and dependencies, and you as the administrator can publish or push those, or even delegate that control to them. Kubernetes Engine has a lot more built-in capabilities that we will discuss in this module. And you have less restrictions than you might have with other configurations. So, what is a Container? Let's take a look at the history of virtualization. If you remember, most people were buying bare metal servers. That included the entire stack. Your applications, dependencies which sit on a common operating system on top of some kind of hardware appliance. With this bare metal setup, there were huge amounts of inefficiency. Like some people would actually buy a single-core processor with four gigabytes of memory and pay five to six grand for a machine that was only going to run Active Directory. You could easily run many more applications on that hardware setup. But that was kind of the dedicated single-server, single-application approach. Running multiple applications could sometimes create conflicts between different dependencies, OS versions, etc. So then, came along virtual machines. And what they did is they abstracted that hardware requirement so that you could have a single piece of hardware, but many different versions of operating systems with many different dependencies and applications running independently of each other. This allowed you to purchase larger pieces of hardware, and still run just as many components as you did before on a much smaller hardware footprint. The problem with this though is the fact that you have this big bloated operating system which was often many times larger than the actual applications running on top of it. And what purpose did the operating system really serve except to give access to the underlying hardware? You've got this small hypervisor hardware kind of abstracting that layer between as well. With Containers, you're bundling the application code and dependencies into a single unit, abstracting the application from the infrastructure. Containers package your applications so they can run in any environment that supports containerized workloads. Container-based solutions give you the ability to manage applications, not machines, and maintain vendor independence. You can write the code once, and run it anywhere. Container-based solutions make it easier to migrate to new platforms and help you de-couple applications from dependencies.

""".trimIndent()


/*fun main() {

    process()

}*/

private fun process() {
    /*    texto.replace("""\.""".toRegex(),".\n")
            .split("\n")
            .map { it.toLowerCase() }
         //   .filter { it.contains("kuberne") }
            .forEach { println(it) }*/

    //   val groupBy = texto.split("""\s""".toRegex()).groupBy ({it},{it})
    val groupBy = texto.split("""\s""".toRegex()).groupBy { it }

    println(groupBy.keys)
    println(groupBy)
}