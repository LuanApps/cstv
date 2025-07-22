package dev.luanramos.cstv.domain.model

data class CsgoTeam(
    val id: Long,
    val name: String,
    val slug: String?= null,
    val acronym: String?= null,
    val image: String ?= null
)