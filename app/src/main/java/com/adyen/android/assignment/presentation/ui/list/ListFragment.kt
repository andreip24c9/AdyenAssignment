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
                                GenericVerticalListWithStickyHeaders(
                                    modifier = Modifier.fillMaxHeight(),
                                    mapItems = viewModel.apodsMap.value,
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

                                val isLoading = viewModel.listLoader.value
                                val loadError = viewModel.initialLoadError.value
                                if (isLoading || loadError != null) {
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