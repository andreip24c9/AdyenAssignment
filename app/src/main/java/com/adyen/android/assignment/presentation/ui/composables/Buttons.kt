package com.adyen.android.assignment.presentation.ui.composables

import android.content.res.Configuration
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.adyen.android.assignment.R
import com.adyen.android.assignment.presentation.theme.AdyenTheme

@Preview(showBackground = true, name = "Button Light Mode")
@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES, showBackground = true, name = "Button Dark Mode")
@Composable
fun SortingButtonPreview() {
    return ExtendableFloatingActionButton(
        extended = true,
        text = {
            Text(
                text = stringResource(id = R.string.reorder_list_label),
                color = MaterialTheme.colorScheme.onSecondary
            )
        },
        icon = {
            Image(
                painter = painterResource(id = R.drawable.ic_reorder),
                contentDescription = null
            )
        }
    )
}


@Composable
fun ExtendableFloatingActionButton(
    modifier: Modifier = Modifier,
    extended: Boolean,
    text: @Composable () -> Unit,
    icon: @Composable () -> Unit,
    onClick: () -> Unit = {}
) {
    AdyenTheme {
        FloatingActionButton(
            modifier = modifier,
            onClick = onClick,
            containerColor = MaterialTheme.colorScheme.secondary,
            contentColor = MaterialTheme.colorScheme.onSecondary
        ) {
            Row(
                modifier = Modifier.padding(start = 16.dp, end = 16.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                icon()

                AnimatedVisibility(visible = extended) {
                    Row {
                        Spacer(Modifier.width(12.dp))
                        text()
                    }
                }
            }
        }
    }
}