package dev.luanramos.cstv.ui.view

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import dev.luanramos.cstv.R
import dev.luanramos.cstv.ui.components.MatchCard
import dev.luanramos.cstv.ui.viewmodel.MainViewModel

@Composable
fun MatchesScreen(
   viewModel: MainViewModel
) {
    val matchesListState by viewModel.matches.collectAsState()

    Surface(
        modifier = Modifier.fillMaxSize(),
        color = MaterialTheme.colorScheme.background
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(
                    vertical = 16.dp,
                    horizontal = 24.dp)
        ) {
            Spacer(modifier = Modifier.height(16.dp))

            Text(
                text = stringResource(R.string.label_matches),
                style = MaterialTheme.typography.titleLarge,
                color = MaterialTheme.colorScheme.onBackground,
                fontSize = 28.sp
            )

            Spacer(modifier = Modifier.height(16.dp))

            LazyColumn{
                items(matchesListState){ match ->
                    MatchCard(
                        csgoMatch = match
                    )
                    Spacer(Modifier.padding(top = 16.dp))
                }
            }
        }
    }
}
