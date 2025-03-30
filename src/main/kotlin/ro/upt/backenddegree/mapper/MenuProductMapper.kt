package ro.upt.backenddegree.mapper

import org.springframework.stereotype.Service
import ro.upt.backenddegree.dto.MenuProductDto
import ro.upt.backenddegree.entity.MenuProduct

@Service
class MenuProductMapper {

    fun toEntity(menuProductDto: MenuProductDto): MenuProduct =
        MenuProduct(
            id = menuProductDto.id,
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
