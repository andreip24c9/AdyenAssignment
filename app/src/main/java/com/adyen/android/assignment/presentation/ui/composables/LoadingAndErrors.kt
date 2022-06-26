package com.adyen.android.assignment.presentation.ui.composables

import android.content.res.Configuration
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.adyen.android.assignment.R
import com.adyen.android.assignment.presentation.theme.AdyenTheme

class ErrorMessage(
    val errorImageRes: Int? = null,
    val titleRes: Int? = null,
    val bodyRes: Int? = null
)

class RetryButton(val textRes: Int, val onClick: () -> Unit)

@Composable
fun LoadingErrorView(
    modifier: Modifier = Modifier,
    isLoading: Boolean = false,
    error: ErrorMessage? = null,
    retryButton: RetryButton? = null
) {
    AdyenTheme {
        Surface(modifier) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .fillMaxHeight(),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                if (isLoading) {
                    CircularProgressIndicator(color = MaterialTheme.colorScheme.secondary)
                } else {
                    error?.apply {
                        errorImageRes?.let {
                            Image(
                                painter = painterResource(id = it),
                                contentDescription = null,
                                contentScale = ContentScale.Crop,
                                modifier = Modifier
                                    .width(145.dp)
                                    .height(145.dp)
                            )
                            Spacer(modifier = Modifier.padding(top = 108.dp))
                        }

                        titleRes?.let {
                            Text(
                                modifier = Modifier
                                    .padding(start = 32.dp, end = 32.dp),
                                textAlign = TextAlign.Center,
                                text = stringResource(id = it),
                                color = MaterialTheme.colorScheme.onBackground,
                                style = TextStyle(fontSize = 22.sp, fontWeight = FontWeight.Bold)
                            )
                        }
                        if (titleRes != null && bodyRes != null) {
                            Spacer(modifier = Modifier.padding(top = 8.dp))
                        }
                        bodyRes?.let {
                            Text(
                                modifier = Modifier.padding(start = 32.dp, end = 32.dp),
                                text = stringResource(id = it),
                                color = MaterialTheme.colorScheme.onBackground,
                                style = TextStyle(fontSize = 16.sp, fontWeight = FontWeight.Normal),
                                textAlign = TextAlign.Center
                            )
                        }
                        retryButton?.apply {
                            Spacer(modifier = Modifier.padding(top = 48.dp))
                            Button(
                                onClick = onClick,
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(start = 42.dp, end = 42.dp),
                                shape = RoundedCornerShape(6.dp),
                                colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.secondary)
                            ) {
                                Text(
                                    text = stringResource(id = textRes),
                                    color = MaterialTheme.colorScheme.onSecondary
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}


@Preview(showBackground = true, name = "Header Light Mode")
@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES, showBackground = true, name = "Header Dark Mode")
@Composable
fun LoadingErrorViewPreview() {
    return LoadingErrorView(
        modifier = Modifier
            .fillMaxHeight()
            .fillMaxWidth(),
        isLoading = false,
        error = ErrorMessage(
            R.drawable.ic_error,
            R.string.unknown_error_title,
            R.string.unkown_error_body
        ),
        retryButton = RetryButton(R.string.retry) {}
    )
}