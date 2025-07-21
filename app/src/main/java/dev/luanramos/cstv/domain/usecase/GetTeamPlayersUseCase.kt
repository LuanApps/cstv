package dev.luanramos.cstv.domain.usecase

import dev.luanramos.cstv.domain.model.CsgoPlayer
import dev.luanramos.cstv.domain.repository.PlayerRepository

class GetTeamPlayersUseCase(private val repository: PlayerRepository) {
    operator fun invoke(): List<CsgoPlayer> = repository.getTeamPlayers()
}