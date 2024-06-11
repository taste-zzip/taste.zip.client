package com.example.tastezzip.ui.screens.foodWc

import android.util.Log
import android.view.ViewGroup
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.gestures.snapping.SnapPosition
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.PageSize
import androidx.compose.foundation.pager.PagerState
import androidx.compose.material.IconButton
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.ButtonDefaults
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.key
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.compose.ui.window.DialogProperties
import androidx.core.content.ContextCompat
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.LifecycleOwner
import com.example.tastezzip.R
import com.example.tastezzip.model.response.worldcup.WorldCupListResponseItem
import com.example.tastezzip.ui.component.CustomText
import com.example.tastezzip.ui.viewmodel.FoodWorldCupGameViewModel
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.PlayerConstants
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.YouTubePlayer
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.AbstractYouTubePlayerListener
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.views.YouTubePlayerView

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun FoodWorldCupGame() {
    val viewModel: FoodWorldCupGameViewModel = hiltViewModel()
    val videoList by viewModel.videoList.collectAsState()
    val currentMatch by viewModel.currentMatch.collectAsState()
    val finishWorldCup by viewModel.finishWorldCup.collectAsState()
    val winnerItem by viewModel.winnerItem.collectAsState()
    val pagerState by viewModel.pagerState.collectAsState()
    val lifecycleOwner = LocalLifecycleOwner.current
    val showWinnerDialog = remember { mutableStateOf(false) }

    if (finishWorldCup) {
        showWinnerDialog.value = true
        viewModel.resetFinishWorldCup()
    }

    if (showWinnerDialog.value) {
        AlertDialog(
            properties = DialogProperties(
                dismissOnBackPress = true,
                dismissOnClickOutside = true
            ),
            onDismissRequest = { showWinnerDialog.value = false },
            title = {
                CustomText(
                    text = "${winnerItem.cafeteriaInfo.name} 우승!",
                    fontSize = 24.sp,
                    font = Font(R.font.pretendard_bold),
                    color = Color.Black
                )
            },
            confirmButton = {
                Button(
                    onClick = { showWinnerDialog.value = false },
                    colors = ButtonDefaults.buttonColors(containerColor = Color.Transparent),
                    contentPadding = PaddingValues(horizontal = 8.dp, vertical = 4.dp)
                ) {
                    CustomText(text = "맛집 추가", fontSize = 14.sp, font = Font(R.font.pretendard_regular), color = Color.Black)
                }
            },
            dismissButton = {
                Button(
                    onClick = { showWinnerDialog.value = false },
                    colors = ButtonDefaults.buttonColors(containerColor = Color.Transparent),
                    contentPadding = PaddingValues(horizontal = 8.dp, vertical = 4.dp)

                ) {
                    CustomText(text = "영상 더보기", fontSize = 14.sp, font = Font(R.font.pretendard_regular), color = Color.Black)
                }
            },
            text = {
                Column(
                    horizontalAlignment = Alignment.Start
                ) {
                    CustomText(text = winnerItem.cafeteriaInfo.address, fontSize = 14.sp, font = Font(R.font.pretendard_regular), color = Color.Black)
                    CustomText(text = "리뷰영상 ${winnerItem.cafeteriaInfo.videoCnt}개", fontSize = 14.sp, font = Font(R.font.pretendard_regular), color = Color.Black)
                }
            },
        )
    }

    if (videoList.isNotEmpty()) {
        key(currentMatch) {
            FoodWorldCupRound(
                videoList = listOf(videoList[currentMatch.first], videoList[currentMatch.second]),
                pagerState = pagerState,
                lifecycleOwner = lifecycleOwner,
                videoList.size
            ) { id ->
                viewModel.selectWinner(id)
            }
        }
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun FoodWorldCupRound(
    videoList: List<WorldCupListResponseItem>,
    pagerState: PagerState,
    lifecycleOwner: LifecycleOwner,
    currentRound: Int,
    onClickBtnSelect: (Long) -> Unit
) {
    val currentVideoList by rememberUpdatedState(newValue = videoList)

    HorizontalPager(
        state = pagerState,
        modifier = Modifier
            .fillMaxSize(),
        pageSize = PageSize.Fill,
        snapPosition = SnapPosition.Start
    ) {page ->
        Log.e("page", page.toString())
        WorldCupShortsScreen(item = currentVideoList[page], lifecycleOwner = lifecycleOwner, currentRound = currentRound, onClickBtnSelect = onClickBtnSelect)
    }
}

@Composable
fun WorldCupShortsScreen(
    item: WorldCupListResponseItem,
    lifecycleOwner: LifecycleOwner,
    currentRound: Int,
    onClickBtnSelect: (Long) -> Unit,
) {
    val ctx = LocalContext.current
    val configuration = LocalConfiguration.current
    val screenHeightPx = with(ctx.resources.displayMetrics) { configuration.screenHeightDp * density }.toInt()

    val youTubePlayerView = remember {
        YouTubePlayerView(ctx).apply {
            lifecycleOwner.lifecycle.addObserver(this)
            layoutParams =
                ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, screenHeightPx)

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
                .padding(bottom = 70.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Box(
                contentAlignment = Alignment.Center,
                modifier = Modifier.size(48.dp)
            ) {
                Canvas(modifier = Modifier.fillMaxSize()) {
                    drawCircle(
                        color = Color(ContextCompat.getColor(ctx, R.color.yellow)),
                        radius = 48.dp.toPx() / 2
                    )
                }
                CustomText(text = "${currentRound}강", fontSize = 12.sp, font = Font(R.font.pretendard_bold), color = Color.White)
            }
            Spacer(modifier = Modifier.height(30.dp))
            IconButton(
                onClick = {
                    onClickBtnSelect(item.id)
                }
            ) {
                Icon(painter = painterResource(id = R.drawable.ic_btn_select_wc), contentDescription = "", tint = Color.Unspecified)
            }
        }
    }
}