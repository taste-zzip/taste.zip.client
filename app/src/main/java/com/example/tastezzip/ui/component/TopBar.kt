package com.example.tastezzip.ui.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Divider
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.tastezzip.R

@Composable
fun TopBar(
    imageButtonSourceId: Int?,
    onClick: () -> Unit,
    text: String
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(70.dp)
            .background(colorResource(id = R.color.background))
    ) {
        Column(
            modifier = Modifier.fillMaxHeight(),
            verticalArrangement = Arrangement.SpaceEvenly
        ) {
            Row(
                modifier = Modifier.fillMaxWidth().padding(horizontal = 15.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                if (imageButtonSourceId != null) {
                    IconButton(
                        onClick = onClick
                    ) {

                        Image(
                            painter = painterResource(id = imageButtonSourceId),
                            contentDescription = ""
                        )
                    }
                }

                CustomText(
                    text = text,
                    fontSize = 22.sp,
                    font = Font(R.font.pretendard_medium),
                    color = Color.Black
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