package dev.luanramos.cstv

import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.Modifier
import androidx.core.splashscreen.SplashScreen
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.core.view.WindowCompat
import androidx.lifecycle.lifecycleScope
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import dagger.hilt.android.AndroidEntryPoint
import dev.luanramos.cstv.ui.theme.CSTVTheme
import dev.luanramos.cstv.ui.view.MatchDetailsScreen
import dev.luanramos.cstv.ui.view.MatchesScreen
import dev.luanramos.cstv.ui.view.SplashScreen
import dev.luanramos.cstv.ui.viewmodel.MainViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    private val viewModel: MainViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val splashScreen = installSplashScreen()
        handleSplashScreen(splashScreen, 2000)
        enableEdgeToEdge()

        setContent {

            SideEffect {
                WindowCompat.getInsetsController(window, window.decorView).apply {
                    isAppearanceLightStatusBars = false
                }
            }

            CSTVTheme {
                val navController = rememberNavController()

                Scaffold(
                    content = { innerPadding ->
                        NavHost(
                            navController = navController,
                            startDestination = "splash",
                            modifier = Modifier.padding(innerPadding)
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
                            composable("home") {
                                MatchesScreen(
                                    viewModel = viewModel,
                                    onMatchClick = {
                                        navController.navigate("details") {
                                            popUpTo("home") { inclusive = false }
                                            launchSingleTop = true
                                        }
                                    }
                                )
                            }
                            composable("details") {
                                MatchDetailsScreen(
                                    viewModel = viewModel,
                                    onBackPressed = {
                                        navController.popBackStack("home", inclusive = false)
                                    }
                                )
                            }
                        }
                    }
                )
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