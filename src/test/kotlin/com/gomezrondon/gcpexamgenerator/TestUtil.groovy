package com.gomezrondon.gcpexamgenerator

import org.junit.Test
import spock.lang.Specification

class TestUtil extends Specification {

    @Test
    def "mini test"() {
        given:
        def service = new GenerateQuestionService()
        when:
        def trim =  service.formatAnswers("ab cD e ")
        then:
        trim == "A B C D E"
    }

}
