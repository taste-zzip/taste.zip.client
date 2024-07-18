package com.example.tastezzip

import com.example.tastezzip.application.LoadingManager
import com.example.tastezzip.ui.base.BaseViewModel
import javax.inject.Inject

class MainViewModel @Inject constructor(
    loadingManager: LoadingManager
): BaseViewModel(loadingManager) {

}