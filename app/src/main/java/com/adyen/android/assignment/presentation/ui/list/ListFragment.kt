@file:OptIn(ExperimentalMaterial3Api::class)

package com.adyen.android.assignment.presentation.ui.list

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.ComposeView
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.adyen.android.assignment.R
import com.adyen.android.assignment.domain.model.AstronomyPicture
import com.adyen.android.assignment.presentation.theme.AdyenTheme
import com.adyen.android.assignment.repository.PlanetaryRepository
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.*
import javax.inject.Inject

@AndroidEntryPoint
class ListFragment : Fragment() {

    @Inject
    lateinit var repository: PlanetaryRepository

    private var planets : MutableState<List<AstronomyPicture>> = mutableStateOf(ArrayList())

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
                                Button(
                                    modifier = Modifier.align(Alignment.Center),
                                    onClick = {
                                        lifecycleScope.launch(Dispatchers.IO) {
                                            planets.value = fetchPlanets()
                                            Log.d(
                                                "Testnetworkresult",
                                                "result: ${planets.value[0].title}"
                                            )
                                        }
                                    },
                                    shape = RoundedCornerShape(12, 12, 12, 12),
                                    colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.secondary)
                                ) {
                                    Text(text = "Next")
                                }
                            }
                        }
                    )
                }
            }
        }
    }

    private suspend fun fetchPlanets() : List<AstronomyPicture> {
        return repository.fetchApodImages(20)
    }
}