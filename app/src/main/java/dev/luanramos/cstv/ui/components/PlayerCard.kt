package dev.luanramos.cstv.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Composable
fun LeftPlayerCard(
    nickname: String,
    realName: String,
    modifier: Modifier = Modifier
) {
    PlayerCard(
        nickname = nickname,
        realName = realName,
        imageOnLeft = false,
        modifier = modifier
    )
}

@Composable
fun RightPlayerCard(
    nickname: String,
    realName: String,
    modifier: Modifier = Modifier
) {
    PlayerCard(
        nickname = nickname,
        realName = realName,
        imageOnLeft = true,
        modifier = modifier
    )
}

@Composable
private fun PlayerCard(
    nickname: String,
    realName: String,
    imageOnLeft: Boolean,
    modifier: Modifier = Modifier
) {
    val shape = if (imageOnLeft) {
        RoundedCornerShape(topStart = 16.dp, bottomStart = 16.dp)
    } else {
        RoundedCornerShape(topEnd = 16.dp, bottomEnd = 16.dp)
    }

    Box(
        modifier = Modifier
        .height(54.dp)
    ) {
        Box(
            modifier = Modifier
                .matchParentSize()
                .offset(y = 8.dp)
                .background(
                    color = MaterialTheme.colorScheme.surface,
                    shape = shape
                )
        )

        Row(
            modifier = Modifier
                .fillMaxSize(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            if (imageOnLeft) {
                PlayerImage(
                    modifier = Modifier.padding(start = 12.dp)
                )
            }

            Column(
                modifier = Modifier
                    .weight(1f)
                    .fillMaxHeight(),
                verticalArrangement = Arrangement.Bottom,
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                Text(
                    text = nickname,
                    style = MaterialTheme.typography.bodyMedium.copy(
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.onSurface
                    ),
                    textAlign = TextAlign.Center
                )
                Text(
                    text = realName,
                    style = MaterialTheme.typography.bodySmall.copy(
                        color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.5f)
                    ),
                    textAlign = TextAlign.Center
                )
            }

            if (!imageOnLeft) {
                PlayerImage(
                    modifier = Modifier.padding(end = 12.dp)
                )
            }
        }
    }
}

@Composable
@Preview(showBackground = true)
fun LeftPlayerCardPreview(){
    LeftPlayerCard(
        nickname = "Player",
        realName = "Fulano"
    )
}

@Composable
@Preview(showBackground = true)
fun RightPlayerCardPreview(){
    RightPlayerCard(
        nickname = "Player",
        realName = "Fulano"
    )
}
