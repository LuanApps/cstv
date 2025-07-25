package dev.luanramos.cstv.ui.view

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.pulltorefresh.PullToRefreshBox
import androidx.compose.material3.pulltorefresh.PullToRefreshDefaults.Indicator
import androidx.compose.material3.pulltorefresh.rememberPullToRefreshState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import dev.luanramos.cstv.R
import dev.luanramos.cstv.ui.components.MatchCard
import dev.luanramos.cstv.ui.viewmodel.MainViewModel
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.filter

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MatchesScreen(
   viewModel: MainViewModel,
   onMatchClick: () -> Unit
) {
    val matchesUiListState by viewModel.matchesUiState.collectAsState()
    val pullRefreshState = rememberPullToRefreshState()

    var isRefreshing by remember { mutableStateOf(false) }

    val listState = rememberLazyListState()
    val isLoadingMoreMatches = remember { mutableStateOf(false) }
    val isInitialLoading = remember { mutableStateOf(true) }

    LaunchedEffect(matchesUiListState) {
        if (matchesUiListState.matches.isNotEmpty() || matchesUiListState.isError) {
            isRefreshing = false
            isInitialLoading.value = false
        }
    }

    LaunchedEffect(listState) {
        snapshotFlow {
            val lastVisibleItemIndex = listState.layoutInfo.visibleItemsInfo.lastOrNull()?.index
            val totalItems = listState.layoutInfo.totalItemsCount
            lastVisibleItemIndex to totalItems
        }
            .distinctUntilChanged()
            .filter { (lastVisible, total) ->
                lastVisible != null && lastVisible >= total - 1
            }
            .collect {
                if (!isLoadingMoreMatches.value) {
                    isLoadingMoreMatches.value = true
                    viewModel.getMoreMatches()
                    isLoadingMoreMatches.value = false
                }
            }
    }

    Surface(
        modifier = Modifier.fillMaxSize(),
        color = MaterialTheme.colorScheme.background
    ) {
        if(isInitialLoading.value){
            Box(
                modifier = Modifier
                    .fillMaxSize()
            ) {
                CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
            }
        }
        else {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(
                        vertical = 16.dp,
                        horizontal = 24.dp
                    )
            ) {
                Spacer(modifier = Modifier.height(16.dp))
                Text(
                    text = stringResource(R.string.label_matches),
                    style = MaterialTheme.typography.titleLarge,
                    color = MaterialTheme.colorScheme.onBackground,
                    fontSize = 28.sp
                )

                Spacer(modifier = Modifier.height(16.dp))

                PullToRefreshBox(
                    isRefreshing = isRefreshing,
                    onRefresh = {
                        isRefreshing = true
                        viewModel.refreshMatches()
                    },
                    state = pullRefreshState,
                    indicator = {
                        Indicator(
                            modifier = Modifier.align(Alignment.Center),
                            isRefreshing = isRefreshing,
                            containerColor = MaterialTheme.colorScheme.primaryContainer,
                            color = MaterialTheme.colorScheme.onPrimaryContainer,
                            state = pullRefreshState
                        )
                    }
                ) {
                    LazyColumn(
                        modifier = Modifier.fillMaxSize(),
                        state = listState
                    ) {
                        if (matchesUiListState.isError) {
                            item {
                                Box(
                                    modifier = Modifier
                                        .fillParentMaxSize(),
                                    contentAlignment = Alignment.Center
                                ) {
                                    Text(
                                        text = stringResource(R.string.label_error_fetch_data),
                                        style = MaterialTheme.typography.bodyLarge,
                                        color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.7f)
                                    )
                                }
                            }
                        } else {
                            items(matchesUiListState.matches) { match ->
                                Box(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .clickable {
                                            viewModel.setSelectedMatch(match)
                                            viewModel.getTeamPlayers(
                                                team1Id = match.team1?.id,
                                                team2Id = match.team2?.id
                                            )
                                            onMatchClick()
                                        }
                                ) {
                                    MatchCard(csgoMatch = match)
                                }
                                Spacer(modifier = Modifier.height(16.dp))
                            }
                        }
                    }
                }
            }
        }
    }
}
