package dev.luanramos.cstv.data.dto

import com.google.gson.annotations.SerializedName
import dev.luanramos.cstv.domain.model.CsgoPlayer

data class CsgoPlayerDto(
    val id: Long,
    val name: String,
    val slug: String?= null,
    @SerializedName("first_name")val firstName: String ?= null,
    @SerializedName("last_name")val lastName: String ?= null,
    @SerializedName("image_url") val image: String?= null,
    @SerializedName("active" )val isActive: Boolean
){
    fun toDomain(): CsgoPlayer = CsgoPlayer(
        id = id,
        name = name,
        slug = slug,
        firstName = firstName,
        lastName = lastName,
        image = image,
        isActive = isActive
    )
}