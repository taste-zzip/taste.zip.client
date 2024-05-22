package com.example.tastezip.ui.screens.shorts

import android.util.Log
import android.view.ViewGroup.LayoutParams
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.gestures.snapping.SnapPosition
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.pager.PageSize
import androidx.compose.foundation.pager.PagerState
import androidx.compose.foundation.pager.VerticalPager
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.lifecycleScope
import com.example.tastezip.ui.viewmodel.ShortsViewModel
import com.example.tastezip.ui.viewmodel.VideoItem
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.PlayerConstants
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.YouTubePlayer
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.AbstractYouTubePlayerListener
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.views.YouTubePlayerView
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlin.math.abs

@Composable
fun ShortsScreen(
    viewModel: ShortsViewModel = hiltViewModel(),
    lifecycleOwner: LifecycleOwner
) {
    val videoList = viewModel.videoList

    YoutubeShortsPager(videoList = videoList, modifier = Modifier.fillMaxSize(), viewModel, lifecycleOwner)
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun YoutubeShortsPager(
    videoList: List<VideoItem>,
    modifier: Modifier = Modifier,
    viewModel: ShortsViewModel,
    lifecycleOwner: LifecycleOwner
) {
    val pageState = viewModel.pageStateFlow.collectAsState().value

    VerticalPager(
        state = pageState,
        modifier = modifier,
        pageSize = PageSize.Fill,
        snapPosition = SnapPosition.Start
    ) {page ->
        if (page != pageState.currentPage) return@VerticalPager
        Log.e("페이지 증감 변화", page.toString() + '/' + pageState.currentPage)
        YoutubeScreen(item = videoList[page], lifecycleOwner)
    }
}

@Composable
fun YoutubeScreen(
    item: VideoItem,
    lifecycleOwner: LifecycleOwner,
) {
    val ctx = LocalContext.current
    val configuration = LocalConfiguration.current
    val screenHeightPx = with(ctx.resources.displayMetrics) { configuration.screenHeightDp * density }.toInt()

    val youTubePlayerView = remember {
        YouTubePlayerView(ctx).apply {
            lifecycleOwner.lifecycle.addObserver(this)
            layoutParams = LayoutParams(LayoutParams.MATCH_PARENT, screenHeightPx)

            addYouTubePlayerListener(object : AbstractYouTubePlayerListener() {
                override fun onReady(youTubePlayer: YouTubePlayer) {
                    youTubePlayer.loadVideo(item.videoId, 0f)
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

    AndroidView(
        modifier = Modifier.fillMaxSize(),
        factory = { youTubePlayerView }
    )
}