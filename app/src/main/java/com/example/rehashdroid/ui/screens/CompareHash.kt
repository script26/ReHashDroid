package com.example.rehashdroid.ui.screens

import android.R.attr.onClick
import android.R.attr.text
import android.R.id.bold
import android.util.Log
import android.widget.EditText
import android.widget.Spinner
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.input.TextFieldLineLimits
import androidx.compose.foundation.text.input.clearText
import androidx.compose.foundation.text.input.rememberTextFieldState
import androidx.compose.foundation.text.input.setTextAndPlaceCursorAtEnd
import androidx.compose.foundation.text.input.setTextAndSelectAll
import androidx.compose.material3.Button
import androidx.compose.material3.Checkbox
import androidx.compose.material3.FilledTonalButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.rehashdroid.R
import androidx.compose.foundation.layout.Spacer


@Composable
@Preview(showBackground = true)
fun CompareHash(modifier: Modifier = Modifier) {
    var identicalHashes by remember { mutableStateOf(false) }
    var differentHashes by remember { mutableStateOf(false) }
    val firstHashState = rememberTextFieldState()
    val secondHashState = rememberTextFieldState()

    Box(
        modifier = Modifier
            .fillMaxSize()
        ,
        contentAlignment = Alignment.Center
    ) {
        Column(
            modifier = Modifier
                .widthIn(0.dp, 400.dp)
                .padding(20.dp)
            ,
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            // First Hash Value Input
            Text(
                stringResource(R.string.input_txt1),
            )
            Row(
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically
            ) {
                OutlinedTextField(
                    state = firstHashState,
                    label = { Text("First hash value") },
                    modifier = Modifier.weight(1f),
                    lineLimits = TextFieldLineLimits.SingleLine
                )
                Spacer(
                    modifier = Modifier.width(20.dp)
                )
                FilledTonalButton(onClick = {
                    firstHashState.clearText()
                }) {
                    Text(text = stringResource(R.string.clear_but))
                }
            }

            // Second Hash Value Input
            Text(
                stringResource(R.string.input_txt2)
            )
            Row(
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically
            ) {
                OutlinedTextField(
                    state = secondHashState,
                    label = { Text("Second hash value") },
                    modifier = Modifier.weight(1f),
                    lineLimits = TextFieldLineLimits.SingleLine
                )
                Spacer(
                    modifier = Modifier.width(20.dp)
                )
                FilledTonalButton(onClick = {
                    secondHashState.clearText()
                }) {
                    Text(text = stringResource(R.string.clear_but))
                }
            }

            Spacer(
                modifier = Modifier.height(20.dp)
            )

            // Compare Button
            FilledTonalButton(
                modifier =  Modifier.fillMaxWidth(),
                onClick = {
                    Log.d("TAG", firstHashState.text.toString())
                    if (firstHashState.text == secondHashState.text) {
                        differentHashes = false
                        identicalHashes = true
                        Log.d("TAG", "true")
                    } else if (firstHashState.text != secondHashState.text) {
                        identicalHashes = false
                        differentHashes = true
                        Log.d("TAG", "false")
                    }
                }
            ) {
                Text(text = stringResource(R.string.compare_but))
            }

            // Answer
            Row(
                modifier =  Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.Start
            ) {
                if (identicalHashes) {
                    Log.d("TAG", "identical text")
                    Text(
                        stringResource(R.string.IdenticalHashes),
                        color = Color.Green,
                        fontWeight = FontWeight.Bold
                    )
                }
                if (differentHashes) {
                    Log.d("TAG", "different text")
                    Text(
                        stringResource(R.string.DifferentHashes),
                        color = Color.Red,
                        fontWeight = FontWeight.Bold
                    )
                }
            }
        }
    }

}