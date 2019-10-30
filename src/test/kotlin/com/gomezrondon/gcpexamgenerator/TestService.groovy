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
        def variable = service.loadQuestions().size()
        then:
        variable == 401
    }


    @Test
    def "testing generating questions"() {
        setup:
        def questionsList = service.loadQuestions()
        def numQuestions = 2
        when:
        def questions = service.generateQuestion(questionsList, numQuestions)
        then:
        numQuestions == questions.size()
    }

    @Test
    def "testing evaluating questions"() {
        setup:
        def questionsList = service.loadQuestions()
        def numQuestions = 2
        when:
        List<Question> questions = service.generateQuestion(questionsList, numQuestions)
        def anwerList = questions.collect {it.answer}
        def results = service.evaluateResults(questions, anwerList)
        then:
        results.get(3) == "Score: 100.0%"
    }

}
