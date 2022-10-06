// Copyright 2000-2021 JetBrains s.r.o. and contributors. Use of this source code is governed by the Apache 2.0 license that can be found in the LICENSE file.
import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Switch
import androidx.compose.material.SwitchDefaults
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import kotlin.random.Random

@Composable
@Preview
fun App() {
    val checkedWorkState = remember { mutableStateOf(true) }
    val checkedHookahState = remember { mutableStateOf(false) }
    val checkedDreamState = remember { mutableStateOf(false) }

    MaterialTheme {
        Column(modifier = Modifier.padding(16.dp)) {
            Swithcer("Work", checkedWorkState.value, onCheckedChange = {
                checkedWorkState.value = it
                validateOtherSwitcher(checkedWorkState, checkedHookahState, checkedDreamState)
            })
            Swithcer("Social life", checkedHookahState.value, onCheckedChange = {
                checkedHookahState.value = it
                validateOtherSwitcher(checkedHookahState, checkedWorkState, checkedDreamState)
            })

            Swithcer("Sleep", checkedDreamState.value, onCheckedChange = {
                checkedDreamState.value = it
                validateOtherSwitcher(checkedDreamState, checkedWorkState, checkedHookahState)
            })
        }
    }
}

fun validateOtherSwitcher(
    notChange: MutableState<Boolean>,
    toChange1: MutableState<Boolean>,
    toChange2: MutableState<Boolean>
) {
    if (notChange.value && toChange1.value && toChange2.value) {
        val randomValue = List(1) { Random.nextInt(0, 2) }
        if (randomValue[0] == 0) {
            toChange1.value = false
        } else {
            toChange2.value = false
        }
    } else {
        println("need to all 3 checked")
    }
}

@Composable
private fun Swithcer(text: String, isChecked: Boolean, onCheckedChange: (Boolean) -> Unit) {
    Row(verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier.clickable {
            onCheckedChange(!isChecked)
        }) {
        Switch(
            checked = isChecked,
            onCheckedChange = onCheckedChange,
            colors = SwitchDefaults.colors(checkedThumbColor = Color(0xFFc8cdfc)),
            modifier = Modifier.alpha(if (isChecked) 1f else 0.3f)
        )
        Text(
            text = text, modifier = Modifier.padding(start = 8.dp)
                .alpha(if (isChecked) 1f else 0.3f)
        )
    }
}

fun main() = application {
    Window(onCloseRequest = ::exitApplication, title = "Long story short") {
        App()
    }
}
