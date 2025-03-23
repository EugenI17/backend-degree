package ro.upt.backenddegree.mapper

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import ro.upt.backenddegree.dto.MenuProductDto
import ro.upt.backenddegree.entity.MenuProduct
import ro.upt.backenddegree.repository.MenuProductRepository

@Service
class MenuProductMapper {

    @Autowired
    private lateinit var menuProductRepository: MenuProductRepository

    fun toEntity(menuProductDto: MenuProductDto): MenuProduct =
        MenuProduct(
            name = menuProductDto.name,
            price = menuProductDto.price,
            ingredients = menuProductDto.ingredients,
            type = menuProductDto.type
        )


    fun toDto(menuProduct: MenuProduct): MenuProductDto =
        MenuProductDto(
            id = menuProduct.id,
            name = menuProduct.name,
            price = menuProduct.price,
            ingredients = menuProduct.ingredients,
            type  = menuProduct.type
        )

}
