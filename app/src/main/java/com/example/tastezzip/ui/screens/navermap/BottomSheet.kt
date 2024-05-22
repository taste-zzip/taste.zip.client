package com.example.tastezip.ui.screens.navermap

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyGridState
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Card
import androidx.compose.material.Divider
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Icon
import androidx.compose.material.ModalBottomSheetLayout
import androidx.compose.material.ModalBottomSheetValue
import androidx.compose.material.Text
import androidx.compose.material.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.example.tastezip.R
import com.example.tastezip.model.vo.VideoItemVo
import com.example.tastezip.navigation.NavRoutes
import com.example.tastezip.ui.component.CustomIconButton
import com.example.tastezip.ui.component.CustomText
import com.example.tastezip.ui.screens.shorts.ShortsScreen
import com.example.tastezip.ui.theme.MainActivityTheme
import com.example.tastezip.ui.viewmodel.BottomSheetViewModel
import com.example.tastezip.ui.viewmodel.NaverMapViewModel
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun BottomSheetLayout(
    naverMapviewModel: NaverMapViewModel = hiltViewModel(),
    bottomSheetViewModel: BottomSheetViewModel = hiltViewModel(),
    isShorts: MutableState<Boolean>,
    navController: NavController
) {
    val shopTitle = bottomSheetViewModel.shopTitleState.collectAsState().value
    val distance = bottomSheetViewModel.distanceState.collectAsState().value
    val type = bottomSheetViewModel.typeState.collectAsState().value
    val videoCount = bottomSheetViewModel.videoCountState.collectAsState().value
    val videoList = bottomSheetViewModel.videoListState.collectAsState().value
    val coroutineScope = rememberCoroutineScope()
    val gridState = rememberLazyGridState()
    val sheetState = rememberModalBottomSheetState(
        initialValue = ModalBottomSheetValue.Hidden,
        skipHalfExpanded = false
    )
    val showBottomSheet = {
        if (sheetState.currentValue == ModalBottomSheetValue.Hidden) {
            coroutineScope.launch {
                sheetState.show()
            }
        }
    }

    ModalBottomSheetLayout(
        sheetState = sheetState,
        sheetContent = {
            Box(
                modifier = Modifier
                    .fillMaxHeight(),
                contentAlignment = Alignment.TopStart
            ) {
                Column(
                    modifier = Modifier
                        .padding(20.dp)
                ) {
                    CustomText(
                        text = shopTitle,
                        fontSize = 20.sp,
                        font = Font(R.font.pretendard_bold),
                        color = Color.Black,
                    )

                    Spacer(modifier = Modifier.height(10.dp))

                    Row {
                        CustomText(text = distance.toString() + "m", fontSize = 12.sp, font = Font(R.font.pretendard_medium), color = Color.Black)
                        CustomText(text = type, fontSize = 12.sp, font = Font(R.font.pretendard_medium), color = Color.Gray)
                    }

                    Spacer(modifier = Modifier.height(10.dp))

                    Row (
                        verticalAlignment = Alignment.CenterVertically
                    ){
                        Icon(painter = painterResource(id = R.drawable.ic_info), contentDescription = "ic_info", tint = Color.Unspecified)
                        CustomText(text = "리뷰 영상 " + videoCount.toString() + "개", fontSize = 12.sp, font = Font(R.font.pretendard_medium), color = Color.Black)
                    }

                    Spacer(modifier = Modifier.height(10.dp))

                    FourEqualButtonsWithDividers()

                    Spacer(modifier = Modifier.height(10.dp))

                    CustomText(text = "리뷰", fontSize = 20.sp, font = Font(R.font.pretendard_bold), color = Color.Black)

                    if (videoList.isEmpty()) {
                        Box(
                            contentAlignment = Alignment.Center,
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Spacer(modifier = Modifier.height(10.dp))

                            Icon(
                                painter = painterResource(id = R.drawable.ic_empty_review),
                                contentDescription = "empty_review",
                                tint = Color.Unspecified
                            )
                        }
                    } else {
                        CustomGridLayout(items = videoList, cellCount = 2, gridState = gridState, isShorts, navController)
                    }
                }
            }
        },
        sheetShape = RoundedCornerShape(topStart = 20.dp, topEnd = 20.dp),
        sheetGesturesEnabled = true
    ) {
        NaverMapScreen(viewModel = naverMapviewModel, onMarkerClick = showBottomSheet)
    }
}

@Composable
fun ShortsItem(
    imageUrl: String,
    title: String,
    id: Long,
    starCount: Double,
    trophyCount: Int,
    onClick: () -> Unit
) {
    val configuration = LocalConfiguration.current
    val screenWidth = configuration.screenWidthDp.dp
    val cardWidth = (screenWidth - 40.dp) / 2

    Column(
        modifier = Modifier
            .width(cardWidth)
            .padding(horizontal = 5.dp)
            .padding(bottom = 5.dp)
            .clickable { onClick() },
    ) {
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .height(178.dp),
            shape = RoundedCornerShape(corner = CornerSize(10.dp)),
            elevation = 2.dp
        ) {
            AsyncImage(
                model = imageUrl,
                contentDescription = null,
                contentScale = ContentScale.FillBounds
            )
        }

        Spacer(modifier = Modifier.height(5.dp))

        CustomText(
            text = title,
            fontSize = 10.sp,
            font = Font(R.font.pretendard_medium),
            color = Color.Black
        )

        CustomText(
            text = "조회수",
            fontSize = 10.sp,
            font = Font(R.font.pretendard_medium),
            color = Color.LightGray
        )

        Row {
            CustomText(
                text = starCount.toString(),
                fontSize = 10.sp,
                font = Font(R.font.pretendard_medium),
                color = Color.LightGray
            )

            CustomText(
                text = trophyCount.toString(),
                fontSize = 10.sp,
                font = Font(R.font.pretendard_medium),
                color = Color.LightGray
            )
        }
    }
}

@Composable
fun FourEqualButtonsWithDividers() {
    Box(modifier = Modifier
        .fillMaxWidth()
        .height(55.dp)
    ) {
        Column {
            Divider(
                color = Color.LightGray, modifier = Modifier
                    .fillMaxWidth()
                    .height(1.dp)
            )

            Row(modifier = Modifier
                .fillMaxWidth()
                .height(53.dp)) {
                Divider(
                    color = Color.LightGray, modifier = Modifier
                        .fillMaxHeight()
                        .width(1.dp)
                )

                Button(
                    onClick = { /*TODO*/ },
                    modifier = Modifier
                        .weight(1f)
                        .fillMaxHeight(),
                    colors = ButtonDefaults.buttonColors(
                        backgroundColor = Color.White
                    )
                ) {
                    CustomIconButton(painterResourceId = R.drawable.ic_camera, text = "리뷰하기")
                }

                Divider(
                    color = Color.Black, modifier = Modifier
                        .fillMaxHeight()
                        .width(1.dp)
                )

                Button(
                    onClick = { /*TODO*/ },
                    modifier = Modifier
                        .weight(1f)
                        .fillMaxHeight(),
                    colors = ButtonDefaults.buttonColors(
                        backgroundColor = Color.White
                    )
                ) {
                    CustomIconButton(painterResourceId = R.drawable.ic_chat, text = "댓글")
                }

                Divider(
                    color = Color.Black, modifier = Modifier
                        .fillMaxHeight()
                        .width(1.dp)
                )

                Button(
                    onClick = { /*TODO*/ },
                    modifier = Modifier
                        .weight(1f)
                        .fillMaxHeight(),
                    colors = ButtonDefaults.buttonColors(
                        backgroundColor = Color.White
                    )
                ) {
                    CustomIconButton(painterResourceId = R.drawable.ic_favorite, text = "저장하기")
                }

                Divider(
                    color = Color.Black, modifier = Modifier
                        .fillMaxHeight()
                        .width(1.dp)
                )

                Button(
                    onClick = { /*TODO*/ },
                    modifier = Modifier
                        .weight(1f)
                        .fillMaxHeight(),
                    colors = ButtonDefaults.buttonColors(
                        backgroundColor = Color.White
                    )
                ) {
                    CustomIconButton(painterResourceId = R.drawable.ic_send, text = "공유하기")
                }

                Divider(
                    color = Color.LightGray, modifier = Modifier
                        .fillMaxHeight()
                        .width(1.dp)
                )
            }

            Divider(
                color = Color.LightGray, modifier = Modifier
                    .fillMaxWidth()
                    .height(1.dp)
            )
        }
    }
}

@Composable
fun CustomGridLayout(items: List<VideoItemVo>, cellCount: Int, gridState: LazyGridState, isShorts: MutableState<Boolean>, navController: NavController) {
    LazyVerticalGrid(
        columns = GridCells.Fixed(cellCount),
        state = gridState,
        contentPadding = PaddingValues(horizontal = 2.dp, vertical = 20.dp)
    ) {
        items(items) { item ->
            ShortsItem(
                imageUrl = item.thumbnailUrl,
                title = item.title,
                id = item.id,
                starCount = item.starCount,
                trophyCount = item.trophyCount,
                onClick = {
//                    isShorts.value = true
                    navController.navigate(NavRoutes.StoreShortsScreen.route)
                }
            )
        }
    }
}