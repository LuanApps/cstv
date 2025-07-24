package dev.luanramos.cstv.ui.viewmodel

import android.util.Log
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
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val getRunningMatchesUseCase: GetRunningMatchesUseCase,
    private val getUpcomingMatchesUseCase: GetUpcomingMatchesUseCase,
    private val getTeamPlayersUseCase: GetTeamPlayersUseCase
): ViewModel() {

    private val _matches = MutableStateFlow<List<CsgoMatch>>(emptyList())
    val matches: StateFlow<List<CsgoMatch>> = _matches

    init {
        getRunningMatches()
        getUpcomingMatches(
            pageNumber = 1,
            pageSize = 20
        )
    }

    fun getRunningMatches(){
        viewModelScope.launch(Dispatchers.IO) {
            val result = getRunningMatchesUseCase()
            result.onSuccess { matches ->
                _matches.value = matches
            }.onFailure { e ->
                Log.e("MatchesViewModel", "Failed to fetch matches", e)
            }

        }
    }

    fun getUpcomingMatches(
        pageNumber: Int,
        pageSize: Int
    ){
        viewModelScope.launch(Dispatchers.IO) {
            val result = getUpcomingMatchesUseCase(
                pageNumber = pageNumber,
                pageSize = pageSize
            )
            result.onSuccess { newMatches ->
                val updatedList = _matches.value + newMatches
                _matches.value = updatedList
            }
        }
    }
}