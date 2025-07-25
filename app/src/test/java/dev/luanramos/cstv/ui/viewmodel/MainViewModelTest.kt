package dev.luanramos.cstv.ui.viewmodel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import dev.luanramos.cstv.domain.model.CsgoMatch
import dev.luanramos.cstv.domain.model.CsgoPlayer
import dev.luanramos.cstv.domain.usecase.GetRunningMatchesUseCase
import dev.luanramos.cstv.domain.usecase.GetTeamPlayersUseCase
import dev.luanramos.cstv.domain.usecase.GetUpcomingMatchesUseCase
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.TestScope
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Before
import org.junit.Rule
import kotlin.test.Test

@ExperimentalCoroutinesApi
class MainViewModelTest {

    @get:Rule
    val instantExecutorRule = InstantTaskExecutorRule()
    private val testDispatcher = StandardTestDispatcher()
    private val testScope = TestScope(testDispatcher)

    private val getRunningMatchesUseCase = mockk<GetRunningMatchesUseCase>()
    private val getUpcomingMatchesUseCase = mockk<GetUpcomingMatchesUseCase>()
    private val getTeamPlayersUseCase = mockk<GetTeamPlayersUseCase>()

    val runningMatches = listOf(
        mockk<CsgoMatch>(relaxed = true)
    )

    val upcomingMatchesPage1 = listOf(
        mockk<CsgoMatch>(relaxed = true),
        mockk<CsgoMatch>(relaxed = true),
        mockk<CsgoMatch>(relaxed = true)
    )

    val upcomingMatchesPage2 = listOf(
        mockk<CsgoMatch>(relaxed = true),
        mockk<CsgoMatch>(relaxed = true),
        mockk<CsgoMatch>(relaxed = true)
    )

    private lateinit var viewModel: MainViewModel

    @Before
    fun setup() {
        Dispatchers.setMain(testDispatcher)

        coEvery { getRunningMatchesUseCase.invoke() } returns Result.success(runningMatches)
        coEvery { getUpcomingMatchesUseCase.invoke(1, 20) } returns Result.success(upcomingMatchesPage1)
        coEvery { getUpcomingMatchesUseCase.invoke(2, 20) } returns Result.success(upcomingMatchesPage2)

        viewModel = MainViewModel(
            getRunningMatchesUseCase,
            getUpcomingMatchesUseCase,
            getTeamPlayersUseCase,
            ioDispatcher = testDispatcher
        )
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `initial state should be empty`() = testScope.runTest {

        assertEquals(emptyList<CsgoMatch>(), viewModel.matchesUiState.value.matches)
        assertFalse(viewModel.matchesUiState.value.isError)
        assertEquals(null, viewModel.selectedMatch.value)
        assertEquals(emptyList<CsgoPlayer>(), viewModel.team1Players.value)
        assertEquals(emptyList<CsgoPlayer>(), viewModel.team2Players.value)
        assertFalse(viewModel.isLoadingPlayers.value)
    }

    @Test
    fun `Init block should load initial CsgoMatches`() = testScope.runTest {
        advanceUntilIdle()

        val initialExpected = runningMatches + upcomingMatchesPage1
        assertEquals(initialExpected, viewModel.matchesUiState.value.matches)
    }

    @Test
    fun `getMoreMatches should append new matches`() = testScope.runTest {
        advanceUntilIdle()

        val initialExpected = runningMatches + upcomingMatchesPage1
        assertEquals(initialExpected, viewModel.matchesUiState.value.matches)

        viewModel.isLastPage = false
        viewModel.getMoreMatches()
        advanceUntilIdle()

        assertEquals(2, viewModel.currentPage)
        val expected = initialExpected + upcomingMatchesPage2
        assertEquals(expected, viewModel.matchesUiState.value.matches)
    }

    @Test
    fun `refreshMatches should reset and reload matches`() = testScope.runTest {
        advanceUntilIdle()

        viewModel.refreshMatches()
        advanceUntilIdle()

        val expectedAfterRefresh = runningMatches + upcomingMatchesPage1
        assertEquals(expectedAfterRefresh, viewModel.matchesUiState.value.matches)
    }

    @Test
    fun `getTeamPlayers should load players for both teams`() = testScope.runTest {
        val playersTeam1 = listOf(mockk<CsgoPlayer>(relaxed = true))
        val playersTeam2 = listOf(mockk<CsgoPlayer>(relaxed = true))

        coEvery { getTeamPlayersUseCase.invoke(10) } returns Result.success(playersTeam1)
        coEvery { getTeamPlayersUseCase.invoke(20) } returns Result.success(playersTeam2)

        viewModel.getTeamPlayers(10, 20)
        advanceUntilIdle()

        assertEquals(playersTeam1, viewModel.team1Players.value)
        assertEquals(playersTeam2, viewModel.team2Players.value)
        assertFalse(viewModel.isLoadingPlayers.value)
    }

    @Test
    fun `getTeamPlayers should handle errors and return empty lists`() = testScope.runTest {
        coEvery { getTeamPlayersUseCase.invoke(any()) } returns Result.failure(Exception("error"))

        viewModel.getTeamPlayers(10, 20)
        advanceUntilIdle()

        assertEquals(emptyList<CsgoPlayer>(), viewModel.team1Players.value)
        assertEquals(emptyList<CsgoPlayer>(), viewModel.team2Players.value)
        assertFalse(viewModel.isLoadingPlayers.value)
    }

}