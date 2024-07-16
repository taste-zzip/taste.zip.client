package com.example.tastezzip.ui.screens.recommend

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImage
import com.example.tastezzip.R
import com.example.tastezzip.model.response.cafeteria.recommendation.RecommendResponseItem
import com.example.tastezzip.model.response.cafeteria.recommendation.Video
import com.example.tastezzip.repository.VideoRepositoryImpl
import com.example.tastezzip.ui.component.CustomText
import com.example.tastezzip.ui.component.TopBar
import com.example.tastezzip.ui.theme.MainActivityTheme
import com.example.tastezzip.ui.viewmodel.RecommendRestaurantViewModel

@Composable
fun RecommendRestaurant(
    onClickVideoItem: (Int) -> Unit
) {
    val viewModel: RecommendRestaurantViewModel = hiltViewModel()
    val recommendList by viewModel.recommendList.collectAsState()

    LaunchedEffect(Unit) {
        viewModel.getRecommendationList()
    }

    Box(
        modifier = Modifier.fillMaxSize()
    ) {
        Column {
            TopBar(
                imageButtonSourceId = R.drawable.ic_location,
                onClick = {},
                text = stringResource(id = R.string.recommend_title)
            )

            LazyColumn(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(10.dp)
            ) {
                items(recommendList) {
                    RecommendItem(item = it, onClickVideoItem)
                }
            }
        }
    }
}

@Composable
fun RecommendItem(item: RecommendResponseItem, onClickVideoItem: (Int) -> Unit) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(270.dp)
    ) {
        Column {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                CustomText(
                    text = item.name,
                    fontSize = 22.sp,
                    font = Font(R.font.pretendard_bold),
                    color = Color.Black
                )
            }
            Spacer(modifier = Modifier.height(5.dp))
            CustomText(
                text = item.type,
                fontSize = 12.sp,
                font = Font(R.font.pretendard_medium),
                color = Color.LightGray
            )
            Spacer(modifier = Modifier.height(5.dp))
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(painter = painterResource(R.drawable.ic_info), contentDescription = "", tint = Color.Gray)
                Spacer(modifier = Modifier.width(5.dp))
                CustomText(text = "리뷰 영상 ${item.videoCnt}개, 댓글 ${item.videoCnt}개", fontSize = 12.sp, font = Font(R.font.pretendard_medium), color = Color.Black)
            }
            Spacer(modifier = Modifier.height(5.dp))
            LazyRow {
                itemsIndexed(item.videos) {index, it ->
                    Box {
                        Card(
                            modifier = Modifier
                                .width(92.dp)
                                .height(165.dp)
                                .padding(horizontal = 5.dp)
                                .clickable {
                                    VideoRepositoryImpl.cafeteriaName = item.name
                                    VideoRepositoryImpl.videoCnt = item.videoCnt
                                    VideoRepositoryImpl.cafeteriaAddress = item.address
                                    VideoRepositoryImpl.setVideoList(item.videos.map {
                                        com.example.tastezzip.model.response.cafeteria.detail.Video(
                                            id = it.id,
                                            videoPk = it.videoPk,
                                            accountVideoMapping = it.accountVideoMapping,
                                            starAverage = it.starAverage.toDouble(),
                                            title = it.title,
                                            trophyCount = it.trophyCount,
                                            viewCount = it.viewCount
                                        )
                                    })
                                    onClickVideoItem(index)
                                },
                            shape = RoundedCornerShape(corner = CornerSize(10.dp)),
                            elevation = 2.dp,
                        ) {
                            AsyncImage(
                                model = it.thumbnailUrl,
                                contentDescription = null,
                                contentScale = ContentScale.Crop
                            )
                        }

                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            modifier = Modifier
                                .align(Alignment.BottomStart)
                                .padding(4.dp)
                        ) {
                            Icon(
                                painter = painterResource(id = R.drawable.ic_red_star),
                                contentDescription = "",
                                tint = Color.Unspecified
                            )
                            Spacer(modifier = Modifier.width(5.dp))
                            CustomText(text = it.starAverage.toString(), fontSize = 16.sp, font = Font(R.font.pretendard_regular), color = Color.White)
                            Spacer(modifier = Modifier.width(10.dp))
                            Icon(
                                painter = painterResource(id = R.drawable.ic_trophy),
                                contentDescription = "",
                                tint = Color.Unspecified
                            )
                            Spacer(modifier = Modifier.width(5.dp))
                            CustomText(text = it.trophyCount.toString(), fontSize = 16.sp, font = Font(R.font.pretendard_regular), color = Color.White)
                        }
                    }
                }
            }
        }
    }
}