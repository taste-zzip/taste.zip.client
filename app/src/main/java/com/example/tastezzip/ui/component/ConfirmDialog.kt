package com.example.tastezzip.ui.component

import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.sp
import com.example.tastezzip.R

@Composable
fun ConfirmDialog(
    title: String,
    content: String,
    onClickBtnConfirm: () -> Unit,
    onClickBtnDismiss: () -> Unit
) {
    AlertDialog(
        onDismissRequest = { onClickBtnDismiss() },
        confirmButton = {
            Button(
                onClick = {
                    onClickBtnConfirm()
                    onClickBtnDismiss()
                },
                colors = ButtonDefaults.buttonColors(containerColor = Color.Transparent)
            ) {
                CustomText(text = "확인", fontSize = 14.sp, font = Font(R.font.pretendard_regular), color = colorResource(id = R.color.main_color))
            }
        },
        dismissButton = { 
            Button(
                onClick = { onClickBtnDismiss() },
                colors = ButtonDefaults.buttonColors(containerColor = Color.Transparent)
            ) {
                CustomText(text = "취소", fontSize = 14.sp, font = Font(R.font.pretendard_regular), color = colorResource(id = R.color.main_color))
            }
        },
        title = {
            CustomText(
                text = title,
                fontSize = 20.sp,
                font = Font(R.font.pretendard_bold),
                color = Color.Black
            )
        },
        text = {
            if (content.isNotEmpty()) {
                CustomText(
                    text = content,
                    fontSize = 14.sp,
                    font = Font(R.font.pretendard_regular),
                    color = Color.Black,
                    textAlign = TextAlign.Start
                )
            }
        }
    )
}