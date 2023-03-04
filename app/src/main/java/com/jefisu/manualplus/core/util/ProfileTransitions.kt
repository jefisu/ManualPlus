package com.jefisu.manualplus.core.util

import androidx.compose.animation.AnimatedContentScope
import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.navigation.NavBackStackEntry
import com.ramcosta.composedestinations.spec.DestinationStyle

@OptIn(ExperimentalAnimationApi::class)
object ProfileTransitions : DestinationStyle.Animated {
    override fun AnimatedContentScope<NavBackStackEntry>.enterTransition(): EnterTransition? {
        return slideIntoContainer(
            towards = AnimatedContentScope.SlideDirection.Start,
            animationSpec = tween(300),
            initialOffset = { it / 10 }
        ) + fadeIn()
    }

    override fun AnimatedContentScope<NavBackStackEntry>.exitTransition(): ExitTransition? {
        return null
    }

    override fun AnimatedContentScope<NavBackStackEntry>.popEnterTransition(): EnterTransition? {
        return slideIntoContainer(
            towards = AnimatedContentScope.SlideDirection.End,
            animationSpec = tween(300),
            initialOffset = { it / 10 }
        ) + fadeIn()
    }

    override fun AnimatedContentScope<NavBackStackEntry>.popExitTransition(): ExitTransition? {
        return null
    }
}