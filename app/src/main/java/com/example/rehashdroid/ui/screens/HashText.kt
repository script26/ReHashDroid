package com.example.rehashdroid.ui.screens

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.text.input.TextFieldLineLimits
import androidx.compose.foundation.text.input.TextFieldState
import androidx.compose.foundation.text.input.clearText
import androidx.compose.foundation.text.input.rememberTextFieldState
import androidx.compose.foundation.text.input.setTextAndPlaceCursorAtEnd
import androidx.compose.material3.Checkbox
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.FilledTonalButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.stringArrayResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.rehashdroid.R
import com.example.rehashdroid.logic.HashFunctionOperator

private var answer = "testing"
private val hashOpe = HashFunctionOperator()
private var msToHash = ""

@OptIn(ExperimentalMaterial3Api::class)
@Composable
@Preview(showBackground = true)
fun HashText() {
    var checked by remember { mutableStateOf(false) }
    val options = stringArrayResource(R.array.Algo_Array)
    val hashFunction = rememberTextFieldState(
        options[5]
    )
    var expanded by remember { mutableStateOf(false) }
    val focusRequester = remember { FocusRequester() }
    val focusManager = LocalFocusManager.current
    val inputHash = rememberTextFieldState()
    val ctx = LocalContext.current
    val text = "Saved to clipboard!"
    val duration = Toast.LENGTH_SHORT
    val toast = Toast.makeText(ctx, text, duration)
    val manager = ctx.getSystemService(ClipboardManager::class.java)
    var calculated by remember { mutableStateOf(false) }

    Box(
        modifier = Modifier.fillMaxSize()
        ,
        contentAlignment = Alignment.Center
    ) {
        Column(
            modifier = Modifier
                .widthIn(0.dp, 400.dp)
                .padding(20.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            // Select a hash function
            Text(
                stringResource(R.string.AlgoList),
            )
            ExposedDropdownMenuBox(
                expanded = expanded,
                onExpandedChange = {
                    expanded = !expanded
                },
            ) {
                OutlinedTextField(
                    readOnly = true,
                    lineLimits = TextFieldLineLimits.SingleLine,
                    modifier = Modifier
                        .fillMaxWidth()
                        .focusRequester(focusRequester)
                        .onFocusChanged { focusState ->
                            if (focusState.isFocused)
                                expanded = !expanded
                        },
                    state = hashFunction,
                    label = { Text("Hash Function") },
                    trailingIcon = {
                        ExposedDropdownMenuDefaults
                            .TrailingIcon(expanded = expanded)
                    },
                )
                ExposedDropdownMenu(
                    expanded = expanded,
                    onDismissRequest = {
                        expanded = false
                        focusManager.clearFocus()
                   },
                ) {
                    options.forEach { selectionOption ->
                        DropdownMenuItem(
                            text = { Text(text = selectionOption) },
                            onClick = {
                                hashFunction
                                    .setTextAndPlaceCursorAtEnd(
                                        selectionOption
                                    )
                                expanded = false
                                focusManager.clearFocus()
                            },
                        )
                    }
                }
            }


            // Input the text to hash
            Text(
                stringResource(R.string.input_txt)
            )
            OutlinedTextField(
                state = inputHash,
                modifier =  Modifier.fillMaxWidth(),
                label = { Text("Input hash") },
            )
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Checkbox(
                    checked = checked,
                    onCheckedChange = { checked = it }
                )
                Text(
                    stringResource(R.string.cb_upper_case)
                )
            }

            // Calculate and clear buttons
            Row(
                modifier =  Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.End
            ) {
                FilledTonalButton(onClick = {
                    calcHash(hashFunction, inputHash)
                    calculated = false
                    calculated = true
                }) {
                    Text(text = stringResource(R.string.calculate_but))
                }
                Spacer(
                    modifier = Modifier.width(20.dp)
                )
                FilledTonalButton(onClick = {
                    inputHash.clearText()
                }) {
                    Text(text = stringResource(R.string.clear_but))
                }
            }

            // Answer and copy hash button
            if (calculated) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.Start
                ) {
                    Text(
                        text = ("Text: " + inputHash.text)
                    )
                }
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.Start
                ) {
                    Text(
                        if (checked) {
                            "${hashFunction.text} checksum: ${answer.uppercase()}"
                        } else {
                            "${hashFunction.text} checksum: ${answer.lowercase()}"
                        }
                    )
                }
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.End
                ) {
                    FilledTonalButton(
                        modifier = Modifier.fillMaxWidth(),
                        onClick = {
                            if (checked) {
                                manager?.setPrimaryClip(
                                    ClipData.newPlainText(
                                        "Plain Text", answer.uppercase()
                                    )
                                )
                            } else {
                                manager?.setPrimaryClip(
                                    ClipData.newPlainText(
                                        "Plain Text", answer.lowercase()
                                    )
                                )
                            }
                            toast.show()
                        }
                    ) {
                        Text(text = stringResource(R.string.copy_but))
                    }
                }
            }
        }
    }
}

private fun calcHash(hashFunction: TextFieldState, inputHash: TextFieldState) {
    msToHash = inputHash.text.toString()
    val setAlgo = hashFunction.text.toString()
    Log.d("setAlgo:", setAlgo)
    hashOpe.SetAlgorithm(setAlgo)
    Log.d("hashOpe:", hashOpe.SetAlgorithm(setAlgo).toString())

    answer = hashOpe.StringToHash(msToHash)
    Log.d("answer:", hashOpe.StringToHash(msToHash))
}