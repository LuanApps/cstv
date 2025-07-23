package dev.luanramos.cstv.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun TeamPlaceHolder(
    name: String,
    modifier: Modifier = Modifier,
    imageOnLeft: Boolean
) {
    val colors = MaterialTheme.colorScheme

    val alignment = if (imageOnLeft) Alignment.Start else Alignment.End
    val textAlign = if (imageOnLeft) TextAlign.Start else TextAlign.End

    Column(
        modifier = modifier
            .width(120.dp)
            .height(100.dp),
        horizontalAlignment = alignment
    ) {
        Box(
            modifier = Modifier
                .size(60.dp)
                .clip(CircleShape)
                .background(colors.outline)
        )
        Spacer(modifier = Modifier.height(12.dp))
        Text(
            text = name,
            color = colors.onSurface,
            fontSize = 12.sp,
            textAlign = textAlign,
            modifier = Modifier
                .padding(horizontal = 12.dp),
            style = TextStyle(
                lineHeight = 14.sp
            )
        )
    }
}

@Composable
@Preview(showBackground = true)
fun TeamPlaceholderPreview(){
    TeamPlaceHolder(
        name = "A real long team name",
        imageOnLeft = false
    )
}