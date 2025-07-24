package dev.luanramos.cstv.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import dev.luanramos.cstv.R
import dev.luanramos.cstv.domain.model.CsgoMatch
import dev.luanramos.cstv.utils.formatMatchDate

@Composable
fun MatchCard(
    modifier: Modifier = Modifier,
    csgoMatch: CsgoMatch
) {
    val context = LocalContext.current
    val colors = MaterialTheme.colorScheme
    val isLive = csgoMatch.status == "running"
    val unknownString = stringResource(R.string.unknown_label)

    Box(
        modifier = modifier
            .background(
                color = colors.surface,
                shape = RoundedCornerShape(16.dp)
            )
            .fillMaxWidth()
    ) {
        LabelMatchTime(
            text = if(isLive) stringResource(R.string.label_live) else csgoMatch.scheduledAt.formatMatchDate(context) ?: "Unknown",
            backgroundColor = if(isLive) MaterialTheme.colorScheme.errorContainer else MaterialTheme.colorScheme.outlineVariant
        )

        Column(
            modifier = Modifier.fillMaxWidth(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            TeamVersusRow(
                team1Name = csgoMatch.team1?.name ?: unknownString,
                team2Name = csgoMatch.team2?.name ?: unknownString,
                team1Image = csgoMatch.team1?.image,
                team2Image = csgoMatch.team2?.image
            )

            HorizontalDivider(
                color = MaterialTheme.colorScheme.outlineVariant,
                thickness = 1.dp
            )

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(12.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Box(
                    modifier = Modifier
                        .size(24.dp)
                ){
                    if (!csgoMatch.league.image.isNullOrEmpty()) {
                        AsyncImage(
                            model = csgoMatch.league.image,
                            contentDescription = "League Logo",
                            modifier = Modifier
                                .size(60.dp),
                            contentScale = ContentScale.Fit
                        )
                    }
                    else{
                        Box(
                            modifier = Modifier
                                .matchParentSize()
                                .background(colors.outline, shape = CircleShape)
                                .clip(CircleShape)
                        )
                    }
                }
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    text = buildLeagueAndSeriesString(
                        leagueName = csgoMatch.league.name,
                        seriesName = csgoMatch.serie.name,
                        seriesFullName = csgoMatch.serie.fullName
                    ),
                    color = colors.onSurfaceVariant,
                    fontSize = 12.sp
                )
            }
        }
    }
}

fun buildLeagueAndSeriesString(
    leagueName: String,
    seriesName: String,
    seriesFullName: String
): String {
    return when {
        seriesName.isNotEmpty() -> "$leagueName - $seriesName"
        seriesFullName.isNotEmpty() -> "$leagueName - $seriesFullName"
        else -> leagueName
    }
}
