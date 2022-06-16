package com.jms.searchpharmacy.util

object Constants {
    const val CLIENT_ID = "yo55hrtdpw"
    const val CLIENT_SECRET = "chzlZ6A18o7c3XcHBCQ0JS69FZMTr8uQhUYpcB1S"
    const val NAVERMAP_BASE_URL="https://naveropenapi.apigw.ntruss.com/"
    const val IP = "ded8-220-72-249-253.jp.ngrok.io"
    const val SERVER_BASE_URL = "http://$IP/api/pharmacy/"

    const val PERMISSION_REQUEST_CODE = 10

    val stationNamePattern = "^[가-힇|0-9]*역".toRegex()

    val dongNamePattern = "^[가-힇|0-9]*동".toRegex()
}