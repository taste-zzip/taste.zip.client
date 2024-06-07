package com.example.tastezzip.ui.screens.mypage

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.tastezzip.R
import com.example.tastezzip.ui.component.TopBar
import com.example.tastezzip.ui.screens.recommend.RecommendItem
import com.example.tastezzip.ui.theme.MainActivityTheme
import com.example.tastezzip.ui.viewmodel.BookmarkCafeteriaViewModel

@Composable
fun BookmarkCafeteria(
    viewModel: BookmarkCafeteriaViewModel = hiltViewModel(),
    popBackStack: () -> Unit
) {
    val bookmarkList by viewModel.bookmarkList.collectAsState()

    Box(
        modifier = Modifier.fillMaxSize()
    ) {
        Column {
            TopBar(
                imageButtonSourceId = R.drawable.ic_arrow_left,
                onClick = {
                    popBackStack()
                },
                text = stringResource(id = R.string.recommend_title)
            )

            LazyColumn(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(10.dp)
            ) {
                items(bookmarkList) {
                    RecommendItem(item = it, type = "myPage")
                }
            }
        }
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun PreviewBookmarkCafeteria() {
    MainActivityTheme {
        BookmarkCafeteria(popBackStack = {})
    }
}