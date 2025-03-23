package ro.upt.backenddegree.repository

import org.springframework.data.jpa.repository.JpaRepository
import ro.upt.backenddegree.entity.MenuProduct

interface MenuProductInterface : JpaRepository<MenuProduct, Long>