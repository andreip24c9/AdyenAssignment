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
import androidx.compose.ui.graphics.BlendMode
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalInspectionMode
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.TextUnit
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
                    .padding(start = 32.dp, end = 16.dp, bottom = 12.dp, top = 12.dp),
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

@Composable
fun DateLikeComposable(
    date: String,
    isLiked: Boolean
) {
    return Surface {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 24.dp, end = 32.dp, top = 8.dp, bottom = 8.dp),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = date,
                modifier = Modifier.wrapContentWidth(),
                style = TextStyle(fontSize = 16.sp, fontWeight = FontWeight.Medium)
            )

            Image(
                painter = painterResource(id = if (isLiked) R.drawable.ic_favorite_filled else R.drawable.ic_favorite_border),
                contentDescription = null,
                colorFilter = if (!isLiked) ColorFilter.tint(
                    MaterialTheme.colorScheme.onSurface,
                    BlendMode.SrcIn
                ) else null
            )
        }
    }
}

@Composable
fun ContentComposable(text: String) {
    return Surface {
            Text(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 24.dp, end = 24.dp, top = 16.dp, bottom = 16.dp),
                text = text,
                style = TextStyle(fontSize = 16.sp, lineHeight = 28.sp)
            )
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

@Preview(showBackground = true, name = "Date & Like Light Mode")
@Preview(
    uiMode = Configuration.UI_MODE_NIGHT_YES,
    showBackground = true,
    name = "Date & Like Dark Mode"
)
@Composable
fun dateComposablePreviewNotLiked() {
    return AdyenTheme {
        DateLikeComposable("24/05/2018", false)
    }
}

@Preview(showBackground = true, name = "Date & Like Light Mode")
@Preview(
    uiMode = Configuration.UI_MODE_NIGHT_YES,
    showBackground = true,
    name = "Date & Like Dark Mode"
)
@Composable
fun dateComposablePreviewLiked() {
    return AdyenTheme {
        DateLikeComposable("24/05/2018", true)
    }
}

@Preview(showBackground = true, name = "Content Light Mode")
@Preview(
    uiMode = Configuration.UI_MODE_NIGHT_YES,
    showBackground = true,
    name = "Content Dark Mode"
)
@Composable
fun contentPreviewLiked() {
    return AdyenTheme {
        ContentComposable(
            "What does the Andromeda galaxy really look like? The featured image shows how our Milky Way Galaxy's closest major galactic neighbor really appears in a long exposure through Earth's busy skies and with a digital camera that introduces normal imperfections.  The picture is a stack of 223 images, each a 300 second exposure, taken from a garden observatory in Portugal over the past year.  Obvious image deficiencies include bright parallel airplane trails, long and continuous satellite trails, short cosmic ray streaks, and bad pixels.  These imperfections were actually not removed with Photoshop specifically, but rather greatly reduced with a series of computer software packages that included Astro Pixel Processor, DeepSkyStacker, and PixInsight.  All of this work was done not to deceive you with a digital fantasy that has little to do with the real likeness of the Andromeda galaxy (M31), but to minimize Earthly artifacts that have nothing to do with the distant galaxy and so better recreate what M31 really does look like."
        )
    }
}