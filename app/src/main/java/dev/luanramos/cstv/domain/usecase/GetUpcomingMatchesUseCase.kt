package dev.luanramos.cstv.domain.usecase

import dev.luanramos.cstv.domain.model.CsgoMatch
import dev.luanramos.cstv.domain.repository.MatchRepository
import jakarta.inject.Inject

class GetUpcomingMatchesUseCase @Inject constructor(
    private val repository: MatchRepository
) {
    operator suspend fun invoke(): Result<List<CsgoMatch>> = repository.getUpcomingMatches()
}