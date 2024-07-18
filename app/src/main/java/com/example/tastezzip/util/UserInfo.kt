package com.example.tastezzip.util

import android.net.Uri

class UserInfo {
    var userId: Long? = null
    var authCode: String = ""
    var accessToken: String = ""
    var refreshToken: String = ""
    lateinit var profileImage: Uri
}