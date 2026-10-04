package com.example.rehashdroid.ui.navigation

import androidx.annotation.StringRes
import androidx.compose.ui.res.stringResource
import com.example.rehashdroid.R

enum class Destination(
    val route: String,
    @StringRes val labelRes: Int
) {
    HashText(
        route = "tab_text",
        labelRes = R.string.tab_text
    ),
    HashFile(
        route = "tab_file",
        labelRes = R.string.tab_file
    ),
    CompareHash(
        route = "tab_compare",
        labelRes = R.string.tab_compare
    )
}