package com.adyen.android.assignment.presentation.ui.details

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.gestures.rememberScrollableState
import androidx.compose.foundation.gestures.scrollable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.ComposeView
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import com.adyen.android.assignment.presentation.theme.AdyenTheme
import com.adyen.android.assignment.presentation.ui.composables.ContentComposable
import com.adyen.android.assignment.presentation.ui.composables.CustomCollapsingToolbarScaffold
import com.adyen.android.assignment.presentation.ui.composables.DateLikeComposable
import dagger.hilt.android.AndroidEntryPoint

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
                    CustomCollapsingToolbarScaffold(
                        title = "The Milky way",
                        imageUrl = "https://apod.nasa.gov/apod/image/2206/NGC6744_chakrabarti1024R.jpg",
                        onBackClick = { findNavController().popBackStack() }
                    ) {
                        Column(
                            modifier = Modifier
                                .verticalScroll(rememberScrollState())
                                .fillMaxSize()
                        ) {
                            DateLikeComposable("24/05/1994", true)
                            ContentComposable(
                                text = "What does the Andromeda galaxy really look like? The featured image shows how our Milky Way Galaxy's closest major galactic neighbor really appears in a long exposure through Earth's busy skies and with a digital camera that introduces normal imperfections.  The picture is a stack of 223 images, each a 300 second exposure, taken from a garden observatory in Portugal over the past year.  Obvious image deficiencies include bright parallel airplane trails, long and continuous satellite trails, short cosmic ray streaks, and bad pixels.  These imperfections were actually not removed with Photoshop specifically, but rather greatly reduced with a series of computer software packages that included Astro Pixel Processor, DeepSkyStacker, and PixInsight.  All of this work was done not to deceive you with a digital fantasy that has little to do with the real likeness of the Andromeda galaxy (M31), but to minimize Earthly artifacts that have nothing to do with the distant galaxy and so better recreate what M31 really does look like."
                            )
                        }

//                        LazyColumn(
//                            modifier = Modifier
//                                .fillMaxSize()
//                        ) {
//                            items(1) {
//                                DateLikeComposable("24/05/1994", true)
//                                ContentComposable(
//                                    text = "What does the Andromeda galaxy really look like? The featured image shows how our Milky Way Galaxy's closest major galactic neighbor really appears in a long exposure through Earth's busy skies and with a digital camera that introduces normal imperfections.  The picture is a stack of 223 images, each a 300 second exposure, taken from a garden observatory in Portugal over the past year.  Obvious image deficiencies include bright parallel airplane trails, long and continuous satellite trails, short cosmic ray streaks, and bad pixels.  These imperfections were actually not removed with Photoshop specifically, but rather greatly reduced with a series of computer software packages that included Astro Pixel Processor, DeepSkyStacker, and PixInsight.  All of this work was done not to deceive you with a digital fantasy that has little to do with the real likeness of the Andromeda galaxy (M31), but to minimize Earthly artifacts that have nothing to do with the distant galaxy and so better recreate what M31 really does look like."
//                                )
//                            }
//                        }
                    }
                }
            }
        }
    }
}