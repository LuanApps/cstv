package dev.luanramos.cstv.data.repository

import dev.luanramos.cstv.BuildConfig
import dev.luanramos.cstv.data.datasource.CsgoApiService
import dev.luanramos.cstv.domain.model.CsgoMatch
import dev.luanramos.cstv.domain.repository.MatchRepository
import javax.inject.Inject

class MatchRepositoryImpl @Inject constructor(
    private val api: CsgoApiService,
): MatchRepository {

    override suspend fun getRunningMatches(): Result<List<CsgoMatch>> {
        return try {
            val response = api.getRunningMatches(
                apiToken = TOKEN
            )
            Result.success(response.map { it.toDomain() })
        } catch (e: Exception){
            Result.failure(e)
        }
    }

    override suspend fun getUpcomingMatches(
        pageNumber: Int,
        pageSize: Int
    ): Result<List<CsgoMatch>> {
        return try {
            val response = api.getUpcomingMatches(
                pageNumber = pageNumber,
                pageSize = pageSize,
                apiToken = TOKEN
            )
            Result.success(response.map { it.toDomain() })
        }
        catch (e: Exception){
            Result.failure(e)
        }
    }

    companion object {
        private const val TOKEN = BuildConfig.API_TOKEN
    }
}