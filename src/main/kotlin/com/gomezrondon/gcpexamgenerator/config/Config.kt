package com.gomezrondon.gcpexamgenerator.config

import com.gomezrondon.gcpexamgenerator.entities.questionContext
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration


@Configuration
class Config {

    @Bean
    fun getLevelOfDifficulty(): List<List<questionContext>> {
      val easy =  listOf<questionContext>(questionContext(70, "e"), questionContext(20, "m"), questionContext(10, "h"))
        val medium =  listOf<questionContext>(questionContext(20, "e"), questionContext(60, "m"), questionContext(20, "h"))
        val hard =  listOf<questionContext>(questionContext(10, "e"), questionContext(20, "m"), questionContext(70, "h"))
        val random =  listOf<questionContext>(questionContext(10, "e"), questionContext(10, "m"), questionContext(10, "h"))
        return  listOf(easy, medium, hard,random)
    }


}