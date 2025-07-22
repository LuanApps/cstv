package dev.luanramos.cstv.domain.model

import java.util.Date

data class CsgoMatch(
    val id: Long,
    val name: String,
    val team1: CsgoTeam?= null,
    val team2: CsgoTeam?= null,
    val league: CsgoLeague,
    val serie: CsgoSerie,
    val scheduledAt: Date
)