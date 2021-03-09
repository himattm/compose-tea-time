package com.example.androiddevchallenge.data

import androidx.compose.ui.graphics.Color
import kotlin.time.Duration
import kotlin.time.ExperimentalTime

@ExperimentalTime
data class Tea(
  val type: String,
  val color: Color,
  val brewTime: ClosedRange<Duration>,
  val brewTemp: IntRange
)