package com.jefisu.manualplus

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.material.Surface
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalFocusManager
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.core.view.WindowCompat
import com.google.accompanist.navigation.animation.rememberAnimatedNavController
import com.google.accompanist.navigation.material.ExperimentalMaterialNavigationApi
import com.jefisu.manualplus.core.ui.theme.ManualPlusTheme
import com.jefisu.manualplus.core.ui.theme.seed
import com.jefisu.manualplus.destinations.DirectionDestination
import com.jefisu.manualplus.destinations.HomeScreenDestination
import com.jefisu.manualplus.destinations.SignInScreenDestination
import com.jefisu.manualplus.features_manual.presentation.home.HomeScreen
import com.ramcosta.composedestinations.DestinationsNavHost
import com.ramcosta.composedestinations.animations.rememberAnimatedNavHostEngine
import com.ramcosta.composedestinations.manualcomposablecalls.composable
import com.ramcosta.composedestinations.navigation.dependency
import com.stevdzasan.messagebar.rememberMessageBarState
import dagger.hilt.android.AndroidEntryPoint
import io.realm.kotlin.mongodb.App
import javax.inject.Inject

@OptIn(ExperimentalAnimationApi::class, ExperimentalMaterialNavigationApi::class)
@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    @Inject
    lateinit var realmApp: App
    private var keepSplashOpened = true

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        WindowCompat.setDecorFitsSystemWindows(window, false)
        installSplashScreen()
        setContent {
            ManualPlusTheme(darkTheme = true) {
                Surface(
                    color = seed,
                    modifier = Modifier.fillMaxSize()
                ) {
                    DestinationsNavHost(
                        navGraph = NavGraphs.root,
                        startRoute = getStartDestination(),
                        navController = rememberAnimatedNavController(),
                        engine = rememberAnimatedNavHostEngine(),
                        manualComposableCallsBuilder = {
                            composable(HomeScreenDestination) {
                                HomeScreen(
                                    navController = navController,
                                    onDataLoaded = { keepSplashOpened = false }
                                )
                            }
                        },
                        dependenciesContainerBuilder = {
                            dependency(applicationContext)
                            dependency(LocalFocusManager.current)
                            dependency(rememberMessageBarState())
                        },
                        modifier = Modifier
                            .statusBarsPadding()
                            .navigationBarsPadding()
                            .imePadding()
                    )
                }
            }
        }
    }

    private fun getStartDestination(): DirectionDestination {
        val user = realmApp.currentUser
        return if (user != null && user.loggedIn) {
            HomeScreenDestination
        } else {
            keepSplashOpened = false
            SignInScreenDestination
        }
    }
}