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
package com.example.androiddevchallenge.data

import androidx.compose.ui.graphics.Color
import com.example.androiddevchallenge.ui.theme.greenTea
import com.example.androiddevchallenge.ui.theme.herbal
import com.example.androiddevchallenge.ui.theme.offWhite
import com.example.androiddevchallenge.ui.theme.oolong
import kotlin.time.ExperimentalTime
import kotlin.time.minutes

@ExperimentalTime
object TeaProvider {
    val teas = listOf<Tea>(
        Tea(
            type = "Black",
            color = Color.Black,
            brewTime = 3.minutes..5.minutes,
            brewTemp = 200..212,
        ),
        Tea(
            type = "Green",
            color = greenTea,
            brewTime = 1.minutes..3.minutes,
            brewTemp = 160..180,
        ),
        Tea(
            type = "White",
            color = offWhite,
            brewTime = 2.minutes..5.minutes,
            brewTemp = 160..190
        ),
        Tea(
            type = "Oolong",
            color = oolong,
            brewTime = 2.minutes..6.minutes,
            brewTemp = 185..205,
        ),
        Tea(
            type = "Herbal",
            color = herbal,
            brewTime = 5.minutes..7.minutes,
            brewTemp = 200..212
        ),
    )
}
