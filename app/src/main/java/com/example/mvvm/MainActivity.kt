package com.example.mvvm

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.ColorScheme
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.ViewModel
import com.example.mvvm.ui.theme.MvvmTheme
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MvvmTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    Greeting()
                }
            }
        }
    }
}

class NumberViewModel: ViewModel() {
    private var _number = MutableStateFlow(0);
    public val number: StateFlow<Int> = _number.asStateFlow()

    fun increment() {
        _number.update { n -> n + 1 }
    }
}

@OptIn(ExperimentalMaterial3Api::class, ExperimentalComposeUiApi::class)
@Composable
fun Greeting(modifier: Modifier = Modifier) {
    var messages = remember { mutableStateListOf("") }
    var message by remember { mutableStateOf(""); }
    val focusManager = LocalFocusManager.current

    var onSendPressed = {
        messages.add(message)
        message = ""
        focusManager.clearFocus();
    }

    var onTextInput = { input: String ->
        message = input
    }


    // App
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(8.dp)
            .border(4.dp, MaterialTheme.colorScheme.primaryContainer)) {
        Text(text = messages.size.toString())
        // Messages
        LazyColumn(
            modifier = modifier
                .fillMaxWidth()
                .weight(0.8f, fill = true),
            userScrollEnabled = true) {
                    items(messages) { message ->
                        Text(text = message)
            }
        }

        // Input
        Row(
            modifier = modifier
                .fillMaxWidth()
                .weight(0.2f, fill = true)) {

            // Text Field Input
            TextField(
                modifier = modifier
                    .weight(0.8f),
                value = message,
                onValueChange = onTextInput)

            // Send Button
            Button(
                modifier = modifier
                    .weight(0.2f, fill = true),
                onClick = onSendPressed) {
                Text(modifier = modifier.fillMaxSize(),
                    text = "Send")
            }
        }
    }

}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    MvvmTheme {
        Greeting()
    }
}