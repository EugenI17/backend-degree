package ro.upt.printingmsdegree.dto

import java.text.Normalizer

data class MenuProductDto(
    val name: String,
    val quantity: Int,
    val price: Double,
) {
    private val paperWidth: Int = 48

    fun formatMenuLine(): String {
        var lineLength = paperWidth
        val line = quantity.toString().length + price.toString().length + name.length + " x ".length + " Lei".length
        if (line >= lineLength) {
           val addedSpace = paperWidth - "$price Lei".length
           return "$name x ${quantity}\n${" ".repeat(addedSpace)}${price} Lei"
        }
        lineLength -= line
        return "$name x ${quantity}${" ".repeat(lineLength)}${price} Lei"
    }

    fun formatMenuForBar(): String {
        var lineLength = paperWidth

        lineLength -= (quantity.toString().length + name.length + " x ".length)
        return "${name.normalizeDiacritics()} x ${quantity}${" ".repeat(lineLength)}"
    }

    private fun String.normalizeDiacritics() : String =
        Normalizer.normalize(this, Normalizer.Form.NFD).replace("\\p{M}".toRegex(), "")

}
