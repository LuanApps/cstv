package dev.luanramos.cstv.data.dto

import com.google.gson.annotations.SerializedName

data class CsgoTeamPlayersDto (
    val id: Long,
    val name: String,
    val slug: String?,
    @SerializedName("image_url") val image: String?,
    val players: List<CsgoPlayerDto>
)