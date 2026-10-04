package com.example.rehashdroid.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.rehashdroid.R
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.ui.Alignment
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.TextButton
import androidx.compose.ui.window.DialogProperties

@Composable
fun About(
    onDismiss: () -> Unit
) {
    AlertDialog(
        properties = DialogProperties(
            usePlatformDefaultWidth = false
        ),
        modifier = Modifier
            .fillMaxWidth(0.95f)
            .wrapContentWidth()
            .fillMaxHeight(0.95f)
            .wrapContentHeight()
        ,
        onDismissRequest = onDismiss,
        title = {
            Text(
                text = stringResource(R.string.label_menu_about)
            )
        },
        text = {
                Column(
                    modifier = Modifier
                        .verticalScroll(rememberScrollState())
                    ,
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Image(
                        painter = painterResource(id = R.mipmap.ic_launcher_foreground),
                        contentDescription = "App icon",
                        modifier = Modifier.size(64.dp),
                    )
                    Text(
                        stringResource(R.string.app_name),
                        modifier = Modifier.padding(2.dp),
                        textAlign = TextAlign.Center
                    )
                    Text(
                        stringResource(R.string.version),
                        modifier = Modifier.padding(2.dp),
                        textAlign = TextAlign.Center
                    )
                    Text(
                        stringResource(R.string.copyright),
                        modifier = Modifier.padding(2.dp),
                        textAlign = TextAlign.Center
                    )
                    Text(
                        stringResource(R.string.contact),
                        modifier = Modifier.padding(2.dp),
                        textAlign = TextAlign.Center
                    )
                    Text(
                        stringResource(R.string.LicenseTitle),
                        modifier = Modifier.padding(2.dp),
                        textAlign = TextAlign.Center
                    )
                    Text(
                        stringResource(R.string.License),
                        modifier = Modifier.padding(2.dp),
                    )
                }
        },
        confirmButton = {
            TextButton(
                onClick = onDismiss
            ) {
                Text("Close")
            }
        },
    )
}