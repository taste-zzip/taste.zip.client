package com.example.tastezzip.ui.screens.shorts

import android.util.Log
import android.view.ViewGroup.LayoutParams
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.snapping.SnapPosition
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.pager.PageSize
import androidx.compose.foundation.pager.VerticalPager
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.ButtonDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.LifecycleOwner
import com.example.tastezzip.R
import com.example.tastezzip.model.response.cafeteria.detail.Video
import com.example.tastezzip.repository.VideoRepositoryImpl
import com.example.tastezzip.ui.component.CustomText
import com.example.tastezzip.ui.viewmodel.BottomSheetViewModel
import com.example.tastezzip.ui.viewmodel.ShortsViewModel
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.PlayerConstants
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.YouTubePlayer
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.AbstractYouTubePlayerListener
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.views.YouTubePlayerView

@Composable
fun ShortsScreen(
    viewModel: ShortsViewModel = hiltViewModel(),
    lifecycleOwner: LifecycleOwner,
    index: Int
) {
    val videoList = VideoRepositoryImpl.getVideoList()
    val showRatingDialog = remember { mutableStateOf(false) }
    val selectedRating = remember { mutableStateOf(0) }
    val videoId = remember { mutableStateOf(-1L) }
    val onClickBtnReview = { id: Long ->
        videoId.value = id
        showRatingDialog.value = true
    }
    viewModel.updatePagerState(videoList.size, index)

    if (showRatingDialog.value) {
        AlertDialog(
            onDismissRequest = { showRatingDialog.value = false },
            confirmButton = {
                androidx.compose.material3.Button(
                    onClick = {
                        viewModel.createVideoRating(videoId.value, selectedRating.value.toDouble())
                        showRatingDialog.value = false
                    },
                    colors = ButtonDefaults.buttonColors(containerColor = Color.Transparent)
                ) {
                    CustomText(
                        text = "확인",
                        fontSize = 14.sp,
                        font = Font(R.font.pretendard_regular),
                        color = Color.Blue
                    )
                }
            },
            dismissButton = {
                androidx.compose.material3.Button(
                    onClick = {
                        showRatingDialog.value = false
                    },
                    colors = ButtonDefaults.buttonColors(containerColor = Color.Transparent)
                ) {
                    CustomText(
                        text = "취소",
                        fontSize = 14.sp,
                        font = Font(R.font.pretendard_regular),
                        color = Color.Blue
                    )
                }
            },
            title = { CustomText(text = stringResource(id = R.string.shorts_rating_dialog_title), fontSize = 20.sp, font = Font(R.font.pretendard_bold), color = Color.Black) },
            text = {
                Column {
                    CustomText(
                        text = stringResource(id = R.string.shorts_rating_dialog_content),
                        fontSize = 14.sp,
                        font = Font(R.font.pretendard_regular),
                        color = Color.DarkGray
                    )
                    Spacer(modifier = Modifier.height(16.dp))
                    Row(
                        horizontalArrangement = Arrangement.Center,
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        for (i in 1..5) {
                            IconButton(onClick = { selectedRating.value = i }) {
                                Icon(
                                    painter = painterResource(
                                        id = if (i <= selectedRating.value) R.drawable.ic_star_filled else R.drawable.ic_start_empty
                                    ),
                                    contentDescription = null,
                                    tint = if (i <= selectedRating.value) Color.Yellow else Color.Gray
                                )
                            }
                        }
                    }
                }
            }
        )
    }

    YoutubeShortsPager(videoList = videoList, modifier = Modifier.fillMaxSize(), viewModel, lifecycleOwner, onClickBtnReview = onClickBtnReview)
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun YoutubeShortsPager(
    videoList: List<Video>,
    modifier: Modifier = Modifier,
    viewModel: ShortsViewModel,
    lifecycleOwner: LifecycleOwner,
    onClickBtnReview: (Long) -> Unit
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
        YoutubeScreen(item = videoList[page], lifecycleOwner, viewModel, onClickBtnReview = onClickBtnReview)
    }
}

@Composable
fun YoutubeScreen(
    item: Video,
    lifecycleOwner: LifecycleOwner,
    viewModel: ShortsViewModel = hiltViewModel(),
    bottomSheetViewModel: BottomSheetViewModel = hiltViewModel(),
    onClickBtnReview: (Long) -> Unit
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

        Column(
            modifier = Modifier
                .align(Alignment.BottomEnd)
                .padding(bottom = 70.dp)
        ) {
            IconButton(
                onClick = {
                    viewModel.updateLikeState(item.accountVideoMapping.like, item.id)
                    bottomSheetViewModel.getCafeteriaDetail(VideoRepositoryImpl.getCafeteriaId())
                },
                modifier = Modifier
                    .padding(16.dp)
                    .background(Color.Transparent)
            ) {
                if (likeState) {
                    Image(
                        painter = painterResource(id = R.drawable.ic_thumbs_up_filled),
                        contentDescription = ""
                    )
                } else {
                    Image(
                        painter = painterResource(id = R.drawable.ic_thumbs_up),
                        contentDescription = ""
                    )
                }
            }

            Column(
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                IconButton(
                    onClick = {
                        onClickBtnReview(item.id)
                    },
                    modifier = Modifier
                        .padding(16.dp)
                        .background(Color.Transparent)
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.ic_star),
                        contentDescription = ""
                    )
                }

                CustomText(text = stringResource(id = R.string.review), fontSize = 12.sp, font = Font(R.font.pretendard_bold), color = Color.White)
            }
        }
    }
}