package ro.upt.backenddegree.controller

import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import ro.upt.backenddegree.entity.Statistics
import ro.upt.backenddegree.service.StatisticsService

@RestController
@RequestMapping("api/statistics")
class StatisticsController (
    private val statisticsService: StatisticsService
) {

    @GetMapping
    fun getAllStatistics(): ResponseEntity<List<Statistics>> {
        val statistics = statisticsService.getAllStatistics()
        return if (statistics.isEmpty()) {
            ResponseEntity.noContent().build()
        } else {
            ResponseEntity.ok(statistics)
        }
    }
}
