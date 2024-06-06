package com.example.tastezzip.model.request.auth.registration

import com.example.tastezzip.model.enums.UserType

data class RegistrationRequestVo(
    val bio: String = "",
    val config: Config = Config(),
    val nickname: String = "",
    val oauth: Oauth,
    val profileImage: String = "",
    val type: UserType = UserType.NORMAL
)