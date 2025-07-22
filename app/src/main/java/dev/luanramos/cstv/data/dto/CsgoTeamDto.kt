package dev.luanramos.cstv.data.dto

import com.google.gson.annotations.SerializedName
import dev.luanramos.cstv.domain.model.CsgoTeam

data class CsgoTeamDto(
    val id: Long,
    val name: String,
    val slug: String?= null,
    val acronym: String?= null,
    @SerializedName("image_url") val image: String ?= null
){
    fun toDomain(): CsgoTeam = CsgoTeam(
        id = id,
        name = name,
        slug = slug,
        acronym = acronym,
        image = image
    )
}