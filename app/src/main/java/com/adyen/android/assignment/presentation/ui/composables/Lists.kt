package com.adyen.android.assignment.presentation.ui.composables

import android.content.res.Configuration
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.*
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.adyen.android.assignment.domain.model.AstronomyPicture
import com.adyen.android.assignment.presentation.theme.AdyenTheme
import java.time.LocalDate

@Composable
fun <ItemType> GenericVerticalList(
    modifier: Modifier = Modifier,
    listItems: List<ItemType>,
    lazyListState: LazyListState = rememberLazyListState(),
    items: @Composable (index: Int, item: ItemType) -> Unit
) {
    AdyenTheme {
        Surface(modifier) {
            LazyColumn(
                state = lazyListState,
                modifier = Modifier.fillMaxWidth(),
                contentPadding = PaddingValues(bottom = (100.dp))
            ) {
                itemsIndexed(listItems) { index, item -> items(index, item) }
            }
        }
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun <ItemType> GenericVerticalListWithStickyHeaders(
    modifier: Modifier = Modifier,
    mapItems: Map<String, List<ItemType>>,
    lazyListState: LazyListState = rememberLazyListState(),
    headers: @Composable (headerTitle: String) -> Unit,
    items: @Composable (index: Int, item: ItemType) -> Unit
) {
    AdyenTheme {
        Surface(modifier) {
            LazyColumn(
                state = lazyListState,
                modifier = Modifier.fillMaxWidth(),
                contentPadding = PaddingValues(bottom = (100.dp))
            ) {
                mapItems.forEach { (header, itemList) ->
                    stickyHeader { headers(header) }

                    itemsIndexed(itemList) { index, item ->
                        items(index, item)
                    }
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
    val mockItems = mutableListOf(
        AstronomyPicture(
            "1", "Shooting star", "explanation",
            LocalDate.now(), "image", null, null, true
        ),

        AstronomyPicture(
            "2", "Blood moon", "explanation",
            LocalDate.now(), "image", null, null
        ),

        AstronomyPicture(
            "3", "The Milky way", "explanation",
            LocalDate.now(), "image", null, null, true
        ),

        AstronomyPicture(
            "4", "Gravity's grin", "explanation",
            LocalDate.now(), "image", null, null
        ),

        AstronomyPicture(
            "4", "Gravity's grin", "explanation",
            LocalDate.now(), "image", null, null
        )
    )

    for (i in 0..20) {
        mockItems.add(
            AstronomyPicture(
                "mock_id", "Mock Item", "mock_explanation",
                LocalDate.now(), "image", null,
                "https://apod.nasa.gov/apod/image/2206/NGC6744_chakrabarti1024R.jpg"
            )
        )
    }

    val groupedItems: Map<String, List<AstronomyPicture>> =
        mockItems.groupBy { if (it.favorite) "Favorite" else "Latest" }

    return GenericVerticalListWithStickyHeaders(
        modifier = Modifier.fillMaxHeight(),
        mapItems = groupedItems,
        headers = { headerTitle ->
            HeaderComposable(headerTitle)
        },
        items = { index, item ->
            ApodComposable(
                imageUrl = item.url,
                title = item.title,
                subtitle = item.date.toString()
            ) {}
        }
    )
}