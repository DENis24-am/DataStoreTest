package com.example.datasharedtest

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.datasharedtest.datastore.DataStoreManager
import com.example.datasharedtest.datastore.Settings
import com.example.datasharedtest.ui.theme.DataSharedTestTheme
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val dataStoreManager = DataStoreManager(this)

        setContent {
            var text by remember {
                mutableStateOf("")
            }

            val coroutine = rememberCoroutineScope()

            LaunchedEffect(true) {
                dataStoreManager.getSetts().collect {
                    text = it.name
                }
            }

            DataSharedTestTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    DataSave(
                        text = text,
                        change = {
                            text = it
                        },
                        save = {
                            coroutine.launch {
                                dataStoreManager.saveSetts(
                                    Settings(text)
                                )
                            }
                        }
                    )
                }
            }
        }
    }
}

@Composable
fun DataSave(
    text: String,
    change: (String) -> Unit = {},
    save: () -> Unit = {}
) {
    Surface(
        color = MaterialTheme.colorScheme.background
    ) {
        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            OutlinedTextField(
                value = text,
                onValueChange = change,
            )

            Button(
                onClick = save
            ) {
                Text(text = "Save")
            }
        }
    }
}