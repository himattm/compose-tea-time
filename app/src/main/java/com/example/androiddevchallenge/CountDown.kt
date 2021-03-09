package com.example.androiddevchallenge

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlin.math.roundToInt
import kotlin.time.Duration
import kotlin.time.ExperimentalTime
import kotlin.time.minutes
import kotlin.time.seconds

@ExperimentalTime
class CountDown(
  val duration: Duration = 1.minutes,
  private val scope: CoroutineScope,
  private val delay: Duration = 1.seconds
) {
  private var job: Job? = null

  var isCountingDown by mutableStateOf(false)
  var state by mutableStateOf(prettyPrint(duration))

  fun toggle() {
    if (isCountingDown) {
      stop()
    } else {
      start()
    }
  }

  private fun start() {
    Log.d("mcm", "start()")
    if (job == null) {
      job = scope.launch {
        isCountingDown = true
        var secondsLeft = duration.inSeconds.roundToInt()
        while (secondsLeft >= 0) {
          state = prettyPrint(secondsLeft.seconds)
          delay(delay)
          secondsLeft -= 1
        }
        isCountingDown = false
        stop()
      }
    }
  }

  private fun prettyPrint(time: Duration): String {
    var r: String
    time.toComponents { minutes, seconds, _ ->

      r = "${padZero(minutes)}:${padZero(seconds)}"
    }
    Log.d("mcm", " send pretty $r")
    return r
  }

  private fun padZero(time: Int): String {
    return if (time < 10) {
      "0$time"
    } else {
      "$time"
    }
  }

  fun stop() {
    Log.d("mcm", "stop()")
    job?.cancel()
    job = null
    isCountingDown = false
  }
}