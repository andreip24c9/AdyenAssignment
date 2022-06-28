package com.adyen.android.assignment.presentation.ui.details

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.ComposeView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.findNavController
import androidx.navigation.fragment.navArgs
import com.adyen.android.assignment.domain.util.DateHelper
import com.adyen.android.assignment.presentation.theme.AdyenTheme
import com.adyen.android.assignment.presentation.ui.composables.*
import com.adyen.android.assignment.presentation.ui.list.ListFragmentArgs
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class DetailsFragment : Fragment() {

    private val args: ListFragmentArgs by navArgs()
    @Inject
    lateinit var detailsViewModelAssistedFactory: DetailsViewModel.AssistedFactory
    private val viewModel: DetailsViewModel by viewModels {
        DetailsViewModel.provideFactory(detailsViewModelAssistedFactory, args.apodId)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return ComposeView(requireContext()).apply {
            setContent {
                AdyenTheme {
                    val apod = viewModel.apod.value
                    CustomCollapsingToolbarScaffold(
                        title = apod?.title ?: "",
                        imageUrl = apod?.url,
                        onBackClick = { findNavController().popBackStack() }
                    ) {
                        Column(
                            modifier = Modifier
                                .verticalScroll(rememberScrollState())
                                .fillMaxSize()
                        ) {
                            val isLoading = viewModel.listLoader.value
                            val loadError = viewModel.initialLoadError.value

                            if (apod == null && isLoading || loadError != null) {
                                LoadingErrorView(
                                    modifier = Modifier.fillMaxHeight(),
                                    isLoading = isLoading,
                                    error = loadError
                                )
                            } else {
                                DateLikeComposable(DateHelper.formatLongDate(apod!!.date), apod.favorite)
                                ContentComposable(
                                    text = apod.explanation
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}
