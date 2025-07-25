package dev.luanramos.cstv.data.repository

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import dev.luanramos.cstv.data.datasource.CsgoApiService
import dev.luanramos.cstv.data.dto.CsgoPlayerDto
import dev.luanramos.cstv.data.dto.CsgoTeamPlayersDto
import dev.luanramos.cstv.domain.model.CsgoPlayer
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.every
import io.mockk.mockk
import junit.framework.TestCase.assertEquals
import junit.framework.TestCase.assertTrue
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class PlayerRepositoryImplTest {

    private lateinit var apiService: CsgoApiService
    private lateinit var repository: PlayerRepositoryImpl

    @get:Rule
    val instantExecutorRule = InstantTaskExecutorRule()
    private val testDispatcher = StandardTestDispatcher()

    @Before
    fun setUp() {
        Dispatchers.setMain(testDispatcher)
        apiService = mockk()
        repository = PlayerRepositoryImpl(apiService)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `getTeamPlayers returns success when API call returns team with players`() = runBlocking {
        val teamId = 1L
        val teamPlayersDto = mockk<CsgoTeamPlayersDto>()
        val playerDto = mockk<CsgoPlayerDto>()
        val player = mockk<CsgoPlayer>()

        every { playerDto.toDomain() } returns player
        every { teamPlayersDto.players } returns listOf(playerDto)
        coEvery { apiService.getPlayersFromTeam(teamId, any()) } returns teamPlayersDto

        val result = repository.getTeamPlayers(teamId)

        assertTrue(result.isSuccess)
        assertEquals(listOf(player), result.getOrNull())
        coVerify { apiService.getPlayersFromTeam(teamId, any()) }
    }

    @Test
    fun `getTeamPlayers returns failure when API call throws exception`() = runBlocking {
        val teamId = 42L
        val exception = RuntimeException("API error")
        coEvery { apiService.getPlayersFromTeam(teamId, any()) } throws exception

        val result = repository.getTeamPlayers(teamId)

        assertTrue(result.isFailure)
        assertEquals(exception, result.exceptionOrNull())
        coVerify { apiService.getPlayersFromTeam(teamId, any()) }
    }
}