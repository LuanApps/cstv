package dev.luanramos.cstv.domain.usecase

import dev.luanramos.cstv.domain.model.CsgoMatch
import dev.luanramos.cstv.domain.repository.MatchRepository

class GetRunningMatchesUseCase(private val repository: MatchRepository) {
    operator fun invoke(): List<CsgoMatch> = repository.getRunningMatches()
}