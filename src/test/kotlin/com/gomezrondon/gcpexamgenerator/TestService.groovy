package com.gomezrondon.gcpexamgenerator

import org.junit.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.ActiveProfiles
import spock.lang.Specification

@SpringBootTest
@ActiveProfiles("sandbox")
class TestService extends Specification {

    @Autowired
    private final GenerateQuestionService service

    @Test
    def "testing loading questions"() {
        when:
        def variable = service.loadQuestions("questions.txt","answers.txt").size()
        then:
        variable == 506
    }


    @Test
    def "testing generating questions"() {
        setup:
        def questionsList = service.loadQuestions("questions.txt","answers.txt")
        def numQuestions = 2
        when:
        def questions = service.generateQuestion(questionsList, numQuestions, true)
        then:
        numQuestions == questions.size()
    }

    @Test
    def "testing evaluating questions"() {
        setup:
        def questionsList = service.loadQuestions("questions.txt","answers.txt")
        def numQuestions = 10
        when:
        List<Question> questions = service.generateQuestion(questionsList, numQuestions, true)
        def anwerList = questions.collect {it.answer}
        def results = service.evaluateResults(questions, anwerList)
        then:
        results.get(3) == "Score: 100.0%"
    }


    @Test
    def "testing evaluating questions without randomness"() {
        setup:
        def questionsList = service.loadQuestions("questions.txt","answers.txt")
        def numQuestions = 10
        def randomness = false
        when:
        List<Question> questions = service.generateQuestion(questionsList, numQuestions, randomness)
        def anwerList = questions.collect {it.answer}
        def results = service.evaluateResults(questions, anwerList)
        then:
        results.get(3) == "Score: 100.0%"
    }

}
