package dev.luanramos.cstv.ui.view

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import dev.luanramos.cstv.R
import dev.luanramos.cstv.ui.components.LeftPlayerCard
import dev.luanramos.cstv.ui.components.RightPlayerCard
import dev.luanramos.cstv.ui.components.TeamVersusRow
import dev.luanramos.cstv.ui.viewmodel.MainViewModel
import dev.luanramos.cstv.utils.buildLeagueAndSeriesString
import dev.luanramos.cstv.utils.formatMatchDate
import dev.luanramos.cstv.utils.handlePlayerFullName

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MatchDetailsScreen(
    viewModel: MainViewModel,
    onBackPressed: () -> Unit
) {

    val context = LocalContext.current
    val selectedMatch by viewModel.selectedMatch.collectAsState()
    val team1Players by viewModel.team1Players.collectAsState()
    val team2Players by viewModel.team2Players.collectAsState()
    val isLoadingPlayers by viewModel.isLoadingPlayers.collectAsState()
    val scrollState = rememberScrollState()

    val unknownString = stringResource(R.string.label_unknown)

    val leagueSeriesString: String = selectedMatch?.let { match ->
        buildLeagueAndSeriesString(
            leagueName = match.league.name,
            seriesName = match.serie.name,
            seriesFullName = match.serie.fullName)
    } ?: unknownString

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
                    containerColor = MaterialTheme.colorScheme.background
                ),
                title = { Text(text = leagueSeriesString ) },
                navigationIcon = {
                    IconButton(onClick = { onBackPressed() }) {
                        Icon(
                            imageVector = Icons.Default.ArrowBack,
                            contentDescription = stringResource(R.string.label_back_navigation)
                        )
                    }
                }
            )
        },
        content = { paddingValues ->
            Box(
                modifier = Modifier
                    .padding(paddingValues)
                    .fillMaxSize()
            ){
                if (isLoadingPlayers) {
                    CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
                } else {
                    Column(
                        modifier = Modifier
                            .verticalScroll(scrollState),
                        verticalArrangement = Arrangement.spacedBy(16.dp),
                    ) {
                        Spacer(modifier = Modifier.height(8.dp))
                        Row {
                            TeamVersusRow(
                                team1Name = selectedMatch?.team1?.name ?: unknownString,
                                team2Name = selectedMatch?.team2?.name ?: unknownString,
                                team1Image = selectedMatch?.team1?.image,
                                team2Image = selectedMatch?.team2?.image
                            )
                        }
                        Row {
                            Text(
                                modifier = Modifier
                                    .fillMaxWidth(),
                                text = selectedMatch?.scheduledAt?.formatMatchDate(context)
                                    ?: unknownString,
                                textAlign = TextAlign.Center
                            )
                        }
                        Row(
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Column(
                                verticalArrangement = Arrangement.spacedBy(12.dp),
                                modifier = Modifier.weight(1f)
                            ) {
                                when (team1Players.size) {
                                    0 -> repeat(5) { index ->
                                        LeftPlayerCard(
                                            nickname = unknownString,
                                            realName = unknownString,
                                            image = ""
                                        )
                                    }

                                    else -> {
                                        team1Players
                                            .filter { it.isActive }
                                            .forEach { player ->
                                                LeftPlayerCard(
                                                    nickname = player.name,
                                                    realName = handlePlayerFullName(
                                                        firstName = player.firstName,
                                                        lastName = player.lastName,
                                                        defaultName = unknownString
                                                    ),
                                                    image = player.image ?: ""
                                                )
                                            }
                                    }
                                }
                            }

                            Spacer(modifier = Modifier.width(12.dp))

                            Column(
                                verticalArrangement = Arrangement.spacedBy(12.dp),
                                modifier = Modifier.weight(1f)
                            ) {
                                when (team2Players.size) {
                                    0 -> {
                                        repeat(5) { index ->
                                            RightPlayerCard(
                                                nickname = unknownString,
                                                realName = unknownString,
                                                image = ""
                                            )
                                        }
                                    }

                                    else -> {
                                        team2Players
                                            .filter { it.isActive }
                                            .forEach { player ->
                                                RightPlayerCard(
                                                    nickname = player.name,
                                                    realName = handlePlayerFullName(
                                                        firstName = player.firstName,
                                                        lastName = player.lastName,
                                                        defaultName = unknownString
                                                    ),
                                                    image = player.image ?: ""
                                                )
                                            }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    )
}

