package com.example.androiddevchallenge

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.unit.dp
import kotlin.time.ExperimentalTime

@ExperimentalTime
@Composable
fun TeaCup(
  color: Color
) {
  Canvas(
    modifier = Modifier.size(350.dp),
  ) {
    drawArc(
      color = Color.Gray,
      startAngle = 0f,
      sweepAngle = 180f,
      useCenter = false,
    )
    drawOval(
      color = Color.Gray,
      size = Size(this.size.width, this.size.height / 2.5f),
      topLeft = Offset(0f, this.size.height / 3.5f),
      style = Stroke(width = 50f)
    )

    drawOval(
      color = color,
      size = Size(this.size.width - 50f, this.size.height / 2.5f - 50f),
      topLeft = Offset(25f, this.size.height / 3.5f + 25f)
    )
    drawArc(
      color = Color.White,
      size = this.size / 7f,
      startAngle = 160f,
      sweepAngle = 190f,
      useCenter = false,
      style = Stroke(width = 10f, cap = StrokeCap.Round),
      topLeft = Offset(-50f, 340f)
    )
  }
}