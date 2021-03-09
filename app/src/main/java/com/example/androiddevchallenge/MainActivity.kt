/*
 * Copyright 2021 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.example.androiddevchallenge

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.animation.Animatable
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.filled.Stop
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.androiddevchallenge.data.Tea
import com.example.androiddevchallenge.data.TeaProvider
import com.example.androiddevchallenge.ui.theme.MyTheme
import com.example.androiddevchallenge.ui.theme.offWhite
import com.example.androiddevchallenge.ui.theme.typography
import kotlinx.coroutines.launch
import kotlin.time.DurationUnit
import kotlin.time.ExperimentalTime
import kotlin.time.milliseconds
import kotlin.time.minutes

@ExperimentalTime
class MainActivity : AppCompatActivity() {
  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContent {
      MyTheme {
        MyApp()
      }
    }
  }
}

// Start building your app here!
@ExperimentalTime
@Composable
fun MyApp() {
  val quickTime = 1

  val scope = rememberCoroutineScope()
  var countDown by remember {
    mutableStateOf(
      CountDown(
        duration = 2.minutes,
        scope = scope
      )
    )
  }

  var currentTea by remember { mutableStateOf(TeaProvider.teas.first()) }

  val buttonColor by animateColorAsState(
    targetValue = currentTea.color,
    animationSpec = tween(
      durationMillis = 500
    )
  )

  val animColor = remember { Animatable(offWhite) }

  Surface(color = MaterialTheme.colors.background) {
    Column(
      horizontalAlignment = Alignment.CenterHorizontally,
      verticalArrangement = Arrangement.SpaceEvenly,
      modifier = Modifier.fillMaxWidth()
    ) {
      Text(
        text = countDown.state,
        style = typography.h2
      )

      Box(
        modifier = Modifier
          .size(300.dp)
          .padding(16.dp)
      ) {
        TeaCup(animColor.value)
      }

      IconButton(
        modifier = Modifier
          .background(
            color = buttonColor,
            shape = CircleShape
          ),
        onClick = {
          countDown.toggle()
          scope.launch {
            animColor.animateTo(
              targetValue = currentTea.color,
              animationSpec = tween(
                durationMillis = currentTea.brewTime.start.toInt(DurationUnit.MILLISECONDS) / quickTime,
                easing = LinearEasing
              )
            )
          }
        }
      ) {
        Icon(
          imageVector = if (countDown.isCountingDown) Icons.Default.Stop else Icons.Default.PlayArrow,
          contentDescription = if (countDown.isCountingDown) "Pause" else "Start"
        )
      }

      TeaSelector(onSelectTea = { tea ->
        scope.launch {
          animColor.animateTo(
            targetValue = offWhite,
            animationSpec = tween(
              durationMillis = 300,
              easing = LinearEasing
            )
          )
        }
        currentTea = tea
        countDown.stop()
        countDown = CountDown(tea.brewTime.start, scope, delay = (1000 / quickTime).milliseconds)
      })
    }
  }
}

@ExperimentalTime
@Composable
fun TeaSelector(
  onSelectTea: (Tea) -> Unit
) {
  Row(
    modifier = Modifier
      .padding(16.dp)
      .fillMaxWidth(),
    verticalAlignment = Alignment.CenterVertically,
    horizontalArrangement = Arrangement.SpaceBetween
  ) {
    TeaProvider.teas.take(3).forEach { tea ->
      TeaButton(tea) { onSelectTea(tea) }
    }
  }

  Row(
    modifier = Modifier
      .padding(16.dp)
      .fillMaxWidth(),
    verticalAlignment = Alignment.CenterVertically,
    horizontalArrangement = Arrangement.SpaceEvenly
  ) {
    TeaProvider.teas.takeLast(2).forEach { tea ->
      TeaButton(tea) { onSelectTea(tea) }
    }
  }
}

@ExperimentalTime
@Composable
fun TeaButton(
  tea: Tea,
  selectTea: (Tea) -> Unit
) {
  Column(
    horizontalAlignment = Alignment.CenterHorizontally,
    verticalArrangement = Arrangement.Center,
  ) {
    IconButton(
      modifier = Modifier
        .size(80.dp)
        .background(
          color = Color.DarkGray,
          shape = RoundedCornerShape(25.dp)
        ),
      onClick = { selectTea(tea) }
    ) {
      Icon(
        modifier = Modifier
          .size(65.dp)
          .padding(4.dp),
        painter = painterResource(id = R.drawable.teabag),
        contentDescription = null,
        tint = tea.color
      )
    }
    Text(text = tea.type)
  }
}

@ExperimentalTime
@Preview("Light Theme", widthDp = 360, heightDp = 640)
@Composable
fun LightPreview() {
  MyTheme {
    MyApp()
  }
}

@ExperimentalTime
@Preview("Dark Theme", widthDp = 360, heightDp = 640)
@Composable
fun DarkPreview() {
  MyTheme(darkTheme = true) {
    MyApp()
  }
}
