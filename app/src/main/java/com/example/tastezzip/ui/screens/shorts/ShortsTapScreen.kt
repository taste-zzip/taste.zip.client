package com.example.tastezzip.ui.screens.shorts

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.LifecycleOwner
import com.example.tastezzip.R
import com.example.tastezzip.model.response.cafeteria.detail.Video
import com.example.tastezzip.model.response.worldcup.CafeteriaResponse
import com.example.tastezzip.ui.component.CustomText
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
    var videoId = remember { mutableStateOf(-1L) }
    val onClickBtnReview = { id: Long ->
        videoId.value = id
        showRatingDialog.value = true
    }
    val newList = videoList.map {
        Video(
            id = it.video.id,
            platform = it.video.platform,
            starAverage = 0.0,
            status = it.video.status,
            thumbnailUrl = it.youtubeVideo.thumbnail,
            title = it.youtubeVideo.title,
            trophyCount = 0,
            videoUrl = "",
            videoPk = it.video.videoPk,
            viewCount = it.youtubeVideo.viewCount,
            accountVideoMapping = it.accountVideoMapping,
            cafeteriaResponse = CafeteriaResponse(
                streetAddress = it.cafeteria.streetAddress,
                name = it.cafeteria.name,
                id = it.cafeteria.id,
                type = it.cafeteria.type
            )
        )
    }
    shortsViewModel.updatePagerState(videoList.size, 0)

    if (showRatingDialog.value) {
        AlertDialog(
            onDismissRequest = { showRatingDialog.value = false },
            confirmButton = {
                Button(
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
                Button(
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
            title = { CustomText(text = stringResource(id = R.string.shorts_rating_dialog_title), fontSize = 20.sp, font = Font(
                R.font.pretendard_bold), color = Color.Black) },
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

    YoutubeShortsPager(videoList = newList, modifier = Modifier.fillMaxSize(), shortsViewModel, lifecycleOwner, onClickBtnReview = onClickBtnReview)
}