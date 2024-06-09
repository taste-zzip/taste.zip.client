package com.example.tastezzip.ui.screens.shorts

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.LifecycleOwner
import com.example.tastezzip.model.response.cafeteria.detail.Video
import com.example.tastezzip.ui.viewmodel.ShortsTapViewModel
import com.example.tastezzip.ui.viewmodel.ShortsViewModel

@Composable
fun ShortsTapScreen(
    viewModel: ShortsTapViewModel = hiltViewModel(),
    shortsViewModel: ShortsViewModel = hiltViewModel(),
    lifecycleOwner: LifecycleOwner
) {
    val videoList by viewModel.videoList.collectAsState()
    val showRatingDialog = remember { mutableStateOf(false) }
    val selectedRating = remember { mutableStateOf(0) }
    var videoId: Long = -1L
    val onClickBtnReview = { id: Long ->
        videoId = id
        showRatingDialog.value = true
    }
    val newList = videoList.map {
        Video(
            id = it.video.id,
            platform = it.video.platform,
            starAverage = 0,
            status = it.video.status,
            thumbnailUrl = it.youtubeVideo.thumbnail,
            title = it.youtubeVideo.title,
            trophyCount = 0,
            videoUrl = "",
            videoPk = it.video.videoPk,
            viewCount = it.youtubeVideo.viewCount,
            accountVideoMapping = it.accountVideoMapping
        )
    }
    shortsViewModel.updatePagerState(videoList.size, 0)

    YoutubeShortsPager(videoList = newList, modifier = Modifier.fillMaxSize(), shortsViewModel, lifecycleOwner, onClickBtnReview = onClickBtnReview)
}