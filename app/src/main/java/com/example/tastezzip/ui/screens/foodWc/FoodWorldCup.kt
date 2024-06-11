package com.example.tastezzip.ui.screens.foodWc

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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.tastezzip.R
import com.example.tastezzip.ui.component.CustomText
import com.example.tastezzip.ui.theme.MainActivityTheme
import com.example.tastezzip.ui.viewmodel.FoodWorldCupViewModel

@Composable
fun FoodWorldCup(
    onClickBtnStartWorldCup: () -> Unit
) {
    val viewModel: FoodWorldCupViewModel = hiltViewModel()

    FoodWcInitScreen(onClickBtnStartWorldCup)
}

@Composable
fun FoodWcInitScreen(
    onClickBtnStartWorldCup: () -> Unit
) {
    Box(
        modifier = Modifier
            .fillMaxSize(),
        contentAlignment = Alignment.TopCenter
    ) {
        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(modifier = Modifier.height(200.dp))
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.ic_food),
                    contentDescription = "",
                    tint = Color.Unspecified,
                    modifier = Modifier.size(45.dp)
                )
                Spacer(modifier = Modifier.width(20.dp))
                CustomText(text = stringResource(R.string.food_wc), fontSize = 25.sp, font = Font(R.font.pretendard_bold), color = Color.Black)
            }
            Spacer(modifier = Modifier.height(20.dp))
            CustomText(text = stringResource(id = R.string.food_wc_sub_title), fontSize = 15.sp, font = Font(R.font.pretendard_regular), color = Color.Black)
            Spacer(modifier = Modifier.weight(1f))
            Button(
                modifier = Modifier.fillMaxWidth().padding(horizontal = 30.dp),
                onClick = { onClickBtnStartWorldCup() },
                shape = RoundedCornerShape(
                    10.dp
                ),
                colors = ButtonDefaults.buttonColors(
                    containerColor = colorResource(id = R.color.food_wc)
                )
            ) {
                CustomText(text = stringResource(id = R.string.food_wc_start), fontSize = 17.sp, font = Font(R.font.pretendard_semi_bold), color = Color.White)
            }
            Spacer(modifier = Modifier.height(10.dp))
        }
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun PreviewFoodWc() {
    MainActivityTheme {
        FoodWcInitScreen({})
    }
}