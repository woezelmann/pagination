package org.woezelmann.pagination.componenttests

import spock.lang.Specification

class TestSpec extends Specification {

    def "some test"() {
        when:
        String s = """{"test":"ewojo"}"""

        then:
        println s
        1 == 1
    }
}
