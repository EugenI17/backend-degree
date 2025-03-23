package ro.upt.backenddegree.repository

import org.springframework.data.jpa.repository.JpaRepository
import ro.upt.backenddegree.entity.Receipt

interface ReceiptRepository : JpaRepository<Receipt, Long>