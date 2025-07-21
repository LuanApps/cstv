package dev.luanramos.cstv.domain.model

data class CsgoLeague(
    val id: Long,
    val name: String,
    val slug: String?= null,
    val image: String?= null
)