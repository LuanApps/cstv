package dev.luanramos.cstv.domain.usecase

import dev.luanramos.cstv.domain.model.CsgoPlayer
import dev.luanramos.cstv.domain.repository.PlayerRepository
import jakarta.inject.Inject

class GetTeamPlayersUseCase @Inject constructor(
    private val repository: PlayerRepository)
{
    operator suspend fun invoke(teamId: Long): Result<List<CsgoPlayer>> = repository.getTeamPlayers(teamId)
}