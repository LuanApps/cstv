package dev.luanramos.cstv.domain.repository

import dev.luanramos.cstv.domain.model.CsgoMatch

interface MatchRepository {
    fun getRunningMatches(): List<CsgoMatch>
    fun getUpcomingMatches(): List<CsgoMatch>
}