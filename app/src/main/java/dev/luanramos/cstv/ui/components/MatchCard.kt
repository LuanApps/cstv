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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun MatchCard(
    modifier: Modifier = Modifier,
    team1Name: String = "Unknown ",
    team2Name: String = "Unknown",
    matchTime: String = "Unknown",
    matchTimeBackgroundColor: Color = MaterialTheme.colorScheme.outlineVariant,
    leagueAndSeries: String = "Unknown",
    leagueLogo: String ?= null,

) {
    val colors = MaterialTheme.colorScheme

    Box(
        modifier = modifier
            .background(
                color = colors.surface,
                shape = RoundedCornerShape(16.dp)
            )
            .fillMaxWidth()
    ) {
        LabelMatchTime(
            text = matchTime,
            backgroundColor = matchTimeBackgroundColor
        )

        Column(
            modifier = Modifier.fillMaxWidth(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 48.dp, bottom = 16.dp),
                horizontalArrangement = Arrangement.spacedBy(24.dp, Alignment.CenterHorizontally),
                verticalAlignment = Alignment.CenterVertically
            ) {
                TeamPlaceholder(
                    name = team1Name
                )
                Text(
                    text = "vs",
                    color = colors.outline,
                    fontSize = 16.sp
                )
                TeamPlaceholder(
                    name = team2Name
                )
            }

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
                        .background(colors.outline, shape = CircleShape)
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    text = leagueAndSeries,
                    color = colors.onSurfaceVariant,
                    fontSize = 12.sp
                )
            }
        }
    }
}

@Composable
@Preview(showBackground = true)
fun MatchCardPreview(){
    MatchCard()
}
