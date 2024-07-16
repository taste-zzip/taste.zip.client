package com.example.tastezzip.ui.screens.mypage

import android.widget.Toast
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
import androidx.compose.runtime.mutableLongStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImage
import com.example.tastezzip.R
import com.example.tastezzip.model.response.cafeteria.bookmark.BookmarkListResponseItem
import com.example.tastezzip.model.response.cafeteria.detail.AccountVideoMapping
import com.example.tastezzip.model.response.cafeteria.recommendation.RecommendResponseItem
import com.example.tastezzip.model.response.cafeteria.recommendation.Video
import com.example.tastezzip.repository.VideoRepositoryImpl
import com.example.tastezzip.ui.component.ConfirmDialog
import com.example.tastezzip.ui.component.CustomText
import com.example.tastezzip.ui.component.TopBar
import com.example.tastezzip.ui.screens.recommend.RecommendItem
import com.example.tastezzip.ui.theme.MainActivityTheme
import com.example.tastezzip.ui.viewmodel.BookmarkCafeteriaViewModel

@Composable
fun BookmarkCafeteria(
    viewModel: BookmarkCafeteriaViewModel = hiltViewModel(),
    popBackStack: () -> Unit,
    onClickVideoItem: (Int) -> Unit
) {
    val context = LocalContext.current
    val cafeteriaName = remember { mutableStateOf("") }
    val cafeteriaId = remember { mutableLongStateOf(-1L) }
    val bookmarkList by viewModel.bookmarkList.collectAsState()
    val showDeleteDialog = remember { mutableStateOf(false) }
    val deleteBookmark = { id: Long, name: String ->
        cafeteriaId.longValue = id
        cafeteriaName.value = name
        showDeleteDialog.value = true
    }

    if (showDeleteDialog.value) {
        ConfirmDialog(
            title = stringResource(id = R.string.my_page_delete_bookmark_title),
            content = stringResource(id = R.string.my_page_delete_bookmark_content, cafeteriaName.value),
            onClickBtnConfirm = {
                viewModel.deleteBookmark(cafeteriaId.longValue)
                Toast.makeText(context, "삭제되었습니다.", Toast.LENGTH_SHORT).show()
            },
            onClickBtnDismiss = { showDeleteDialog.value = false }
        )
    }

    Box(
        modifier = Modifier.fillMaxSize()
    ) {
        Column {
            TopBar(
                imageButtonSourceId = R.drawable.ic_arrow_left,
                onClick = {
                    popBackStack()
                },
                text = stringResource(id = R.string.my_page_bookmark_cafeteria)
            )

            LazyColumn(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(10.dp)
            ) {
                items(bookmarkList) {
                    BookmarkItem(item = it, deleteBookmark, onClickVideoItem)
                }
            }
        }
    }
}

@Composable
fun BookmarkItem(
    item: BookmarkListResponseItem,
    deleteBookmark: (Long, String) -> Unit,
    onClickVideoItem: (Int) -> Unit
) {

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
                    text = item.cafeteria.name,
                    fontSize = 22.sp,
                    font = Font(R.font.pretendard_bold),
                    color = Color.Black,
                    modifier = Modifier
                        .weight(1f)
                        .padding(end = 8.dp),
                    textAlign = TextAlign.Start
                )

                IconButton(
                    onClick = {
                        deleteBookmark(item.cafeteria.id, item.cafeteria.name)
                    }
                ) {
                    Icon(painter = painterResource(id = R.drawable.ic_delete), contentDescription = "")
                }
            }
            CustomText(
                text = item.cafeteria.type,
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
                CustomText(text = "리뷰 영상 ${item.cafeteria.videoCnt}개, 댓글 ${item.cafeteria.commentCnt}개", fontSize = 12.sp, font = Font(R.font.pretendard_medium), color = Color.Black)
            }
            Spacer(modifier = Modifier.height(5.dp))
            LazyRow {
                itemsIndexed(item.videoList) {index, it ->
                    Card(
                        modifier = Modifier
                            .width(92.dp)
                            .height(165.dp)
                            .padding(horizontal = 5.dp)
                            .clickable {
                                VideoRepositoryImpl.cafeteriaName = item.cafeteria.name
                                VideoRepositoryImpl.videoCnt = item.cafeteria.videoCnt
                                VideoRepositoryImpl.cafeteriaAddress = item.cafeteria.streetAddress
                                VideoRepositoryImpl.setVideoList(item.videoList.map {
                                    com.example.tastezzip.model.response.cafeteria.detail.Video(
                                        id = it.id,
                                        videoPk = it.videoPk,
                                        accountVideoMapping = AccountVideoMapping(),
                                        starAverage = it.starAverage,
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
                }
            }
        }
    }
}