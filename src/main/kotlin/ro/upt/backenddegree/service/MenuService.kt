package ro.upt.backenddegree.service

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import ro.upt.backenddegree.dto.MenuProductDto
import ro.upt.backenddegree.mapper.MenuProductMapper
import ro.upt.backenddegree.repository.MenuProductRepository

@Service
class MenuService (
    private val menuProductMapper: MenuProductMapper,
    private val menuProductRepository: MenuProductRepository
){

    fun addProduct(menuProductDto: MenuProductDto) =
        menuProductRepository.save(menuProductMapper.toEntity(menuProductDto))

    fun getProducts() = menuProductRepository.findAll()

    fun updateProduct(menuProductDto: MenuProductDto) {

    }
}