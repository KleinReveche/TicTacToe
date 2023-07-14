/*
 * Changes were made to make Segmented Buttons available.
 * TODO: THIS WILL BE REMOVED WHEN SEGMENTED BUTTONS IN MATERIAL3 ARE AVAILABLE
 *
 * Copyright 2023 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.kleinreveche.tictactoe.features.local.ui.components.material3

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.interaction.FocusInteraction
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.PressInteraction
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.selection.selectableGroup
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.CornerBasedShape
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ProvideTextStyle
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Immutable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.ReadOnlyComposable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.layout.Layout
import androidx.compose.ui.layout.layout
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

@OptIn(ExperimentalMaterial3Api::class)
@Preview
@Composable
fun SegmentedButtonPreview() {
    var checkedIndex by remember { mutableStateOf(1) }
    val options = listOf("$", "$$", "$$$")
    SegmentedButtonRow {
        options.forEachIndexed { index, label ->
            Material3SegmentedButton(
                shape = SegmentedButtonDefaults.shape(position = index, count = options.size),
                onCheckedChange = { checkedIndex = index },
                checked = index == checkedIndex
            ) {
                Spacer(Modifier.size(30.dp))
                Text(label)
                Spacer(Modifier.size(30.dp))
            }
        }
    }
}


private const val CheckedZIndexFactor = 5f

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Material3SegmentedButton(
    checked: Boolean,
    onCheckedChange: (Boolean) -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    shape: Shape = RectangleShape,
    colors: SegmentedButtonColors = SegmentedButtonDefaults.colors(),
    border: SegmentedButtonBorder = SegmentedButtonDefaults.Border,
    interactionSource: MutableInteractionSource = remember { MutableInteractionSource() },
    content: @Composable RowScope.() -> Unit,
) {

    var interactionCount: Int by remember { mutableStateOf(0) }
    LaunchedEffect(interactionSource) {
        interactionSource.interactions.collect { interaction ->
            when (interaction) {
                is PressInteraction.Press,
                is FocusInteraction.Focus -> {
                    interactionCount++
                }

                is PressInteraction.Release,
                is FocusInteraction.Unfocus,
                is PressInteraction.Cancel -> {
                    interactionCount--
                }
            }
        }
    }

    val containerColor = colors.containerColor(enabled, checked)
    val contentColor = colors.contentColor(enabled, checked)
    val checkedState by rememberUpdatedState(checked)

    Surface(
        modifier = modifier
            .layout { measurable, constraints ->
                val placeable = measurable.measure(constraints)
                layout(placeable.width, placeable.height) {
                    val zIndex = interactionCount + if (checkedState) CheckedZIndexFactor else 0f
                    placeable.place(0, 0, zIndex)
                }
            }
            .defaultMinSize(
                minWidth = ButtonDefaults.MinWidth,
                minHeight = ButtonDefaults.MinHeight
            ),
        checked = checked,
        onCheckedChange = onCheckedChange,
        enabled = enabled,
        shape = shape,
        color = containerColor,
        contentColor = contentColor,
        border = border.borderStroke(enabled, checked, colors),
        interactionSource = interactionSource
    ) {
        ProvideTextStyle(value = MaterialTheme.typography.labelLarge) {
            Row(
                Modifier.padding(ButtonDefaults.TextButtonContentPadding),
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically,
                content = content
            )
        }
    }
}

@Composable
fun SegmentedButtonRow(
    modifier: Modifier = Modifier,
    space: Dp = 1.dp,
    content: @Composable () -> Unit
) {
    Layout(
        modifier = modifier
            .height(40.dp)
            .width(IntrinsicSize.Max)
            .selectableGroup(),
        content = content
    ) { measurables, constraints ->
        val width = measurables.fold(0) { curr, max ->
            maxOf(curr, max.maxIntrinsicWidth(constraints.maxHeight))
        }
        val placeables = measurables.map {
            it.measure(constraints.copy(minWidth = width))
        }
        layout(placeables.size * width, constraints.maxHeight) {
            val itemCount = placeables.size
            val startOffset = (itemCount - 1) * space.roundToPx() / 2
            placeables.forEachIndexed { index, placeable ->
                placeable.placeRelative(startOffset + index * (width - space.roundToPx()), 0)
            }
        }
    }
}

@ExperimentalMaterial3Api
object SegmentedButtonDefaults {
    @Composable
    fun colors(
        checkedContainerColor: Color = MaterialTheme.colorScheme.primary,
        checkedContentColor: Color = MaterialTheme.colorScheme.onPrimary,
        checkedBorderColor: Color = MaterialTheme.colorScheme.outline,
        uncheckedContainerColor: Color = MaterialTheme.colorScheme.surfaceVariant,
        uncheckedContentColor: Color = MaterialTheme.colorScheme.onSurface,
        uncheckedBorderColor: Color = checkedBorderColor,
        disabledCheckedContainerColor: Color = checkedContainerColor,
        disabledCheckedContentColor: Color = MaterialTheme.colorScheme.onSurface
            .copy(alpha = 0.38f),
        disabledCheckedBorderColor: Color = MaterialTheme.colorScheme.outline
            .copy(alpha = 0.12f),
        disabledUncheckedContainerColor: Color = uncheckedContainerColor,
        disabledUncheckedContentColor: Color = disabledCheckedContentColor,
        disabledUncheckedBorderColor: Color = checkedBorderColor,
    ): SegmentedButtonColors = SegmentedButtonColors(
        checkedContainerColor = checkedContainerColor,
        checkedContentColor = checkedContentColor,
        checkedBorderColor = checkedBorderColor,
        uncheckedContainerColor = uncheckedContainerColor,
        uncheckedContentColor = uncheckedContentColor,
        uncheckedBorderColor = uncheckedBorderColor,
        disabledCheckedContainerColor = disabledCheckedContainerColor,
        disabledCheckedContentColor = disabledCheckedContentColor,
        disabledCheckedBorderColor = disabledCheckedBorderColor,
        disabledUncheckedContainerColor = disabledUncheckedContainerColor,
        disabledUncheckedContentColor = disabledUncheckedContentColor,
        disabledUncheckedBorderColor = disabledUncheckedBorderColor,
    )

    val Border = SegmentedButtonBorder(width = 1.dp)
    private val Shape: CornerBasedShape
        @Composable
        @ReadOnlyComposable
        get() = CircleShape

    @Composable
    @ReadOnlyComposable
    fun shape(position: Int, count: Int, shape: CornerBasedShape = this.Shape): Shape {
        return when (position) {
            0 -> shape.copy(topEnd = CornerSize(0.0.dp), bottomEnd = CornerSize(0.0.dp))
            count - 1 -> shape.copy(topStart = CornerSize(0.0.dp), bottomStart = CornerSize(0.0.dp))
            else -> RectangleShape
        }
    }

    /**
     * Icon size to use for icons inside [SegmentedButtonRow] item
     */
    val IconSize = 18.dp
}

/**
 * Class to create border stroke for segmented button, see [SegmentedButtonColors], for
 * customization of colors.
 */
@ExperimentalMaterial3Api
@Immutable
class SegmentedButtonBorder(val width: Dp) {
    fun borderStroke(
        enabled: Boolean,
        checked: Boolean,
        colors: SegmentedButtonColors
    ): BorderStroke = BorderStroke(
        width = width,
        color = colors.borderColor(enabled, checked)
    )
}

@Immutable
@ExperimentalMaterial3Api
class SegmentedButtonColors(
    // enabled & checked
    val checkedContainerColor: Color,
    val checkedContentColor: Color,
    val checkedBorderColor: Color,
    // enabled & unchecked
    val uncheckedContainerColor: Color,
    val uncheckedContentColor: Color,
    val uncheckedBorderColor: Color,
    // disable & checked
    val disabledCheckedContainerColor: Color,
    val disabledCheckedContentColor: Color,
    val disabledCheckedBorderColor: Color,
    // disable & unchecked
    val disabledUncheckedContainerColor: Color,
    val disabledUncheckedContentColor: Color,
    val disabledUncheckedBorderColor: Color,
) {
    /**
     * Represents the color used for the SegmentedButton's border,
     * depending on [enabled] and [checked].
     *
     * @param enabled whether the [SegmentedButtonRow] is enabled or not
     * @param checked whether the [SegmentedButtonRow] item is checked or not
     */
    internal fun borderColor(enabled: Boolean, checked: Boolean): Color {
        return when {
            enabled && checked -> checkedBorderColor
            enabled && !checked -> uncheckedBorderColor
            !enabled && checked -> disabledCheckedContentColor
            else -> disabledUncheckedContentColor
        }
    }

    internal fun contentColor(enabled: Boolean, checked: Boolean): Color {
        return when {
            enabled && checked -> checkedContentColor
            enabled && !checked -> uncheckedContentColor
            !enabled && checked -> disabledCheckedContentColor
            else -> disabledUncheckedContentColor
        }
    }

    /**
     * Represents the container color passed to the items
     *
     * @param enabled whether the [SegmentedButtonRow] is enabled or not
     * @param checked whether the [SegmentedButtonRow] item is checked or not
     */
    internal fun containerColor(enabled: Boolean, checked: Boolean): Color {
        return when {
            enabled && checked -> checkedContainerColor
            enabled && !checked -> uncheckedContainerColor
            !enabled && checked -> disabledCheckedContainerColor
            else -> disabledUncheckedContainerColor
        }
    }
}