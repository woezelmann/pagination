package org.woezelmann.pagination.componenttests

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.IntegrationTest
import org.springframework.boot.test.SpringApplicationConfiguration
import org.woezelmann.pagination.Application
import org.woezelmann.pagination.database.BasicPaginationRepository
import org.woezelmann.pagination.database.FooEntity
import org.woezelmann.pagination.database.FooRepository
import spock.lang.Specification

@SpringApplicationConfiguration(classes = Application.class)
@IntegrationTest
public class BasicSpec extends Specification {

    @Autowired
    private FooRepository fooRepository

    @Autowired
    private BasicPaginationRepository basicPaginationRepository

    def "add files to repo"() {
        when:
        fooRepository.save(new FooEntity(created: new Date()))

        then:
        fooRepository.count() == 1

    }

    def "@Query() works as supposed"() {
        given:
        def millis = System.currentTimeMillis()

        1000.times {
            fooRepository.save(new FooEntity(created: new Date(millis + (it * 1000))))
        }

        when:
        def foos = basicPaginationRepository.queryPage(100, 0)

        then:
        foos.size() >= 1000
    }

}
