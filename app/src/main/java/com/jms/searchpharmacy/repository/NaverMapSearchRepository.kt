package com.jms.searchpharmacy.repository

import com.jms.a20220602_navermap.data.model.GeoInfo
import com.jms.searchpharmacy.data.api.RetrofitInstance.api
import retrofit2.Response

class NaverMapSearchRepository {
    suspend fun searchGeoInfo(
        query: String
    ): Response<GeoInfo> {
        return api.searchGeoInfo(query)
    }
}