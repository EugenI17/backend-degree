package ro.upt.backenddegree.service

import org.springframework.stereotype.Service
import ro.upt.backenddegree.dto.MenuProductDto
import ro.upt.backenddegree.mapper.MenuProductMapper
import ro.upt.backenddegree.repository.MenuProductRepository

@Service
class MenuService (
    private val menuProductMapper: MenuProductMapper,
    private val menuProductRepository: MenuProductRepository
) {

    fun addProduct(menuProductDto: MenuProductDto) =
        menuProductRepository.save(menuProductMapper.toEntity(menuProductDto))

    fun getProducts() = menuProductRepository.findAll().map(menuProductMapper::toDto)

    fun updateProduct(menuProductDto: MenuProductDto) {
        val updatedProduct = menuProductMapper.toEntity(menuProductDto)
        menuProductRepository.save(updatedProduct)
    }

    fun deleteProduct(id: Long) {
        menuProductRepository.deleteById(id)
    }
}