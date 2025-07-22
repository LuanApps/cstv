package dev.luanramos.cstv.ui.view

import android.os.Build
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import dev.luanramos.cstv.R
import kotlinx.coroutines.delay

@Composable
fun SplashScreen(onFinish: () -> Unit) {
    if (Build.VERSION.SDK_INT < 31) {
        LaunchedEffect(Unit) {
            delay(2000)
            onFinish()
        }

        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.Black),
            contentAlignment = Alignment.Center
        ) {
            Image(
                painter = painterResource(id = R.drawable.fuze_logo),
                contentDescription = "Splash Logo",
                modifier = Modifier.size(100.dp)
            )
        }
    } else {
        LaunchedEffect(Unit) {
            onFinish()
        }
    }
}

@Composable
@Preview(showBackground = true)
fun SplashScreenPreview(){
    SplashScreen {  }
}

