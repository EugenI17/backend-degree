import ro.upt.backenddegree.dto.MenuProductDto


data class BarOrderDto(
    var tableNumber: String? = null,
    var menuProducts: List<MenuProductDto>,
)
