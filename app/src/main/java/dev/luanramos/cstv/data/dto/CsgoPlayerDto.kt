package dev.luanramos.cstv.data.dto

import com.google.gson.annotations.SerializedName
import dev.luanramos.cstv.domain.model.CsgoPlayer

data class CsgoPlayerDto(
    val id: Long,
    val name: String,
    val slug: String?= null,
    @SerializedName("image_url") val image: String?= null,
    @SerializedName("active" )val isActive: Boolean
){
    fun toDomain(): CsgoPlayer = CsgoPlayer(
        id = id,
        name = name,
        slug = slug,
        image = image,
        isActive = isActive
    )
}