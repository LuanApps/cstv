package dev.luanramos.cstv.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun TeamVersusRow(
    team1Name: String,
    team2Name: String,
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 48.dp, bottom = 16.dp),
        horizontalArrangement = Arrangement.spacedBy(0.dp, Alignment.CenterHorizontally),
        verticalAlignment = Alignment.CenterVertically
    ) {
        TeamPlaceHolder(
            name = team1Name,
            imageOnLeft = false,
            modifier = Modifier
                .padding(end = 24.dp)
        )
        Text(
            text = "vs",
            color = MaterialTheme.colorScheme.outline,
            fontSize = 16.sp
        )
        TeamPlaceHolder(
            name = team2Name,
            imageOnLeft = true,
            modifier = Modifier
                .padding(start = 24.dp)
        )
    }
}

@Composable
@Preview(showBackground = true)
fun TeamVersusRowPreview(){
    TeamVersusRow(
        "Team1",
        "Team2"
    )
}