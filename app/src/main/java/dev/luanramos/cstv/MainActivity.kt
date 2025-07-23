package dev.luanramos.cstv

import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.core.splashscreen.SplashScreen
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.lifecycle.lifecycleScope
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import dagger.hilt.android.AndroidEntryPoint
import dev.luanramos.cstv.ui.theme.CSTVTheme
import dev.luanramos.cstv.ui.view.MatchesScreen
import dev.luanramos.cstv.ui.view.SplashScreen
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {

        val splashScreen = installSplashScreen()
        handleSplashScreen(splashScreen, 2000)

        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        setContent {
            CSTVTheme {
                val navController = rememberNavController()
                NavHost(
                    navController = navController,
                    startDestination = "splash"
                ) {
                    composable("splash") {
                        SplashScreen(
                            onFinish = {
                                navController.navigate("home"){
                                    popUpTo("splash") { inclusive = true }
                                }
                            }
                        )
                    }
                    composable("home") { MatchesScreen() }
                }
            }
        }
    }

    fun ComponentActivity.handleSplashScreen(splashScreen: SplashScreen, duration: Long) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
            var keepSplash = true

            splashScreen.setKeepOnScreenCondition { keepSplash }
            lifecycleScope.launch {
                delay(duration)
                keepSplash = false
            }
        }
    }
}