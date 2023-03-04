package com.jefisu.manualplus.features_manual.domain

import androidx.annotation.DrawableRes
import com.jefisu.manualplus.R

enum class Category(@DrawableRes val image: Int) {
    Ultrassom(image = R.drawable.ic_ultrassom),
    Tomografia(image = R.drawable.ic_tomografia)
}