package dev.luanramos.cstv.domain.model

data class CsgoSerie(
    val id: Long,
    val name: String,
    val fullName: String,
    val slug: String,
    val season: String?= null
)
