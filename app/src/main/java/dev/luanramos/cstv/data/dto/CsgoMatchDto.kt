package dev.luanramos.cstv.data.dto

import com.google.gson.annotations.SerializedName
import dev.luanramos.cstv.domain.model.CsgoMatch
import java.util.Date

data class CsgoMatchDto(
    val id: Long,
    val name: String,
    @SerializedName("scheduled_at") val scheduledAt: Date,
    val league: CsgoLeagueDto,
    val serie: CsgoSerieDto,
    val opponents: List<OpponentWrapperDto>,
    val status: String
) {
    fun toDomain(): CsgoMatch = CsgoMatch(
        id = id,
        name = name,
        team1 = opponents.getOrNull(0)?.opponent?.toDomain(),
        team2 = opponents.getOrNull(1)?.opponent?.toDomain(),
        scheduledAt = scheduledAt,
        league = league.toDomain(),
        serie = serie.toDomain(),
        status = status
    )
}

data class OpponentWrapperDto(
    val opponent: CsgoTeamDto
)