package com.example.tastezip.ui.component

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.TextUnit

@Composable
fun CustomText(text: String, fontSize: TextUnit, font: Font, modifier: Modifier = Modifier, color: Color) {
    Text(
        text = text,
        style = TextStyle(
            fontSize = fontSize,
            fontFamily = FontFamily(font)
        ),
        color = color,
        modifier = modifier,
        textAlign = TextAlign.Center
    )
}