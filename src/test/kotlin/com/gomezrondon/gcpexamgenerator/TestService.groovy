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
        variable == 505
    }

    @Test
    def "testing loading from command questions"() {
        when:
        def variable = service.loadQuestions("commands-questions.txt","commands-answers.txt").size()
        then:
        variable == 82
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
    def "testing getSpecificQuestions method"() {
        setup:
        def questionsList = service.loadQuestions("questions.txt","answers.txt")
        def numQuestionsList = [441, 505]
        when:"execute the method with a set of questions"
        List<Question> questions = service.getSpecificQuestions(questionsList, numQuestionsList)
        then:
        questions.size() == 2
        questions.getAt("id") == [441, 505]
    }


    @Test
    def "testing correct response with incorrect order"() {
        setup:"we set keepOrder = false"
        def questionsList = service.loadQuestions("questions.txt","answers.txt")
        def numQuestionsList = [441]
        def keepOrder = false
        when:
        List<Question> questions = service.getSpecificQuestions(questionsList, numQuestionsList)
        def anwerList = ["d a c "]
        def results = service.evaluateResults(questions, anwerList, keepOrder)
        then:" we should get a correct response"
        results.get(3).trim() == "Score: 100.0%"
    }


    @Test
    def "testing correct response with incorrect order 2"() {
        setup:"we set keepOrder = true"
        def questionsList = service.loadQuestions("questions.txt","answers.txt")
        def numQuestionsList = [441]
        def keepOrder = true
        when:
        List<Question> questions = service.getSpecificQuestions(questionsList, numQuestionsList)
        def anwerList = ["d a c "]
        def results = service.evaluateResults(questions, anwerList, keepOrder)
        then:"This should FAIL"
        results.get(3).trim() == "Score: 0.0%"
    }

    @Test
    def "testing evaluating questions without randomness"() {
        setup:
        def questionsList = service.loadQuestions("questions.txt","answers.txt")
        def numQuestions = 10
        def randomness = false
        def keepOrder = false //normal multiple questions dont need specific order
        when:
        List<Question> questions = service.generateQuestion(questionsList, numQuestions, randomness)
        def anwerList = questions.collect {it.answer}
        def results = service.evaluateResults(questions, anwerList, keepOrder)
        then:
        results.get(3).trim() == "Score: 100.0%"
    }

    @Test
    def "testing evaluating commands questions"() {
        setup:
        def questionsList = service.loadQuestions("commands-questions.txt","commands-answers.txt")
        def numQuestions = 10
        def keepOrder = true // command questions need specific order
        when:
        List<Question> questions = service.generateQuestion(questionsList, numQuestions, true)
        def answerList = questions.collect {it.answer}
        def results = service.evaluateResults(questions, answerList, keepOrder)
        then:
        results.get(3).trim() == "Score: 100.0%"
    }


}
