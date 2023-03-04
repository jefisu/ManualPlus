package com.jefisu.manualplus.core.util

import android.content.Context
import androidx.annotation.StringRes
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import com.jefisu.manualplus.R

sealed class UiText {
    data class DynamicString(val value: String) : UiText()
    class StringResource(
        @StringRes val resId: Int,
        vararg val args: Any
    ) : UiText()

    @Composable
    fun asString(): String {
        return when (this) {
            is DynamicString -> value
            is StringResource -> stringResource(resId, *args)
        }
    }

    fun asString(context: Context): String {
        return when (this) {
            is DynamicString -> value
            is StringResource -> context.getString(resId, *args)
        }
    }

    companion object {
        fun unknownError(): UiText {
            return DynamicString("Unknown error")
        }

        fun internetUnavailable(): UiText {
            return StringResource(R.string.you_re_offline_check_your_internet_connection)
        }
    }
}