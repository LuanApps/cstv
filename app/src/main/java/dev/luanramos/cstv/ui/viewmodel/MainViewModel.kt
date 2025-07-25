package dev.luanramos.cstv.ui.viewmodel

import androidx.annotation.VisibleForTesting
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import dev.luanramos.cstv.di.IoDispatcher
import dev.luanramos.cstv.domain.model.CsgoMatch
import dev.luanramos.cstv.domain.model.CsgoPlayer
import dev.luanramos.cstv.domain.usecase.GetRunningMatchesUseCase
import dev.luanramos.cstv.domain.usecase.GetTeamPlayersUseCase
import dev.luanramos.cstv.domain.usecase.GetUpcomingMatchesUseCase
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

data class MatchesUiState(
    val matches: List<CsgoMatch> = emptyList(),
    val isError: Boolean = false
)

@HiltViewModel
class MainViewModel @Inject constructor(
    private val getRunningMatchesUseCase: GetRunningMatchesUseCase,
    private val getUpcomingMatchesUseCase: GetUpcomingMatchesUseCase,
    private val getTeamPlayersUseCase: GetTeamPlayersUseCase,
    @IoDispatcher private val ioDispatcher: CoroutineDispatcher
): ViewModel() {

    private val _selectedMatch = MutableStateFlow<CsgoMatch?>(null)
    val selectedMatch: StateFlow<CsgoMatch?> = _selectedMatch

    private val _team1Players = MutableStateFlow<List<CsgoPlayer>>(listOf())
    val team1Players: StateFlow<List<CsgoPlayer>> = _team1Players

    private val _team2Players = MutableStateFlow<List<CsgoPlayer>>(listOf())
    val team2Players: StateFlow<List<CsgoPlayer>> = _team2Players

    private val _matchesUiState = MutableStateFlow(MatchesUiState())
    val matchesUiState: StateFlow<MatchesUiState> = _matchesUiState

    private val _isLoadingPlayers = MutableStateFlow(false)
    val isLoadingPlayers: StateFlow<Boolean> = _isLoadingPlayers

    var currentPage = 1
        private set

    @VisibleForTesting
    internal var isLastPage = false

    init {
        getInitialMatchesData()
    }

    private fun getInitialMatchesData() {
        currentPage = 1
        isLastPage = false

        viewModelScope.launch {
            val runningMatches = withContext(ioDispatcher) {
                getRunningMatchesUseCase().getOrElse { emptyList() }
            }

            val upcomingMatches = withContext(ioDispatcher) {
                getUpcomingMatchesUseCase(1, 20).getOrElse { emptyList() }
            }

            _matchesUiState.value = MatchesUiState(
                matches = runningMatches + upcomingMatches,
                isError = (runningMatches + upcomingMatches).isEmpty()
            )

            if (upcomingMatches.size < 20) {
                isLastPage = true
            }
        }
    }

    fun getMoreMatches() {
        if (isLastPage) return

        currentPage += 1
        viewModelScope.launch {
            val result = getUpcomingMatchesUseCase(currentPage, 20)
            result.onSuccess { newMatches ->
                if (newMatches.isEmpty()) {
                    isLastPage = true
                } else {
                    _matchesUiState.update { current ->
                        current.copy(
                            matches = current.matches + newMatches,
                            isError = false
                        )
                    }
                    if (newMatches.size < 20) isLastPage = true
                }
            }.onFailure {
                currentPage -= 1
            }
        }
    }

    private fun resetMatchesList(){
        _matchesUiState.value = MatchesUiState()
    }

    fun refreshMatches() {
        viewModelScope.launch {
            resetMatchesList()
            getInitialMatchesData()
        }
    }

    fun setSelectedMatch(csgoMatch: CsgoMatch){
        _selectedMatch.value = csgoMatch
    }

    fun getTeamPlayers(
        team1Id: Long?,
        team2Id: Long?
    ){
        viewModelScope.launch {
            resetAllTeamPlayers()
            _isLoadingPlayers.value = true

            withContext(ioDispatcher){
                team1Id?.let {  id ->
                    val fetchedTeam1Players = getTeamPlayersUseCase(id)
                    fetchedTeam1Players.onSuccess { players ->
                        _team1Players.value = players
                    }.onFailure {
                        _team1Players.value = listOf()
                    }
                }
            }

            withContext(ioDispatcher){
                team2Id?.let { id ->
                    val fetchedTeam2Players = getTeamPlayersUseCase(id)
                    fetchedTeam2Players.onSuccess { players ->
                        _team2Players.value = players
                    }.onFailure {
                        _team2Players.value = listOf()
                    }
                }
            }
            _isLoadingPlayers.value = false
        }
    }

    private fun resetAllTeamPlayers(){
        _team1Players.value = listOf()
        _team2Players.value = listOf()
    }
}