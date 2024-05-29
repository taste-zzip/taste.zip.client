package com.example.tastezzip.ui.screens.shorts

import android.util.Log
import android.view.ViewGroup.LayoutParams
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.snapping.SnapPosition
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.pager.PageSize
import androidx.compose.foundation.pager.PagerState
import androidx.compose.foundation.pager.VerticalPager
import androidx.compose.material.Button
import androidx.compose.material.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.LifecycleOwner
import com.example.tastezzip.R
import com.example.tastezzip.model.enums.LikeType
import com.example.tastezzip.model.response.cafeteria.detail.Video
import com.example.tastezzip.repository.VideoRepositoryImpl
import com.example.tastezzip.ui.viewmodel.BottomSheetViewModel
import com.example.tastezzip.ui.viewmodel.ShortsViewModel
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.PlayerConstants
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.YouTubePlayer
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.AbstractYouTubePlayerListener
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.views.YouTubePlayerView

@Composable
fun ShortsScreen(
    viewModel: ShortsViewModel = hiltViewModel(),
    lifecycleOwner: LifecycleOwner
) {
    val videoList = VideoRepositoryImpl.getVideoList()
    viewModel.updatePageCount(videoList.size)

    Log.e("비디오 리스트", videoList.toString())

    YoutubeShortsPager(videoList = videoList, modifier = Modifier.fillMaxSize(), viewModel, lifecycleOwner)
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun YoutubeShortsPager(
    videoList: List<Video>,
    modifier: Modifier = Modifier,
    viewModel: ShortsViewModel,
    lifecycleOwner: LifecycleOwner
) {
    val pageState by viewModel.pageStateFlow.collectAsState()

    VerticalPager(
        state = pageState,
        modifier = modifier,
        pageSize = PageSize.Fill,
        snapPosition = SnapPosition.Start
    ) {page ->
        if (page != pageState.currentPage) return@VerticalPager
        Log.e("페이지 증감 변화", page.toString() + '/' + pageState.currentPage)
        YoutubeScreen(item = videoList[page], lifecycleOwner, viewModel)
    }
}

@Composable
fun YoutubeScreen(
    item: Video,
    lifecycleOwner: LifecycleOwner,
    viewModel: ShortsViewModel = hiltViewModel(),
    bottomSheetViewModel: BottomSheetViewModel = hiltViewModel()
) {
    val ctx = LocalContext.current
    val configuration = LocalConfiguration.current
    val screenHeightPx = with(ctx.resources.displayMetrics) { configuration.screenHeightDp * density }.toInt()
    val likeState by viewModel.likeState.collectAsState()

    val youTubePlayerView = remember {
        YouTubePlayerView(ctx).apply {
            lifecycleOwner.lifecycle.addObserver(this)
            layoutParams = LayoutParams(LayoutParams.MATCH_PARENT, screenHeightPx)

            addYouTubePlayerListener(object : AbstractYouTubePlayerListener() {
                override fun onReady(youTubePlayer: YouTubePlayer) {
                    youTubePlayer.loadVideo(item.videoPk, 0f)
                    youTubePlayer.play()
                }

                override fun onStateChange(
                    youTubePlayer: YouTubePlayer,
                    state: PlayerConstants.PlayerState
                ) {
                    if (state == PlayerConstants.PlayerState.ENDED) {
                        youTubePlayer.seekTo(0.0f)
                        youTubePlayer.play()
                    }
                }
            })
        }
    }

    Box {
        AndroidView(
            modifier = Modifier.fillMaxSize(),
            factory = { youTubePlayerView }
        )

        IconButton(
            onClick = {
                viewModel.updateLikeState(item.accountVideoMapping.like, item.id)
                Log.e("뭐가 문젠데", item.id.toString())
                bottomSheetViewModel.getCafeteriaDetail(VideoRepositoryImpl.getCafeteriaId())
            },
            modifier = Modifier
                .align(Alignment.BottomEnd)
                .padding(16.dp)
                .padding(bottom = 50.dp)
                .background(Color.Transparent)
        ) {
            if (likeState) {
                Image(painter = painterResource(id = R.drawable.ic_thumbs_up_filled), contentDescription = "")
            } else {
                Image(painter = painterResource(id = R.drawable.ic_thumbs_up), contentDescription = "")
            }
        }
    }
}