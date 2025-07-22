package dev.luanramos.cstv.data.repository

import dev.luanramos.cstv.BuildConfig
import dev.luanramos.cstv.data.datasource.CsgoApiService
import dev.luanramos.cstv.domain.model.CsgoPlayer
import dev.luanramos.cstv.domain.repository.PlayerRepository
import javax.inject.Inject

class PlayerRepositoryImpl @Inject constructor(
    private val apiService: CsgoApiService
): PlayerRepository {

    override suspend fun getTeamPlayers(teamId: Long): Result<List<CsgoPlayer>> {
        return try {
            val response =  apiService.getPlayersFromTeam(
                teamId = teamId,
                apiToken = TOKEN
            )
            Result.success(response.players.map { it.toDomain() })
        }
        catch (e: Exception){
            Result.failure(e)
        }

    }

    companion object {
        private const val TOKEN = BuildConfig.API_TOKEN
    }
}