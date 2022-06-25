@file:OptIn(ExperimentalMaterial3Api::class)

package com.adyen.android.assignment.presentation.ui.list

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.ComposeView
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
                    Scaffold(
                        topBar = {
                            CenterAlignedTopAppBar(title = { Text(text = getString(R.string.apod_list_title)) })
                        },
                        content = { paddingValues ->
                            Surface(
                                modifier = Modifier
                                    .padding(paddingValues)
                                    .fillMaxWidth()
                                    .fillMaxHeight()
                            ) {
                                val isLoading = viewModel.listLoader.value
                                val apodsMap = viewModel.apodsMap.value
                                SwipeRefresh(
                                    state = SwipeRefreshState(isLoading && viewModel.apodsMap.value.isNotEmpty()),
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