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
import androidx.compose.runtime.toMutableStateList
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.composecodelab.data.TaskItemModel
import com.example.composecodelab.data.getTasks
import com.example.composecodelab.ui.theme.ComposeCodeLabTheme
import com.example.composecodelab.ui.viewmodel.BasicStateViewModel

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
fun BasicStateScreen(
    modifier: Modifier = Modifier,
    stateViewModel: BasicStateViewModel = viewModel()
) {
    Column(modifier) {
        StatefulCounter()
        TaskList(
            list = stateViewModel.tasks,
            onClose = { task -> stateViewModel.remove(task) },
            onChecked = { task, checked -> stateViewModel.updateCheckedValue(task, checked) }
        )
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
    modifier: Modifier = Modifier,
    name: String,
    checked: Boolean,
    onClose: () -> Unit,
    onChecked: (Boolean) -> Unit
) {
    TaskItem(
        name = name,
        isChecked = checked,
        onChecked = { newValue -> onChecked(newValue) },
        onClose = onClose,
        modifier
    )
}

@Composable
fun TaskList(
    modifier: Modifier = Modifier,
    list: List<TaskItemModel> = remember { getTasks() },
    onClose: (TaskItemModel) -> Unit,
    onChecked: (TaskItemModel, Boolean) -> Unit
) {
    LazyColumn(modifier) {
        items(
            items = list,
            key = { task -> task.id }
        ) {
            TaskItem(
                name = it.label,
                checked = it.checked,
                onClose = { onClose(it) },
                onChecked = { newValue -> onChecked(it, newValue) })
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