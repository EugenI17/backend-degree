package ro.upt.backenddegree.service

import org.springframework.stereotype.Service
import ro.upt.backenddegree.entity.Order
import ro.upt.backenddegree.entity.Statistics
import ro.upt.backenddegree.repository.StatisticsRepository

@Service
class StatisticsService(
    private val statisticsRepository: StatisticsRepository,
) {

    fun addOrderToStatistics(order: Order) {
        order.items.forEach { item ->
           item.product.id?.let { productId ->
                val statistics = statisticsRepository.findByProductId(productId)
                    ?: Statistics(product = item.product, totalBought = 0)
                statistics.totalBought += item.quantity
                statisticsRepository.save(statistics)
            }
        }
    }

    fun getAllStatistics(): List<Statistics> {
        return statisticsRepository.findAll()
    }

}