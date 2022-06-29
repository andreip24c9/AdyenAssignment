package com.adyen.android.assignment.presentation.ui.composables

import android.content.res.Configuration
import androidx.appcompat.content.res.AppCompatResources
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.gestures.rememberScrollableState
import androidx.compose.foundation.gestures.scrollable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.*
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalInspectionMode
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.graphics.ColorUtils
import coil.compose.SubcomposeAsyncImage
import com.adyen.android.assignment.R
import com.adyen.android.assignment.presentation.theme.AdyenTheme
import com.adyen.android.assignment.presentation.theme.OffWhite
import com.adyen.android.assignment.presentation.theme.Translucent
import me.onebone.toolbar.CollapsingToolbarScaffold
import me.onebone.toolbar.CollapsingToolbarScaffoldScope
import me.onebone.toolbar.ScrollStrategy
import me.onebone.toolbar.rememberCollapsingToolbarScaffoldState

@Composable
fun CustomCollapsingToolbarScaffold(
    title: String,
    imageUrl: String? = null,
    onBackClick: () -> Unit,
    content: @Composable CollapsingToolbarScaffoldScope.() -> Unit
) {
    return AdyenTheme {
        val collapsingToolbarScaffoldState = rememberCollapsingToolbarScaffoldState()

        CollapsingToolbarScaffold(
            modifier = Modifier
                .fillMaxSize()
                .background(color = MaterialTheme.colorScheme.background),
//                .scrollable(
//                    orientation = Orientation.Vertical,
//                    // allow to scroll from within the toolbar
//                    state = rememberScrollableState { delta ->
//                        collapsingToolbarScaffoldState.toolbarState.dispatchRawDelta(delta)
//                        delta
//                    }
//                ),
            state = collapsingToolbarScaffoldState,
            scrollStrategy = ScrollStrategy.ExitUntilCollapsed,
            toolbar = {
                SubcomposeAsyncImage(
                    model = imageUrl,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(300.dp)
                        .parallax(0.2f)
                        .graphicsLayer {
                            alpha = collapsingToolbarScaffoldState.toolbarState.progress
                        },
                    contentDescription = null,
                    error = {
                        Image(
                            painter = painterResource(id = R.drawable.ic_cloud_error),
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(300.dp),
                            contentDescription = null,
                            colorFilter = ColorFilter.tint(
                                MaterialTheme.colorScheme.onSurface,
                                BlendMode.SrcIn
                            ),
                        )
                    },
                    loading = {
                        CircularProgressIndicator(
                            modifier = Modifier.padding(80.dp),
                            color = MaterialTheme.colorScheme.secondary
                        )
                    },
                    contentScale = ContentScale.Crop
                )

                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(200.dp)
                        .road(
                            whenCollapsed = Alignment.BottomCenter,
                            whenExpanded = Alignment.BottomCenter
                        )
                        .background(
                            brush = Brush.verticalGradient(
                                colors = listOf(
                                    Color.Transparent,
                                    MaterialTheme.colorScheme.primary
                                )
                            )
                        )
                )

                Text(
                    modifier = Modifier
                        .padding(start = 24.dp, end = 24.dp, top = 52.dp)
                        .road(
                            whenCollapsed = Alignment.CenterStart,
                            whenExpanded = Alignment.CenterStart
                        )
                        .alpha(collapsingToolbarScaffoldState.toolbarState.progress * 2 - 1f),
                    text = title,
                    maxLines = 3,
                    overflow = TextOverflow.Ellipsis,
                    style = TextStyle(
                        color = MaterialTheme.colorScheme.onPrimary,
                        fontSize = 38.sp,
                        fontWeight = FontWeight.Medium,
                        shadow = Shadow(
                            color = MaterialTheme.colorScheme.background,
                            offset = Offset.Zero,
                            blurRadius = 8f
                        )
                    )
                )

                val navEndColor = OffWhite.toArgb()
                val navStartColor = MaterialTheme.colorScheme.onBackground.toArgb()
                val navIconColor: Int =
                    ColorUtils.blendARGB(
                        navStartColor,
                        navEndColor,
                        collapsingToolbarScaffoldState.toolbarState.progress
                    )

                val titleEndColor = Translucent.toArgb()
                val titleStartColor = MaterialTheme.colorScheme.onBackground.toArgb()
                val titleColor: Int = ColorUtils.blendARGB(
                    titleStartColor,
                    titleEndColor,
                    collapsingToolbarScaffoldState.toolbarState.progress
                )
                CenterAlignedTopAppBar(
                    colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
                        containerColor = Color.Transparent,
                        titleContentColor = Color(titleColor),
                        navigationIconContentColor = Color(navIconColor)
                    ),
                    modifier = Modifier.road(
                        whenCollapsed = Alignment.TopStart,
                        whenExpanded = Alignment.TopStart
                    ),
                    title = {
                        Text(
                            modifier = Modifier
                                .padding(start = 32.dp, end = 24.dp)
                                .alpha(1f - collapsingToolbarScaffoldState.toolbarState.progress * 2),
                            text = title,
                            maxLines = 2,
                            overflow = TextOverflow.Ellipsis,
                        )
                    },
                    navigationIcon = {
                        IconButton(onClick = onBackClick) {
                            Icon(Icons.Filled.ArrowBack, "backIcon")
                        }
                    }
                )
            }
        ) { content() }
    }
}


@Preview(showBackground = true, name = "Collapsible Toolbar Light Mode")
@Preview(
    uiMode = Configuration.UI_MODE_NIGHT_YES,
    showBackground = true,
    name = "Collapsible Toolbar Dark Mode"
)
@Composable
fun PreviewCustomCollapsibleToolbar() {
    return AdyenTheme {
        CustomCollapsingToolbarScaffold(
            title = "In, Through, and Beyond Saturn's Rings",
            onBackClick = {}
        ) {
            LazyColumn(modifier = Modifier.fillMaxSize()) {
                items(List(9) { "Hello World!! $it" }) {
                    Text(
                        text = it,
                        color = MaterialTheme.colorScheme.onBackground,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(4.dp)
                    )
                }
            }
        }
    }
}