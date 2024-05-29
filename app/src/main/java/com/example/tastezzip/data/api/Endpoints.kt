package com.example.tastezzip.data.api

object Endpoints {

    private const val API = "/api"

    object Auth {
        private const val AUTH = "$API/auth"
        private const val OATH = "/oauth"
        const val GOOGLE = "$AUTH$OATH/login"
    }

    object Cafeteria {
        private const val CAFETERIA = "$API/cafeteria"
        const val LIST = "$CAFETERIA/list"
        const val CAFETERIAID = "$CAFETERIA/{id}"
        const val BOOKMARK = "$CAFETERIA/account"
        const val LIKE = "$CAFETERIA/like"
    }

    object Video {
        private const val VIDEO = "$API/video"
        const val ACCOUNT = "$VIDEO/account"
        const val FEED = "$VIDEO/feed"
    }

    object Youtube{
        private const val YOUTUBE = "$API/youtube"
        const val LIKE = "$YOUTUBE/like-cafeteria-update"
    }
}