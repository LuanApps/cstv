package dev.luanramos.cstv.data.datasource

import dev.luanramos.cstv.data.dto.CsgoMatchDto
import dev.luanramos.cstv.data.dto.CsgoTeamPlayersDto
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface CsgoApiService {

    @GET("csgo/matches/running")
    suspend fun getRunningMatches(
        @Query("token") apiToken: String
    ): List<CsgoMatchDto>

    @GET("csgo/matches/upcoming")
    suspend fun getUpcomingMatches(
        @Query("token") apiToken: String
    ): List<CsgoMatchDto>

    @GET("teams/{id}")
    suspend fun getPlayersFromTeam(
        @Path("id") teamId: Long,
        @Query("token") apiToken: String
    ): CsgoTeamPlayersDto

}