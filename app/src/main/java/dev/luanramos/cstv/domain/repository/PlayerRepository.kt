package dev.luanramos.cstv.domain.repository

import dev.luanramos.cstv.domain.model.CsgoPlayer

interface PlayerRepository {
    fun getTeamPlayers(): List<CsgoPlayer>
}