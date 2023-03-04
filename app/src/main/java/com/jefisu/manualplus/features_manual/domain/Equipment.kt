package com.jefisu.manualplus.features_manual.domain

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Equipment(
    val name: String,
    val description: String,
    val steps: List<String>,
    val serialNumber: Int,
    val releaseYear: Int,
    val rate: Float?,
    val imageRemotePath: String,
    val category: String,
    val id: String
): Parcelable