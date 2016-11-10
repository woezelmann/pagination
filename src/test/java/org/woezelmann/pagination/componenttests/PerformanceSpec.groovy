package org.woezelmann.pagination.componenttests

import org.apache.commons.lang.RandomStringUtils
import org.apache.commons.lang.time.StopWatch
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.IntegrationTest
import org.springframework.boot.test.SpringApplicationConfiguration
import org.woezelmann.pagination.Application
import org.woezelmann.pagination.database.BasicPaginationRepository
import org.woezelmann.pagination.database.FooEntity
import org.woezelmann.pagination.database.FooRepository
import spock.lang.Specification
import spock.lang.Unroll

@SpringApplicationConfiguration(classes = Application.class)
@IntegrationTest
class PerformanceSpec extends Specification {

    private static Random random = new Random()
    private static final Integer ENTRY_COUNT = 100_000

    @Autowired
    private FooRepository fooRepository

    @Autowired
    private BasicPaginationRepository basicPaginationRepository

    void setup() {
        ENTRY_COUNT.times {
            fooRepository.save(new FooEntity(
                    created: new Date(Math.abs(random.nextLong())),
                    text: RandomStringUtils.randomAscii(1024)
            ))


        }
    }

    @Unroll
    def "measure basic pagination performance with pagesize #pagesize"() {
        when:
        println "--------- PAGESIZE: $pagesize ---------"

        List<Long> splitTimes = new ArrayList<>((int)(ENTRY_COUNT / pagesize ) + 1)

        StopWatch split = new StopWatch()

        split.start()

        for (int offset = 0; offset < ENTRY_COUNT; offset += pagesize) {
            split.split()
            basicPaginationRepository.queryPage(pagesize, offset)
            splitTimes.add(split.getSplitTime())
        }

        split.stop()

        printStatistics(splitTimes)

        then:
        1 == 1

        where:
        pagesize << [10_000]
    }

    def printStatistics(ArrayList<Long> splitTimes) {
        def iterator = splitTimes.iterator()
        def previous = iterator.next()
        while (iterator.hasNext()) {
            def current = iterator.next()
            println current - previous
            previous = current
        }
    }
}
