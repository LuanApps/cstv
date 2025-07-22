package dev.luanramos.cstv.data.dto

import com.google.gson.annotations.SerializedName
import dev.luanramos.cstv.domain.model.CsgoSerie

data class CsgoSerieDto(
    val id: Long,
    val name: String,
    @SerializedName("full_name") val fullName: String,
    val slug: String,
    val season: String ?= null
){
    fun toDomain(): CsgoSerie = CsgoSerie(
        id = id,
        name = name,
        fullName = fullName,
        slug = slug,
        season = season
    )
}