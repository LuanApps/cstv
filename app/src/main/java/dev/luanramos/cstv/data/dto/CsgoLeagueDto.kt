package dev.luanramos.cstv.data.dto

import com.google.gson.annotations.SerializedName
import dev.luanramos.cstv.domain.model.CsgoLeague

data class CsgoLeagueDto(
    val id: Long,
    val name: String,
    val slug: String?= null,
    @SerializedName("image_url") val image: String?= null
){
    fun toDomain(): CsgoLeague = CsgoLeague(
        id = id,
        name = name,
        slug = slug,
        image = image
    )
}