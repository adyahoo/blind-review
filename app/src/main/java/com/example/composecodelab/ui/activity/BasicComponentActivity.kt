package com.example.composecodelab.ui.activity

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.spring
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ExpandLess
import androidx.compose.material.icons.filled.ExpandMore
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.datasource.LoremIpsum
import androidx.compose.ui.unit.dp
import com.example.composecodelab.ui.theme.ComposeCodeLabTheme
import androidx.compose.material3.*
import com.example.composecodelab.R

class BasicComponentActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ComposeCodeLabTheme {
                MyApp(Modifier.fillMaxSize())
            }
        }
    }
}

@Composable
private fun MyApp(modifier: Modifier = Modifier) {
    // state hoisting, menaruh state di component parent padahal diupdate sama childnya
    // remember untuk mempertahankan nilai state lama agar tidak reset ketika mount component baru
    // rememberSaveable sama kyk remember, tapi valuenya gak akan reset kalo activity restart
    var isShowOnboarding by rememberSaveable { mutableStateOf(true) }

    // A surface container using the 'background' color from the theme
    Surface(
        modifier = modifier,
        color = MaterialTheme.colorScheme.background
    ) {
//        Conversation(data = SampleData.conversationSample)

        if (isShowOnboarding) {
            // parameter fungsi sbg callback onclick event yg ada di child
            OnBoardingScreen(onClick = { isShowOnboarding = false })
        } else {
            Greetings()
        }
    }
}

@Composable
fun Greetings(
    modifier: Modifier = Modifier,
    names: Array<String> = Array(100) { "$it" }
) {
    // list yg cuma render item keliatan di layar, mirip kyk recyclerview tpi gak recycle item
    LazyColumn(modifier = modifier.padding(vertical = 4.dp)) {
        items(names) {
            Greeting(name = it)
        }
    }
}

@Composable
fun OnBoardingScreen(
    modifier: Modifier = Modifier,
    onClick: () -> Unit
) {
    Column(
        modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text("Welcome")
        Button(onClick, modifier = Modifier.padding(vertical = 24.dp)) {
            Text("Continue")
        }
    }
}

@Composable
fun Greeting(name: String) {
    Card(
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.secondary),
        modifier = Modifier.padding(vertical = 4.dp, horizontal = 8.dp)
    ) {
        GreetingContent(name)
    }
}

@Composable
fun GreetingContent(name: String) {
    var isExpanded by remember {
        mutableStateOf(false)
    }
    // component animasi dimana akan ada animasi pergerakan sampai mencapai target value
    val extraPadding by animateDpAsState(
        targetValue = if (isExpanded) 48.dp else 0.dp,
        animationSpec = spring(
            dampingRatio = Spring.DampingRatioMediumBouncy,
            stiffness = Spring.StiffnessLow
        )
    )

    Row(
        Modifier
            .padding(24.dp)
            .animateContentSize(
                animationSpec = spring(
                    Spring.DampingRatioMediumBouncy,
                    Spring.StiffnessLow
                )
            )
    ) {
        Column(
            Modifier
                .weight(1f)
                .padding(12.dp)
        ) {
            Text(text = "Hello, ")
            Text(text = name, style = MaterialTheme.typography.headlineSmall)
            if (isExpanded) {
                Text(text = LoremIpsum(25).values.toString())
            }
        }
        IconButton(onClick = {
            isExpanded = !isExpanded
        }) {
            Icon(
                imageVector = if (isExpanded) Icons.Filled.ExpandLess else Icons.Filled.ExpandMore,
                contentDescription = if (isExpanded) stringResource(id = R.string.show_less) else stringResource(
                    id = R.string.show_more
                )
            )
        }
    }
}

@Preview(showBackground = true, device = Devices.PIXEL_4)
@Composable
fun DefaultPreview() {
    ComposeCodeLabTheme {
        MyApp(Modifier.fillMaxSize())
    }
}