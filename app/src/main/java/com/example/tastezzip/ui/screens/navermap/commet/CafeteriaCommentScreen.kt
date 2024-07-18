package com.example.tastezzip.ui.screens.navermap.commet

import android.net.Uri
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Icon
import androidx.compose.material.TextField
import androidx.compose.material.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImage
import coil.compose.rememberAsyncImagePainter
import coil.compose.rememberImagePainter
import coil.request.ImageRequest
import coil.transform.CircleCropTransformation
import com.example.tastezzip.R
import com.example.tastezzip.model.response.cafeteria.detail.CafeteriaDetailResponse
import com.example.tastezzip.model.response.comment.get.Account
import com.example.tastezzip.model.response.comment.get.Comment
import com.example.tastezzip.model.response.comment.get.Content
import com.example.tastezzip.ui.component.CustomText
import com.example.tastezzip.ui.theme.MainActivityTheme
import com.example.tastezzip.ui.viewmodel.BottomSheetViewModel

@Composable
fun CafeteriaCommentScreen(viewModel: BottomSheetViewModel = hiltViewModel(), cafeteriaId: Long) {

    LaunchedEffect(Unit) {
        viewModel.getCafeteriaDetail(cafeteriaId)
        viewModel.getCafeteriaComment(cafeteriaId)
    }

    val cafeteriaDetail by viewModel.cafeteriaDetail.collectAsState()
    val commentList by viewModel.commentList.collectAsState()
    val createComment = { id: Long, content: String ->
        viewModel.createComment(id, content)
    }
    val profileImage = viewModel.getProfileImage()

    CommentScreen(cafeteriaDetail = cafeteriaDetail, commentList = commentList, createComment, profileImage)
}

@Composable
fun CommentScreen(
    cafeteriaDetail: CafeteriaDetailResponse,
    commentList: List<Content>,
    createComment: (Long, String) -> Unit,
    profileImage: Uri
) {
    val comment = remember { mutableStateOf("") }

    Box(
        modifier = Modifier.fillMaxSize()
    ) {
        Column(
            modifier = Modifier
                .padding(10.dp)
                .fillMaxSize()
        ) {
            CustomText(
                text = cafeteriaDetail.name,
                fontSize = 20.sp,
                font = Font(R.font.pretendard_bold),
                color = Color.Black,
            )

            Spacer(modifier = Modifier.height(10.dp))

            CustomText(text = cafeteriaDetail.type, fontSize = 12.sp, font = Font(R.font.pretendard_medium), color = Color.Gray)

            Spacer(modifier = Modifier.height(10.dp))

            Row (
                verticalAlignment = Alignment.CenterVertically
            ){
                Icon(painter = painterResource(id = R.drawable.ic_info), contentDescription = "ic_info", tint = Color.Unspecified)
                Spacer(modifier = Modifier.width(5.dp))
                CustomText(text = "리뷰 영상 " + cafeteriaDetail.videoCnt + "개", fontSize = 12.sp, font = Font(R.font.pretendard_medium), color = Color.Black)
            }

            Spacer(modifier = Modifier.height(5.dp))

            CustomText(text = "주소 " + cafeteriaDetail.address, fontSize = 12.sp, font = Font(R.font.pretendard_medium), color = Color.Black)

            Spacer(modifier = Modifier.height(30.dp))

            CustomText(text = stringResource(id = R.string.comment), fontSize = 20.sp, font = Font(R.font.pretendard_bold), color = Color.Black)

            Spacer(modifier = Modifier.height(10.dp))

            LazyColumn(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f)
            ) {
                items(commentList) {item ->
                    CommentItem(item = item)
                }
            }

            Box(modifier = Modifier
                .fillMaxWidth()
                .height(64.dp)) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.fillMaxSize()
                ) {
//                    Icon(
//                        painter = painterResource(id = R.drawable.ic_my_page),
//                        contentDescription = "",
//                        tint = Color.Gray,
//                        modifier = Modifier.size(48.dp, 48.dp)
//                    )
                    Image(
                        painter = rememberAsyncImagePainter(
                            ImageRequest.Builder(LocalContext.current)
                                .data(data = profileImage)
                                .apply(block = fun ImageRequest.Builder.() {
                                    transformations(CircleCropTransformation())
                                    error(R.drawable.ic_my_page) // 에러 발생 시 보여줄 이미지
                                }).build()
                        ),
                        contentDescription = null,
                        modifier = Modifier
                            .size(48.dp)
                            .then(Modifier)
                    )
                    Spacer(modifier = Modifier.width(10.dp))
                    TextField(
                        modifier = Modifier
                            .weight(1f)
                            .border(1.dp, Color.Black, RoundedCornerShape(8.dp))
                            .height(50.dp),
                        value = comment.value,
                        onValueChange = {
                            comment.value = it
                        },
                        shape = RoundedCornerShape(8.dp),
                        colors = TextFieldDefaults.textFieldColors(
                            backgroundColor = Color.White,
                            cursorColor = Color.Black,
                            focusedIndicatorColor = Color.Transparent,
                            unfocusedIndicatorColor = Color.Transparent
                        ),
                        keyboardActions = KeyboardActions (
                            onDone = {
                                createComment(cafeteriaDetail.id, comment.value)
                                comment.value = ""
                            }
                        ),
                        keyboardOptions = KeyboardOptions(imeAction = ImeAction.Done)
                    )
                }
            }
        }
    }
}

@Composable
fun CommentItem(
    item: Content
) {
    Column {
        Row(
            modifier = Modifier
                .fillMaxWidth()
        ) {
            if (item.account.profileImage.isEmpty()) {
                Icon(
                    painter = painterResource(id = R.drawable.ic_my_page),
                    contentDescription = "",
                    tint = Color.Gray,
                    modifier = Modifier.size(48.dp)
                )
            } else {
                AsyncImage(
                    model = item.account.profileImage,
                    contentDescription = null,
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .size(48.dp)
                        .clip(CircleShape)
                )
            }
            Column(
                modifier = Modifier
                    .weight(1f)
                    .padding(horizontal = 10.dp)
            ) {
                CustomText(
                    text = item.account.nickname,
                    fontSize = 12.sp,
                    font = Font(R.font.pretendard_medium),
                    color = Color.Gray
                )
                Spacer(modifier = Modifier.height(5.dp))
                CustomText(
                    text = item.comment.content,
                    fontSize = 12.sp,
                    font = Font(R.font.pretendard_medium),
                    color = Color.Black
                )
            }
            CustomText(
                text = item.comment.createdAt.substring(0, 10),
                fontSize = 12.sp,
                font = Font(R.font.pretendard_medium),
                color = Color.Gray
            )
        }

        Spacer(modifier = Modifier.height(20.dp))
    }
}