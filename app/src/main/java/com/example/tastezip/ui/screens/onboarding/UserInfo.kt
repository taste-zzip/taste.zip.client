package com.example.tastezip.ui.screens

import android.content.Context
import android.widget.Toast
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CheckboxDefaults
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.example.tastezip.R
import com.example.tastezip.navigation.NavRoutes
import com.example.tastezip.ui.theme.MainActivityTheme
import com.example.tastezip.ui.viewmodel.UserInfoViewModel

@Composable
fun UserInfo(navController: NavHostController, userInfoViewModel: UserInfoViewModel) {
    val context = LocalContext.current

    var nickname by remember{ mutableStateOf("") }
    var oneLineInfo by remember { mutableStateOf("") }

    var firstChecked by remember { mutableStateOf(false) }
    var secondChecked by remember { mutableStateOf(false) }
    var thirdChecked by remember { mutableStateOf(false) }

    val onNicknameChanged = { newName: String ->
        nickname = newName
    }

    val onOneLineChanged = { newInfo: String ->
        oneLineInfo = newInfo
    }

    val firstCheckedChange = { checked: Boolean ->
        firstChecked = checked
    }

    val secondCheckedChange = { checked: Boolean ->
        secondChecked = checked
    }

    val thirdCheckedChange = { checked: Boolean ->
        thirdChecked = checked
    }

    val btnCompleteListener = {

        if (firstChecked && secondChecked && nickname.isNotEmpty() && oneLineInfo.isNotEmpty()) {
            userInfoViewModel.saveUserInfo(nickname, oneLineInfo)

            Toast.makeText(context, "저장되었습니다.\n 닉네임: $nickname, 한 줄 소개: $oneLineInfo", Toast.LENGTH_SHORT).show()

            navController.navigate(NavRoutes.NaverMapScreen.route) {
                popUpTo(NavRoutes.UserInfo.route) {
                    inclusive = true
                }
            }
        } else if (firstChecked && secondChecked && nickname.isEmpty() || oneLineInfo.isEmpty()) {
            Toast.makeText(context, "필수 항목을 채워주세요.", Toast.LENGTH_SHORT).show()
        } else if (!firstChecked || !secondChecked) {
            Toast.makeText(context, "필수 항목에 동의해주세요.", Toast.LENGTH_SHORT).show()
        }
    }

    Box(
        modifier = Modifier
            .fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Column(
            modifier = Modifier
                .padding(start = 10.dp, end = 10.dp)
                .align(Alignment.TopStart)
        ) {

            Spacer(modifier = Modifier.size(40.dp))

            Column {
                CustomText(
                    text = "회원 정보를 입력해주세요",
                    fontSize = 24.sp,
                    font = Font(R.font.pretendard_bold),
                    color = Color.Black,
                    modifier = Modifier
                        .padding(horizontal = 10.dp)
                )

                Spacer(Modifier.size(30.dp))

                Column(
                    modifier = Modifier
                        .padding(horizontal = 10.dp),
                    verticalArrangement = Arrangement.SpaceAround
                ) {
                    CustomText(
                        text = "닉네임",
                        fontSize = 16.sp,
                        font = Font(R.font.pretendard_bold),
                        color = Color.Gray
                    )
                    CustomUnderLinedTextField(
                        textState = nickname,
                        onTextChange = onNicknameChanged
                    )
                }

                Spacer(Modifier.size(40.dp))

                Column(
                    modifier = Modifier.padding(horizontal = 10.dp),
                    verticalArrangement = Arrangement.SpaceEvenly
                ) {
                    CustomText(
                        text = "한 줄 소개",
                        fontSize = 16.sp,
                        font = Font(R.font.pretendard_bold),
                        color = Color.Gray
                    )
                    CustomUnderLinedTextField(
                        textState = oneLineInfo,
                        onTextChange = onOneLineChanged
                    )
                }

                Spacer(modifier = Modifier.size(20.dp))

                CheckBoxText(isChecked = firstChecked, "(필수) 이용 약관에 동의합니다.", firstCheckedChange)
                CheckBoxText(isChecked = secondChecked, "(필수) 위치 기반 서비스 이용에 동의합니다.", secondCheckedChange)
                CheckBoxText(isChecked = thirdChecked, "(선택) 마케팅 정보 수신에 동의합니다.", thirdCheckedChange)

                Spacer(modifier = Modifier.weight(1f))

                Box(
                    modifier = Modifier
                        .padding(bottom = 10.dp)
                ) {
                    Button(
                        onClick = { btnCompleteListener() },
                        modifier = Modifier
                            .fillMaxWidth(),
                        shape = RoundedCornerShape(
                            10.dp
                        ),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = colorResource(id = R.color.main_color)
                        )
                    ) {
                        Text("완료")
                    }
                }
            }
        }
    }
}

@Composable
fun CustomUnderLinedTextField(
    textState: String,
    onTextChange: (String) -> Unit
) {

    BasicTextField(
        value = textState,
        onValueChange = { onTextChange(it) },
        singleLine = true,
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 10.dp),
        textStyle = TextStyle(
            color = Color.Black,
            fontFamily = FontFamily(Font(R.font.pretendard_semi_bold)),
            fontSize = 14.sp
        ),
        decorationBox = {

            it()    // 키보드 커서를 표시해주는 역할

            Canvas(modifier = Modifier
                .fillMaxWidth()
                .padding(top = 25.dp)) {
                val strokeWidth = 1.dp.toPx()
                val y = size.height - strokeWidth / 2
                drawLine(
                    Color.Black,  // 밑줄 색상
                    Offset(0f, y),  // 시작점
                    Offset(size.width, y),  // 끝점
                    strokeWidth  // 두께
                )
            }
        }
    )
}

@Composable
fun CheckBoxText(isChecked: Boolean, text: String, onCheckedChange: (Boolean) -> Unit) {

    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .fillMaxWidth()
    ) {
        Checkbox(
            checked = isChecked,
            onCheckedChange = { onCheckedChange(it) },
            colors = CheckboxDefaults.colors(
                checkedColor = colorResource(id = R.color.main_color),
                uncheckedColor = colorResource(id = R.color.main_color)
            )
        )

        CustomText(text = text, fontSize = 14.sp, font = Font(R.font.pretendard_light), color = Color.Gray)
    }
}

@Preview
@Composable
fun UserInfoPreview() {
    MainActivityTheme {
//        UserInfo(hiltViewModel<UserInfoViewModel>(), context = )
    }
}