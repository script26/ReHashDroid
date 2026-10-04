package com.example.rehashdroid.ui.navigation

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.rehashdroid.ui.screens.HashText
import com.example.rehashdroid.ui.screens.HashFile
import com.example.rehashdroid.ui.screens.CompareHash
import com.example.rehashdroid.ui.screens.Help
import com.example.rehashdroid.ui.screens.About

@Composable
@Preview(showBackground = true)
fun AppNavHost(
    navController: NavHostController = rememberNavController(),
    startDestination: Destination = Destination.HashText,
) {
    NavHost(
        navController,
        startDestination = startDestination.route
    ) {
        Destination.entries.forEach { destination ->
            composable(destination.route) {
                when (destination) {
                    Destination.HashFile -> HashFile()
                    Destination.HashText -> HashText()
                    Destination.CompareHash -> CompareHash()
                }
            }
        }
    }
}





