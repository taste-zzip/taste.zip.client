package com.example.tastezzip.ui.screens.mypage

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.tastezzip.R
import com.example.tastezzip.data.api.AccountApi
import com.example.tastezzip.data.repository.AccountRepository
import com.example.tastezzip.data.repositoryImpl.AccountRepositoryImpl
import com.example.tastezzip.ui.component.CustomText
import com.example.tastezzip.ui.component.TopBar
import com.example.tastezzip.ui.theme.MainActivityTheme
import com.example.tastezzip.ui.viewmodel.MyPageViewModel

@Composable
fun MyPageScreen(
    onClickBookmarkCafeteria: () -> Unit,
) {
    val viewModel: MyPageViewModel = hiltViewModel()
    val nickname by viewModel.nickname.collectAsState()
    val bio by viewModel.bio.collectAsState()
    val showDeleteDialog = remember { mutableStateOf(false) }

    if (showDeleteDialog.value) {
        AlertDialog(
            onDismissRequest = { showDeleteDialog.value = false },
            confirmButton = {
                Button(
                    onClick = {
                        viewModel.deleteAccount()
                        showDeleteDialog.value = false
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
                        showDeleteDialog.value = false
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
            title = { CustomText(text = stringResource(id = R.string.my_page_delete_account), fontSize = 20.sp, font = Font(R.font.pretendard_bold), color = Color.Black) },
        )
    }

    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        TopBar(imageButtonSourceId = null, onClick = { }, text = stringResource(id = R.string.my_page))

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(96.dp)
                .padding(10.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                painter = painterResource(id = R.drawable.ic_my_page),
                contentDescription = "",
                tint = Color.LightGray,
                modifier = Modifier.size(48.dp, 48.dp)
            )

            Spacer(modifier = Modifier.width(10.dp))

            Column(
                modifier = Modifier.fillMaxHeight(),
                verticalArrangement = Arrangement.SpaceEvenly
            ) {
                CustomText(text = nickname, fontSize = 16.sp, font = Font(R.font.pretendard_semi_bold), color = Color.Black)
                CustomText(text = "국밥을 사랑하는 사람", fontSize = 14.sp, font = Font(R.font.pretendard_medium), color = Color.Gray)
            }
        }

        Spacer(modifier = Modifier.height(20.dp))

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 15.dp)
        ) {
            CustomText(text = stringResource(id = R.string.my_page_settings), fontSize = 20.sp, font = Font(R.font.pretendard_bold), color = Color.Black)
            Spacer(modifier = Modifier.height(20.dp))
            Row(
                modifier = Modifier
                    .clickable {
                        onClickBookmarkCafeteria()
                    }
            ) {
                Icon(painter = painterResource(id = R.drawable.ic_favorite), contentDescription = "")
                Spacer(modifier = Modifier.width(5.dp))
                CustomText(
                    text = stringResource(id = R.string.my_page_bookmark_cafeteria),
                    fontSize = 14.sp,
                    font = Font(R.font.pretendard_regular),
                    color = Color.Black
                )
            }
            Spacer(modifier = Modifier.height(20.dp))
            Row(
                modifier = Modifier
                    .clickable {
                        showDeleteDialog.value = true
                    }
            ) {
                Icon(painter = painterResource(id = R.drawable.ic_withdrawn), contentDescription = "")
                Spacer(modifier = Modifier.width(5.dp))
                CustomText(
                    text = stringResource(id = R.string.my_page_withdrawn),
                    fontSize = 14.sp,
                    font = Font(R.font.pretendard_regular),
                    color = Color.Black
                )
            }
        }
    }
}