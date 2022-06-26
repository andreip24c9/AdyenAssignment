package com.adyen.android.assignment.presentation.ui.composables

import android.content.res.Configuration
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.adyen.android.assignment.R
import com.adyen.android.assignment.presentation.theme.AdyenTheme

@Composable
fun ReorderDialog(
    selectedOptionRes: Int,
    optionsRes: List<Int>,
    onDismiss: () -> Unit,
    onApply: (optionRes: Int) -> Unit
) {
    val selectedItem: MutableState<Int> =
        remember { mutableStateOf(optionsRes.find { it == selectedOptionRes } ?: optionsRes[0]) }

    return AlertDialog(
        onDismissRequest = { onDismiss() },
        confirmButton = {
            Button(
                onClick = {
                    onApply(selectedItem.value)
                    onDismiss()
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 16.dp, end = 16.dp),
                shape = RoundedCornerShape(6.dp),
                colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.secondary)
            ) {
                Text(
                    text = stringResource(id = R.string.order_alert_positive_btn),
                    color = MaterialTheme.colorScheme.onSecondary
                )
            }
        },
        dismissButton = {
            Button(
                onClick = { onDismiss() },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 16.dp, end = 16.dp),
                shape = RoundedCornerShape(6.dp),
                colors = ButtonDefaults.buttonColors(containerColor = Color.Transparent)
            ) {
                Text(
                    text = stringResource(id = R.string.order_alert_negative_btn),
                    color = MaterialTheme.colorScheme.onBackground
                )
            }
        },
        icon = {},
        title = {
            Text(
                text = stringResource(id = R.string.order_alert_title),
                color = MaterialTheme.colorScheme.onBackground,
                style = TextStyle(
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold
                )
            )
        },
        text = {
            selectedItem.value = radioGroup(
                radioOptionsRes = optionsRes,
                selectedOptionRes = selectedItem.value
            )
        },
        shape = MaterialTheme.shapes.medium
    )
}

@Preview(showBackground = true, name = "Reorder Alert Light Mode")
@Preview(
    uiMode = Configuration.UI_MODE_NIGHT_YES, showBackground = true,
    name = "Reorder Alert Dark Mode"
)

@Composable
fun ReorderAlertPreview() {
    return AdyenTheme {
        ReorderDialog(
            selectedOptionRes = R.string.order_by_title_label,
            optionsRes = mutableListOf(R.string.order_by_title_label, R.string.order_by_date_label),
            onDismiss = { },
            onApply = { optionRes -> })
    }
}