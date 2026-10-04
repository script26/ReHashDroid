package com.example.rehashdroid.ui.components

import android.content.Intent
import android.net.Uri
import com.example.rehashdroid.more_vert
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberTopAppBarState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.rememberNavController
import com.example.rehashdroid.R
import com.example.rehashdroid.ui.navigation.AppNavigation
import com.example.rehashdroid.ui.screens.Help
import com.example.rehashdroid.ui.screens.About

@OptIn(ExperimentalMaterial3Api::class)
@Composable
@Preview
fun TitleBar() {
    val context = LocalContext.current
    val packageName = context.packageName
    val url = "market://details?id=$packageName"
    val intent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
    var expanded by remember { mutableStateOf(false) }
    val navController = rememberNavController()
    var showHelpDialog by remember { mutableStateOf(false) }
    var showAboutDialog by remember { mutableStateOf(false) }

    Scaffold(
        topBar = {
            TopAppBar(
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer,
                    titleContentColor = MaterialTheme.colorScheme.primary,
                ),
                title = {
                    Text(
                        text = stringResource(R.string.app_name)
                    )
                },
                actions = {
                    IconButton(onClick = { expanded = !expanded }) {
                        Icon(
                            imageVector = more_vert,
                            contentDescription = "More options"
                        )
                    }
                    DropdownMenu(
                        expanded = expanded,
                        onDismissRequest = { expanded = false }
                    ) {
                        DropdownMenuItem(
                            text = {
                                Text (
                                    text = stringResource(R.string.label_menu_help)
                                )
                            },
                            onClick = {
                                showHelpDialog = true
                                expanded = false
                            }
                        )
                        DropdownMenuItem(
                            text = {
                                Text (
                                    text = stringResource(R.string.label_menu_rateit)
                                )
                            },
                            onClick = {
                                context.startActivity(intent)
                                expanded = false
                            }
                        )
                        DropdownMenuItem(
                            text = {
                                Text (
                                    text = stringResource(R.string.label_menu_about)
                                )
                            },
                            onClick = {
                                showAboutDialog = true
                                expanded = false
                            }
                        )
                    }
                }
            )
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {
            AppNavigation()
        }
    }

    if (showHelpDialog) {
        Help(onDismiss = {showHelpDialog = false})
    }
    if (showAboutDialog) {
        About(onDismiss = {showAboutDialog = false})
    }
}