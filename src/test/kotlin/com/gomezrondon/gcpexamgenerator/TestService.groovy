package com.gomezrondon.gcpexamgenerator

import com.gomezrondon.gcpexamgenerator.entities.questionContext
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
        variable == 544
    }

    @Test
    def "testing loading subset questions"() {
        when:
        def variable = service.loadSubSetQuestions("questions.txt","answers.txt","--- chapter 9","--- chapter 10").size()
        then:
        variable == 20
    }

    @Test
    def "testing loading subset questions (returns All)"() {
        when:
        def variable = service.loadSubSetQuestions("questions.txt","answers.txt","","").size()
        then:
        variable ==544
    }

    @Test
    def "testing loading subset questions with start but not end"() {
        setup:"start at --- chapter 11"
        def start = "--- chapter 11"
        when:
        def variable = service.loadSubSetQuestions("questions.txt","answers.txt", start,"").size()
        then:
        variable == 312
    }

    @Test
    def "testing loading from command questions"() {
        when:
        def variable = service.loadQuestions("commands-questions.txt","commands-answers.txt").size()
        then:
        variable == 102
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
        def numQuestionsList = [441, 505, 529]
        when:"execute the method with a set of questions"
        List<Question> questions = service.getSpecificQuestions(questionsList, numQuestionsList)
        then:
        questions.size() == 3
        questions.getAt("id") == [441, 505, 529]
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

/*
    @Test
    def "testing give me 20% of easy questions"() {
        setup:
        def questionsList = service.loadQuestions("questions.txt","answers.txt")
        def percentage = 20
        def level = "e"
        when:
        def questions = service.getNumfromPorcentage(percentage,level, questionsList)
        then:
        questions == 108
    }
*/


    @Test
    def "testing give me 6 easy random questions"() {
        setup:
        def questionsList = service.loadQuestions("questions.txt","answers.txt")
        def numQuestion = 6
        def level = "e"
        when:
        def questions = service.getRandomQuestionsByLevel(numQuestion,level, questionsList)
        then:
        questions.size() == numQuestion
        questions.findAll {it.getLevel() == level} != null

    }


    @Test
    def "testing give me 7 hard random questions"() {
        setup:
        def questionsList = service.loadQuestions("questions.txt","answers.txt")
        def numQuestion = 7
        def level = "h"
        when:
        def questions = service.getRandomQuestionsByLevel(numQuestion,level, questionsList)
        then:
        questions.size() == numQuestion
        questions.findAll {it.getLevel() == level} != null

    }


    @Test
    def "testing give me a list of questions depending on the percentage of each level"() {
        setup:
        def questionsList = service.loadQuestions("questions.txt","answers.txt")
        def percentageList = [new questionContext(40,"e"), new questionContext(30,"m"), new questionContext(30,"h")]
        def numQuestion = 20
        when:
        def questions = service.getPercentageListOfQuestions(numQuestion, percentageList, questionsList)
        then:
        questions.size() == numQuestion
        questions.findAll {it.getLevel() == "e"}.size() == 8
        questions.findAll {it.getLevel() == "m"}.size() == 6
        questions.findAll {it.getLevel() == "h"}.size() == 6


    }



}
