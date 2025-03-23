package ro.upt.backenddegree.service

import org.springframework.stereotype.Service
import org.springframework.web.reactive.function.client.WebClient
@Service

class PrintService(
    private val webClient: WebClient.Builder) {
}