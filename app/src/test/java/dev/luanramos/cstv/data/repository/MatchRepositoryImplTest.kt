package dev.luanramos.cstv.data.repository

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import dev.luanramos.cstv.data.datasource.CsgoApiService
import dev.luanramos.cstv.data.dto.CsgoMatchDto
import dev.luanramos.cstv.domain.model.CsgoMatch
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.every
import io.mockk.mockk
import junit.framework.TestCase.assertEquals
import junit.framework.TestCase.assertTrue
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class MatchRepositoryImplTest {

    private lateinit var api: CsgoApiService
    private lateinit var repository: MatchRepositoryImpl

    @get:Rule
    val instantExecutorRule = InstantTaskExecutorRule()
    private val testDispatcher = StandardTestDispatcher()

    @Before
    fun setUp() {
        Dispatchers.setMain(testDispatcher)
        api = mockk()
        repository = MatchRepositoryImpl(api)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `getRunningMatches should return success when API call is successful`() = runTest {

        val dto = mockk<CsgoMatchDto>()
        val domain = mockk<CsgoMatch>()
        every { dto.toDomain() } returns domain
        coEvery { api.getRunningMatches(any()) } returns listOf(dto)

        val result = repository.getRunningMatches()

        assertTrue(result.isSuccess)
        assertEquals(listOf(domain), result.getOrNull())
        coVerify { api.getRunningMatches(any()) }
    }

    @Test
    fun `getRunningMatches should return failure when API throws exception`() = runTest {
        val exception = RuntimeException("API error")
        coEvery { api.getRunningMatches(any()) } throws exception

        val result = repository.getRunningMatches()

        assertTrue(result.isFailure)
        assertEquals(exception, result.exceptionOrNull())
        coVerify { api.getRunningMatches(any()) }
    }

    @Test
    fun `getUpcomingMatches should return success when API call is successful`() = runTest {
        val dto = mockk<CsgoMatchDto>()
        val domain = mockk<CsgoMatch>()
        every { dto.toDomain() } returns domain
        coEvery {
            api.getUpcomingMatches(
                pageNumber = 1,
                pageSize = 20,
                apiToken = any()
            )
        } returns listOf(dto)

        val result = repository.getUpcomingMatches(1, 20)

        assertTrue(result.isSuccess)
        assertEquals(listOf(domain), result.getOrNull())
        coVerify {
            api.getUpcomingMatches(pageNumber = 1, pageSize = 20, apiToken = any())
        }
    }

    @Test
    fun `getUpcomingMatches should return failure when API throws exception`() = runTest {
        val exception = RuntimeException("API error")
        coEvery {
            api.getUpcomingMatches(pageNumber = 1, pageSize = 20, apiToken = any())
        } throws exception

        val result = repository.getUpcomingMatches(1, 20)

        assertTrue(result.isFailure)
        assertEquals(exception, result.exceptionOrNull())
        coVerify {
            api.getUpcomingMatches(pageNumber = 1, pageSize = 20, apiToken = any())
        }
    }
}