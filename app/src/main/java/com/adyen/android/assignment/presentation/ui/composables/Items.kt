package com.adyen.android.assignment.presentation.ui.composables

import android.content.res.Configuration
import androidx.appcompat.content.res.AppCompatResources
import androidx.compose.foundation.Image
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
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalInspectionMode
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.rememberImagePainter
import com.adyen.android.assignment.R
import com.adyen.android.assignment.presentation.theme.AdyenTheme

@Composable
fun ApodComposable(
    imageUrl: String?, title: String, subtitle: String,
    onClick: () -> Unit
) {
    AdyenTheme {
        Surface(Modifier.selectable(selected = true, onClick = onClick)) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 16.dp, end = 16.dp, bottom = 8.dp, top = 8.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Image(
                    painter = rememberImagePainter(
                        data = imageUrl,
                        builder = {
                            val fallback = AppCompatResources.getDrawable(
                                LocalContext.current,
                                R.drawable.ic_cloud_error
                            )
                            fallback?.setTint(MaterialTheme.colorScheme.onSurface.toArgb())

                            val placeholder = AppCompatResources.getDrawable(
                                LocalContext.current,
                                if (LocalInspectionMode.current) R.drawable.galaxy_image_placeholder else R.drawable.ic_cloud_download
                            )
                            placeholder?.setTint(MaterialTheme.colorScheme.onSurface.toArgb())

                            crossfade(true)
                            fallback(fallback)
                            placeholder(placeholder)
                        }
                    ),
                    contentDescription = null,
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .width(38.dp)
                        .height(38.dp)
                        .clip(CircleShape)
                )

                Column(modifier = Modifier.padding(start = 16.dp, end = 16.dp)) {
                    Text(
                        text = title,
                        modifier = Modifier.fillMaxWidth(),
                        style = TextStyle(fontSize = 18.sp, fontWeight = FontWeight.Medium)
                    )
                    Text(
                        text = subtitle,
                        modifier = Modifier
                            .padding(top = 4.dp)
                            .fillMaxWidth(),
                        style = TextStyle(fontSize = 16.sp)
                    )
                }
            }
        }
    }
}

@Composable
fun HeaderComposable(title: String) {
    AdyenTheme {
        Surface {
            Text(
                text = title,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 16.dp, end = 16.dp, top = 24.dp, bottom = 8.dp),
                style = TextStyle(fontSize = 18.sp, fontWeight = FontWeight.Medium)
            )
        }
    }
}

@Preview(showBackground = true, name = "Apod Light Mode")
@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES, showBackground = true, name = "Apod Dark Mode")
@Composable
fun ApodPreview() {
    return ApodComposable(imageUrl = "", title = "The Milky way", subtitle = "25/06/2022") {}
}

@Preview(showBackground = true, name = "Header Light Mode")
@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES, showBackground = true, name = "Header Dark Mode")
@Composable
fun HeaderPreview() {
    return HeaderComposable(title = "Latest")
}