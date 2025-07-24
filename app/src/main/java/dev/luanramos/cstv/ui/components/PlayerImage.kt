package dev.luanramos.cstv.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage

@Composable
fun PlayerImage(
    modifier: Modifier = Modifier,
    image: String
) {
    var hasError by remember { mutableStateOf(false) }

    Box(
        modifier = modifier
            .size(60.dp)
            .clip(RoundedCornerShape(8.dp))
    ) {
        if (image.isNotEmpty() && !hasError) {
            AsyncImage(
                model = image,
                contentDescription = "Player Image",
                modifier = Modifier.matchParentSize(),
                contentScale = ContentScale.Fit,
                onError = {
                    hasError = true
                }
            )
        }

        if (image.isEmpty() || hasError) {
            Box(
                modifier = Modifier
                    .matchParentSize()
                    .background(Color.LightGray)
            )
        }
    }
}

@Composable
@Preview(showBackground = true)
fun PlayerImagePreview(){
    PlayerImage(
        image = ""
    )
}