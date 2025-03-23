package ro.upt.backenddegree.service

import PrintedBarOrder
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Service
import org.springframework.web.reactive.function.client.WebClient
import ro.upt.backenddegree.dto.PrintedKitchenOrder

@Service

class PrintRequestService(
    private val webClient: WebClient.Builder,
    @Value("\${printer.service.kitchen-url}")
    private val kitchenPrinterUrl: String,
    @Value("\${printer.service.receipt-url}")
    private val receiptPrinterUrl: String,
    @Value("\${printer.service.bar-url}")
    private val barPrinterUrl: String,
    @Value("\${printer.service.api-key.name}")
    private val apiKeyName: String,
    @Value("\${printer.service.api-key.value}")
    private val apiKeyValue: String

) {

    fun printKitchenOrder(printedKitchenOrder: PrintedKitchenOrder) {
        webClient.build()
            .post()
            .uri(kitchenPrinterUrl)
            .header(apiKeyName, apiKeyValue)
            .bodyValue(printedKitchenOrder)
            .retrieve()
            .bodyToMono(String::class.java)
            .block()
    }

    fun printBarOrder(printedBarOrder: PrintedBarOrder) {
        webClient.build()
            .post()
            .uri(barPrinterUrl)
            .header(apiKeyName, apiKeyValue)
            .bodyValue(printedBarOrder)
            .retrieve()
            .bodyToMono(String::class.java)
            .block()
    }
}