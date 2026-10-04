package com.example.rehashdroid.ui.screens

import android.content.ClipData
import android.content.ClipboardManager
import android.database.Cursor
import android.provider.OpenableColumns
import android.util.Log
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.text.input.TextFieldLineLimits
import androidx.compose.foundation.text.input.TextFieldState
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
import java.io.InputStream


const val PICK_PDF_FILE = 2

private var answer = "testing"
private val hashOpe = HashFunctionOperator()
private var msToHash = ""

private var fileSize: Long = 0L
private var filePath: String = ""
private var fileName: String = ""
private var stream: Any = ""

@OptIn(ExperimentalMaterial3Api::class)
@Composable
@Preview(showBackground = true)
fun HashFile(modifier: Modifier = Modifier) {
    var checked by remember { mutableStateOf(false) }
    val options = stringArrayResource(R.array.Algo_Array)
    val hashFunction = rememberTextFieldState(
        options[5]
    )
    var expanded by remember { mutableStateOf(false) }
    var calculated by remember { mutableStateOf(false) }
    var appSelected by remember { mutableStateOf(false) }
    val focusRequester = remember { FocusRequester() }
    val focusManager = LocalFocusManager.current
    val ctx = LocalContext.current
    val text = "Saved to clipboard!"
    val duration = Toast.LENGTH_SHORT
    val toast = Toast.makeText(ctx, text, duration)
    val manager = ctx.getSystemService(ClipboardManager::class.java)
    val pickFileLauncher = rememberLauncherForActivityResult(
        ActivityResultContracts.GetContent()
    ) { uri ->
        /*if (textUri != null) {
            // Update the state with the Uri
            textUriState = textUri.toString()
            Log.d("TAG", textUriState)
        }*/
        val item = ctx.contentResolver.openInputStream(uri!!)
        val bytes = item?.readBytes()
        //val length: Int = item!!.available()
        //val displayName: String = item.fileName()

        //val cursor: Cursor? = getContentResolver().query(uri, null, null, null, null)
        //val displayName = File(uri).name
        val metaCursor: Cursor? = ctx.contentResolver.query(uri, null, null, null, null)
        metaCursor?.moveToFirst()
        val path = metaCursor?.getString(0)
        val lengthIndex = metaCursor?.getColumnIndex(OpenableColumns.SIZE)
        val displayNameIndex = metaCursor?.getColumnIndex(OpenableColumns.DISPLAY_NAME)
        val length = metaCursor?.getLong(lengthIndex!!)
        val displayName = metaCursor?.getString(displayNameIndex!!)

        Log.d("BYTES: ", bytes.toString())
        //item?.close()

        //val file = File(uri.getPath()!!)
        //val file: DocumentFile? = DocumentFile.fromSingleUri(ctx, uri)
        //Log.d("PATH: ", uri.getPath()!!)
        //Log.d("FILE: ", file.toString())
        //val fileDescriptor: AssetFileDescriptor =
        //    getApplicationContext().getContentResolver().openAssetFileDescriptor(uri, "r")
        //val size = fileDescriptor.getLength()


        //val displayName = file.getName()
        //val displayName = File(uri.path).name
        //val displayName = file.getName()
        //val size = file.length()
        //val size = File(uri.path).size
        //val size = file.length()
        //val mimeType = file.getType()
        //val path = file.getPath()

        //val size = getFileSizeFromUri(contentResolver, uri)

        stream = item!!
        fileName = displayName!!
        fileSize = length!!
        filePath = path!!
        Log.d("STREAM: ", stream.toString())
        Log.d("FILENAME: ", fileName.toString())
        Log.d("FILESIZE: ", fileSize.toString())
        Log.d("FILEPATH: ", filePath.toString())


        //item?.close()
        appSelected = true
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
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
            Text(
                stringResource(R.string.AlgoList),
            )
            // Select a hash function dropdown menu
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
                                    .setTextAndPlaceCursorAtEnd(selectionOption)
                                expanded = false
                                focusManager.clearFocus()
                            },
                        )
                    }
                }
            }

            Spacer(
                modifier = Modifier.height(20.dp)
            )

            // Click here to select the file to hash button
            FilledTonalButton(
                modifier =  Modifier.fillMaxWidth(),
                onClick = {
                    // In your button's click
                    pickFileLauncher.launch("*/*")
                }
            ) {
                if (appSelected) {
                    Text(text = fileName)
                } else {
                    Text(text = stringResource(R.string.but_title_select_file))
                }
            }
            // Checkbox
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
            // Calculate button
            Row(
                modifier =  Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.End
            ) {
                FilledTonalButton(onClick = {
                    calcHash(hashFunction, stream)
                    calculated = false
                    calculated = true
                }) {
                    Text(text = stringResource(R.string.calculate_but))
                }
            }

            // Answer text
            if (calculated) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.Start

                ) {
                    Text(
                        text = ("Name: ${fileName}")
                    )
                }
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.Start
                ) {
                    Text(
                        text = ("Size: ${fileSize}")
                    )
                }
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.Start
                ) {
                    Text(
                        if (checked)
                            "${hashFunction.text} checksum: ${answer.uppercase()}"
                        else
                            "${hashFunction.text} checksum: ${answer.lowercase()}"
                    )
                }
                // Copy button
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

private fun calcHash(hashFunction: TextFieldState, stream: Any) {
    // Set algorithm
    val setAlgo = hashFunction.text.toString()
    Log.d("setAlgo:", setAlgo)
    hashOpe.SetAlgorithm(setAlgo)
    Log.d("hashOpe:", hashOpe.SetAlgorithm(setAlgo).toString())

    // Set input stream
    msToHash = hashOpe.FileToHash(stream as InputStream?)

    answer = msToHash
    Log.d("answer:", answer)
}