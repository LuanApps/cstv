package dev.luanramos.cstv.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import dev.luanramos.cstv.domain.model.CsgoMatch
import dev.luanramos.cstv.domain.usecase.GetRunningMatchesUseCase
import dev.luanramos.cstv.domain.usecase.GetTeamPlayersUseCase
import dev.luanramos.cstv.domain.usecase.GetUpcomingMatchesUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val getRunningMatchesUseCase: GetRunningMatchesUseCase,
    private val getUpcomingMatchesUseCase: GetUpcomingMatchesUseCase,
    private val getTeamPlayersUseCase: GetTeamPlayersUseCase
): ViewModel() {

    private val _matches = MutableStateFlow<List<CsgoMatch>>(emptyList())
    val matches: StateFlow<List<CsgoMatch>> = _matches

    private var currentPage = 1
    private var isLastPage = false

    init {
        getInitialMatchesData()
    }

    private fun getInitialMatchesData() {
        currentPage = 1
        isLastPage = false

        viewModelScope.launch {
            val runningMatches = withContext(Dispatchers.IO) {
                getRunningMatchesUseCase().getOrElse { emptyList() }
            }

            val upcomingMatches = withContext(Dispatchers.IO) {
                getUpcomingMatchesUseCase(1, 20).getOrElse { emptyList() }
            }

            _matches.value = runningMatches + upcomingMatches

            if (upcomingMatches.size < 20) {
                isLastPage = true
            }
        }
    }

    fun loadMoreMatches() {
        if (isLastPage) return

        currentPage += 1
        viewModelScope.launch {
            val result = getUpcomingMatchesUseCase(currentPage, 20)
            result.onSuccess { newMatches ->
                if (newMatches.isEmpty()) {
                    isLastPage = true
                } else {
                    _matches.update { it + newMatches }
                    if (newMatches.size < 20) isLastPage = true
                }
            }.onFailure {
                currentPage -= 1
            }
        }
    }

    private fun resetMatchesList(){
        _matches.value = listOf()
    }

    fun refreshMatches() {
        viewModelScope.launch {
            resetMatchesList()
            getInitialMatchesData()
        }
    }
}