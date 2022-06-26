package com.adyen.android.assignment.presentation.ui.composables

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.selection.selectable
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun radioGroup(radioOptionsRes: List<Int>, selectedOptionRes: Int? = null): Int {
    if (radioOptionsRes.isNotEmpty()) {
        val (selectedOption, onOptionSelected) = remember {
            mutableStateOf(radioOptionsRes.find { it == selectedOptionRes } ?: radioOptionsRes[0])
        }

        Column(
            Modifier.padding(top = 8.dp, bottom = 8.dp)
        ) {
            radioOptionsRes.forEach { stringRes ->
                Row(
                    Modifier
                        .padding(start = 12.dp, end = 12.dp)
                        .fillMaxWidth()
                        .selectable(selected = false, onClick = { onOptionSelected(stringRes) }),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically,
                ) {
                    Text(
                        text = LocalContext.current.getString(stringRes),
                        style = TextStyle (
                            color = MaterialTheme.colorScheme.onBackground,
                            fontSize = 16.sp,
                            fontWeight = FontWeight.Normal
                                )
                    )

                    RadioButton(
                        colors = DefaultRadioButtonColors(
                            selectedColor = MaterialTheme.colorScheme.secondary,
                            unselectedColor = MaterialTheme.colorScheme.onBackground,
                            disabledColor = MaterialTheme.colorScheme.onBackground
                        ),
                        selected = (stringRes == selectedOption),
                        onClick = { onOptionSelected(stringRes) }
                    )
                }
            }
        }
        return selectedOption
    } else {
        return -1
    }
}

private class DefaultRadioButtonColors(
    private val selectedColor: Color,
    private val unselectedColor: Color,
    private val disabledColor: Color
) : RadioButtonColors {
    @Composable
    override fun radioColor(enabled: Boolean, selected: Boolean): State<Color> {
        val target = when {
            !enabled -> disabledColor
            !selected -> unselectedColor
            else -> selectedColor
        }

        // If not enabled 'snap' to the disabled state, as there should be no animations between
        // enabled / disabled.
        return if (enabled) {
            animateColorAsState(target, tween(durationMillis = 100))
        } else {
            rememberUpdatedState(target)
        }
    }
}