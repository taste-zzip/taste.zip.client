package com.example.tastezip.ui.screens.navermap

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.ModalBottomSheetLayout
import androidx.compose.material.ModalBottomSheetValue
import androidx.compose.material.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.example.tastezip.R
import com.example.tastezip.ui.component.CustomText
import com.example.tastezip.ui.viewmodel.NaverMapViewModel
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun BottomSheetLayout(viewModel: NaverMapViewModel) {
    val coroutineScope = rememberCoroutineScope()
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
                        text = "리뷰",
                        fontSize = 20.sp,
                        font = Font(R.font.pretendard_bold),
                        color = Color.Black,
                    )

                    Spacer(modifier = Modifier.height(20.dp))

                    ShortsItem(
                        imageUrl = "https://mblogthumb-phinf.pstatic.net/MjAxODA5MjdfMjky/MDAxNTM4MDM4ODk4NDkx.Vb7NwFAUi7oRMD4GBX2Vp1YDRiXb0lAlLVd7Eh4pX1wg.W94Aj26a78S-9vkgtNPwImRCV54r-BCeX_GaSThzfsMg.JPEG.yamtablecontents/002_%EC%88%99%EC%84%B1%ED%9A%8C.jpg?type=w800",
                        title = "테스트",
                        id = 1,
                        starCount = 1.0,
                        trophyCount = 1.0
                    )
                }
            }
        },
        sheetShape = RoundedCornerShape(topStart = 20.dp, topEnd = 20.dp),
        sheetGesturesEnabled = true
    ) {
        NaverMapScreen(viewModel = viewModel, onMarkerClick = showBottomSheet)
    }
}

@Composable
fun ShortsItem(
    imageUrl: String,
    title: String,
    id: Long,
    starCount: Double,
    trophyCount: Double
) {
    val configuration = LocalConfiguration.current
    val screenWidth = configuration.screenWidthDp.dp
    val cardWidth = (screenWidth - 30.dp) / 2

    Column(
        modifier = Modifier
            .width(cardWidth),
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
            )
        }

        Spacer(modifier = Modifier.height(5.dp))

        CustomText(
            text = title,
            fontSize = 12.sp,
            font = Font(R.font.pretendard_medium),
            color = Color.Black
        )
    }
}