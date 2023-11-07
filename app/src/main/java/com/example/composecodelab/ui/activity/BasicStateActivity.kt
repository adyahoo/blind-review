package com.example.composecodelab.ui.activity

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.Button
import androidx.compose.material3.Checkbox
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.composecodelab.data.TaskItemModel
import com.example.composecodelab.data.getTasks
import com.example.composecodelab.ui.theme.ComposeCodeLabTheme

class BasicStateActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ComposeCodeLabTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    BasicStateScreen(Modifier.fillMaxSize())
                }
            }
        }
    }
}

@Composable
fun BasicStateScreen(modifier: Modifier = Modifier) {
    Column(modifier) {
        StatefulCounter()
        TaskList()
    }
}

@Composable
fun WaterCounter(modifier: Modifier = Modifier) {
    Column(modifier = modifier.padding(16.dp)) {
        var count by rememberSaveable {
            mutableStateOf(0)
        }
        if (count > 0) {
            var showTask by remember {
                mutableStateOf(true)
            }
            if (showTask) {
//                TaskItem(name = "Go get some walk!", onClose = { showTask = false })
            }

            Text("You've had $count glassess")
        }

        Row(Modifier.padding(top = 8.dp)) {
            Button(onClick = { count++ }, enabled = count < 10) {
                Text("Add")
            }
            Button(
                onClick = {
                    count = 0
                },
                modifier = Modifier.padding(start = 8.dp)
            ) {
                Text("Reset Counter")
            }
        }
    }
}

@Composable
fun TaskItem(
    name: String,
    isChecked: Boolean,
    onChecked: (Boolean) -> Unit,
    onClose: () -> Unit,
    modifier: Modifier = Modifier
) {
    Row(verticalAlignment = Alignment.CenterVertically, modifier = modifier) {
        Text(
            name,
            modifier = Modifier
                .weight(1f)
                .padding(start = 16.dp)
        )
        Checkbox(
            checked = isChecked,
            onCheckedChange = onChecked,
            modifier = Modifier.padding(end = 8.dp)
        )
        IconButton(onClick = onClose) {
            Icon(Icons.Filled.Close, contentDescription = "Close")
        }
    }
}

@Composable
fun TaskItem(
    name: String,
    modifier: Modifier = Modifier
) {
    var checkedState by remember { mutableStateOf(false) }

    TaskItem(
        name = name,
        isChecked = checkedState,
        onChecked = { newValue -> checkedState = newValue },
        onClose = {},
        modifier
    )
}

@Composable
fun TaskList(
    modifier: Modifier = Modifier,
    list: List<TaskItemModel> = remember { getTasks() },
) {
    LazyColumn(modifier) {
        items(list) {
            TaskItem(name = it.label)
        }
    }
}

@Composable
fun StatelessCounter(
    count: Int,
    onIncrement: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(modifier.padding(16.dp)) {
        if (count > 0) {
            Text("You've had $count glassess")
        }

        Button(
            onClick = onIncrement,
            enabled = count < 10,
            modifier = Modifier.padding(top = 8.dp)
        ) {
            Text("Add")
        }
    }
}

@Composable
fun StatefulCounter(modifier: Modifier = Modifier) {
    var count by remember { mutableIntStateOf(0) }
    StatelessCounter(count = count, onIncrement = { count++ }, modifier)
}

@Preview(showBackground = true, device = Devices.PIXEL_4)
@Composable
fun Preview() {
    ComposeCodeLabTheme {
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = MaterialTheme.colorScheme.background
        ) {
            BasicStateScreen(Modifier.fillMaxSize())
        }
    }
}