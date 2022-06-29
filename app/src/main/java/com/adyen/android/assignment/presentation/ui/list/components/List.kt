package com.adyen.android.assignment.presentation.ui.list.components

import android.content.res.Configuration
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.adyen.android.assignment.R
import com.adyen.android.assignment.domain.model.AstronomyPicture
import com.adyen.android.assignment.domain.util.DateHelper
import com.adyen.android.assignment.presentation.theme.AdyenTheme
import java.time.LocalDate

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun ApodListWithStickyHeaders(
    modifier: Modifier = Modifier,
    mapItems: Map<Int, List<AstronomyPicture>>,
    lazyListState: LazyListState = rememberLazyListState(),
    onItemClicked: (itemId: String) -> Unit
) {
    AdyenTheme {
        Surface(modifier) {
            LazyColumn(
                state = lazyListState,
                modifier = Modifier.fillMaxWidth(),
                contentPadding = PaddingValues(bottom = 100.dp, top = 16.dp)
            ) {
                mapItems.forEach { (headerRes, itemList) ->
                    stickyHeader { HeaderComposable(titleRes = headerRes) }

                    itemsIndexed(
                        itemList,
                        key = { index, item ->
                            (item as? AstronomyPicture)?.id ?: index.toString()
                        },
                        itemContent = { index, item ->
                            ApodComposable(
                                modifier = Modifier.animateItemPlacement(),
                                imageUrl = item.url,
                                title = item.title,
                                subtitle = DateHelper.formatShortDate(item.date)
                            ) { onItemClicked(item.id) }
                        })
                }
            }
        }
    }
}

@Preview(showBackground = true, name = "Apod List Light Mode")
@Preview(
    uiMode = Configuration.UI_MODE_NIGHT_YES,
    showBackground = true,
    name = "Apod List Dark Mode"
)
@Composable
fun ApodListPreview() {
    val mockItems = mutableListOf<AstronomyPicture>()

    for (i in 0..20) {
        mockItems.add(
            AstronomyPicture(
                "mock_id", "Mock Item", "mock_explanation",
                LocalDate.now(), "image", null, null, i % 4 == 0
            )
        )
    }

    val groupedItems: Map<Int, List<AstronomyPicture>> =
        mockItems.groupBy { if (it.favorite) R.string.favorites_header_label else R.string.latest_header_label }

    return ApodListWithStickyHeaders(
        modifier = Modifier.fillMaxHeight(),
        mapItems = groupedItems,
        onItemClicked = {}
    )
}