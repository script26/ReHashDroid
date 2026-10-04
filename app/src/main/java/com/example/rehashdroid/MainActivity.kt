package com.example.rehashdroid

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.example.rehashdroid.ui.components.TitleBar
import com.example.rehashdroid.ui.navigation.AppNavigation
import com.example.rehashdroid.ui.theme.ReHashDroidTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            ReHashDroidTheme {
                TitleBar()
            }
        }
    }
}
