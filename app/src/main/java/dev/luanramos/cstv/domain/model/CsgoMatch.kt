package dev.luanramos.cstv.domain.model

data class CsgoMatch(
    val id: Long,
    val name: String,
    val team1: CsgoTeam,
    val team2: CsgoTeam,
    val league: CsgoLeague
)