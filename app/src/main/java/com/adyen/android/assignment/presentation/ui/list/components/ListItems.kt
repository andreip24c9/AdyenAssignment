package com.adyen.android.assignment.presentation.ui.list.components

import android.content.res.Configuration
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.selection.selectable
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.adyen.android.assignment.R
import com.adyen.android.assignment.presentation.theme.AdyenTheme

@Composable
fun ApodComposable(
    modifier: Modifier = Modifier,
    imageUrl: String?, title: String, subtitle: String,
    onClick: () -> Unit
) {
    AdyenTheme {
        Surface(modifier.selectable(selected = true, onClick = onClick)) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 32.dp, end = 16.dp, bottom = 12.dp, top = 12.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                AsyncImage(
                    modifier = Modifier
                        .clip(CircleShape)
                        .height(38.dp)
                        .width(38.dp),
                    model = ImageRequest.Builder(LocalContext.current)
                        .data(imageUrl)
                        .crossfade(true)
                        .build(),
                    contentDescription = null,
                    contentScale = ContentScale.Crop
                )

                Column(modifier = Modifier.padding(start = 16.dp, end = 16.dp)) {
                    Text(
                        text = title,
                        modifier = Modifier.fillMaxWidth(),
                        style = TextStyle(fontSize = 16.sp, fontWeight = FontWeight.Medium)
                    )
                    Text(
                        text = subtitle,
                        modifier = Modifier
                            .padding(top = 4.dp)
                            .fillMaxWidth(),
                        style = TextStyle(fontSize = 15.sp)
                    )
                }
            }
        }
    }
}

@Composable
fun HeaderComposable(titleRes: Int) {
    AdyenTheme {
        Surface {
            Text(
                text = LocalContext.current.getString(titleRes),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 16.dp, end = 16.dp, top = 8.dp, bottom = 8.dp),
                style = TextStyle(
                    fontSize = 16.sp,
                    color = MaterialTheme.colorScheme.onBackground,
                    fontWeight = FontWeight.Medium
                )
            )
        }
    }
}


@Preview(showBackground = true, name = "Apod Cell Light Mode")
@Preview(
    uiMode = Configuration.UI_MODE_NIGHT_YES,
    showBackground = true,
    name = "Apod Cell Dark Mode"
)
@Composable
fun ApodPreview() {
    return ApodComposable(imageUrl = "", title = "The Milky way", subtitle = "25/06/2022") {}
}

@Preview(showBackground = true, name = "Header Cell Light Mode")
@Preview(
    uiMode = Configuration.UI_MODE_NIGHT_YES,
    showBackground = true,
    name = "Header Cell Dark Mode"
)
@Composable
fun HeaderPreview() {
    return HeaderComposable(titleRes = R.string.latest_header_label)
}