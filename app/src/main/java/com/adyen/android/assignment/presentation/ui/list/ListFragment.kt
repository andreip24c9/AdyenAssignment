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
import com.adyen.android.assignment.presentation.theme.AdyenTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ListFragment : Fragment() {

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
                            CenterAlignedTopAppBar(title = { Text(text = "Top App Bar") })
                        },
                        content = { paddingValues ->
                            Box(
                                modifier = Modifier
                                    .padding(paddingValues)
                                    .fillMaxWidth()
                                    .fillMaxHeight()
                            ) {
                                // todo add UI here
                            }
                        }
                    )
                }
            }
        }
    }
}