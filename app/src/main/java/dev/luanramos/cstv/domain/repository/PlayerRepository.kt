package dev.luanramos.cstv.domain.repository

import dev.luanramos.cstv.domain.model.CsgoPlayer

interface PlayerRepository {
    suspend fun getTeamPlayers(teamId: Long): Result<List<CsgoPlayer>>
}