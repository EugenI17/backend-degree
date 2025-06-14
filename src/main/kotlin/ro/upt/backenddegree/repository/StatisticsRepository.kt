package ro.upt.backenddegree.repository

import org.springframework.data.jpa.repository.JpaRepository
import ro.upt.backenddegree.entity.Statistics
import java.time.LocalDate

interface StatisticsRepository : JpaRepository<Statistics, Long> {
    fun findByProductId(productId: Long): Statistics?
}