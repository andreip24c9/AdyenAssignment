package com.adyen.android.assignment.presentation.ui.details

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.animation.rememberSplineBasedDecay
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.unit.dp
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.adyen.android.assignment.presentation.theme.AdyenTheme
import dagger.hilt.android.AndroidEntryPoint

@OptIn(ExperimentalMaterial3Api::class)
@AndroidEntryPoint
class DetailsFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return ComposeView(requireContext()).apply {
            setContent {
                AdyenTheme {
                    val decayAnimationSpec = rememberSplineBasedDecay<Float>()
                    val scrollBehavior = TopAppBarDefaults.exitUntilCollapsedScrollBehavior(
                        decayAnimationSpec,
                        rememberTopAppBarScrollState()
                    )

                    Scaffold(
                        modifier = Modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
                        topBar = {
                            LargeTopAppBar(
                                title = { Text(text = "The Milky way") },
                                navigationIcon = {
                                    IconButton(onClick = { findNavController().popBackStack() }) {
                                        Icon(Icons.Filled.ArrowBack, "backIcon")
                                    }
                                },
                                scrollBehavior = scrollBehavior
                            )
                        },
                        content = { innerPadding ->
                            LazyColumn(
                                contentPadding = innerPadding,
                                verticalArrangement = Arrangement.spacedBy(8.dp)
                            ) {
                                val list = (0..75).map { it.toString() }
                                items(count = list.size) {
                                    Text(
                                        text = list[it],
                                        style = MaterialTheme.typography.bodyLarge,
                                        modifier = Modifier
                                            .fillMaxWidth()
                                            .padding(horizontal = 16.dp)
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