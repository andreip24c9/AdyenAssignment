@file:OptIn(ExperimentalMaterial3Api::class)

package com.adyen.android.assignment.presentation.ui.list

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.adyen.android.assignment.R
import com.adyen.android.assignment.presentation.theme.AdyenTheme
import com.adyen.android.assignment.presentation.ui.composables.*
import com.google.accompanist.swiperefresh.SwipeRefresh
import com.google.accompanist.swiperefresh.SwipeRefreshIndicator
import com.google.accompanist.swiperefresh.SwipeRefreshState
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ListFragment : Fragment() {

    private val viewModel: ListViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return ComposeView(requireContext()).apply {
            setContent {
                AdyenTheme {
                    val scrollState = rememberLazyListState()
                    val openDialog = remember { mutableStateOf(false) }
                    Scaffold(
                        topBar = {
                            CenterAlignedTopAppBar(title = { Text(text = getString(R.string.apod_list_title)) })
                        },
                        floatingActionButton = {
                            if (viewModel.apodsMap.value.isNotEmpty()) {
                                val state =
                                    derivedStateOf { scrollState.firstVisibleItemIndex }.value == 0
                                ExtendableFloatingActionButton(
                                    extended = state,
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
                                    },
                                    onClick = { openDialog.value = true }
                                )
                            }
                        },
                        content = { paddingValues ->
                            Surface(
                                modifier = Modifier
                                    .padding(paddingValues)
                                    .fillMaxWidth()
                                    .fillMaxHeight()
                            ) {

                                if (openDialog.value) {
                                    ReorderDialog(
                                        selectedOptionRes = when (viewModel.selectedSorting.value) {
                                            ListViewModel.Sorting.TITLE -> R.string.order_by_title_label
                                            ListViewModel.Sorting.DATE -> R.string.order_by_date_label
                                        },
                                        optionsRes = mutableListOf(
                                            R.string.order_by_title_label,
                                            R.string.order_by_date_label
                                        ),
                                        onDismiss = { openDialog.value = false },
                                        onApply = { optionRes ->
                                            when (optionRes) {
                                                R.string.order_by_title_label -> viewModel.reorderByTitle()
                                                R.string.order_by_date_label -> viewModel.reorderByDate()
                                            }
                                        })
                                }

                                val isLoading = viewModel.listLoader.value
                                val apodsMap = viewModel.apodsMap.value
                                SwipeRefresh(
                                    state = SwipeRefreshState(isLoading && apodsMap.isNotEmpty()),
                                    indicator = { state, trigger ->
                                        SwipeRefreshIndicator(
                                            state = state,
                                            refreshTriggerDistance = trigger,
                                            scale = true,
                                            backgroundColor = MaterialTheme.colorScheme.primary,
                                            contentColor = MaterialTheme.colorScheme.secondary
                                        )
                                    },
                                    onRefresh = { viewModel.refresh() }) {
                                    GenericVerticalListWithStickyHeaders(
                                        modifier = Modifier.fillMaxHeight(),
                                        lazyListState = scrollState,
                                        mapItems = apodsMap,
                                        headers = { headerRes ->
                                            HeaderComposable(titleRes = headerRes)
                                        },
                                        items = { index, item ->
                                            ApodComposable(
                                                imageUrl = item.url,
                                                title = item.title,
                                                subtitle = item.date.toString()
                                            ) {
                                                // todo open details
                                            }
                                        }
                                    )
                                }

                                Box(contentAlignment = Alignment.BottomCenter) {
                                    GradientComposable(modifier = Modifier.height(120.dp))
                                }

                                val loadError = viewModel.initialLoadError.value
                                if (apodsMap.isEmpty() && isLoading || loadError != null) {
                                    LoadingErrorView(
                                        modifier = Modifier.fillMaxHeight(),
                                        isLoading = isLoading,
                                        error = loadError,
                                        retryButton = RetryButton(R.string.refresh) { // todo move logic
                                            viewModel.refresh()
                                        }
                                    )
                                }
                            }
                        }
                    )
                }
            }
        }
    }
}