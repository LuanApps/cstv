package dev.luanramos.cstv.domain.model

data class CsgoPlayer(
    val id: Long,
    val name: String,
    val slug: String?= null,
    val firstName: String?= null,
    val lastName: String?= null,
    val image: String?= null,
    val isActive: Boolean
)