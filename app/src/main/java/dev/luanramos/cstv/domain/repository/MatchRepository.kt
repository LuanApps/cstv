package dev.luanramos.cstv.domain.repository

import dev.luanramos.cstv.domain.model.CsgoMatch

interface MatchRepository {
    suspend fun getRunningMatches(): Result<List<CsgoMatch>>
    suspend fun getUpcomingMatches(
        pageNumber: Int,
        pageSize: Int,
    ): Result<List<CsgoMatch>>
}