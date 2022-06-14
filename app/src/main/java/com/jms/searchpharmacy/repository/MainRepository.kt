package com.jms.searchpharmacy.repository

import com.jms.a20220602_navermap.data.model.GeoInfo
import com.jms.searchpharmacy.data.api.RetrofitInstance.naverMapApi
import com.jms.searchpharmacy.data.api.RetrofitInstance.serverApi
import com.jms.searchpharmacy.data.model.server.*

import retrofit2.Call
import retrofit2.Response

class MainRepository {
    suspend fun searchGeoInfo(
        query: String
    ): Response<GeoInfo> {
        return naverMapApi.searchGeoInfo(query)
    }
    fun fetchLines(): Call<List<Line>> {
        return serverApi.lines
    }

    fun fetchStations(line_name: String): Call<List<Station>> {
        return serverApi.getStationByLine(line_name)
    }

    fun fetchDongByStation(station_name: String): Call<List<Station>> {
        return serverApi.getDongByStation(station_name)
    }

    fun fetchPLs(dongName: String): Call<List<PharmacyLocation>> {
        return serverApi.getPLList(dongName)
    }


    fun fetchConvList(primaryKey: Int): Call<List<Convenience>> {
        return serverApi.getConvenienceList(primaryKey)
    }

    fun fetchHospList(primaryKey: Int): Call<List<Hospital>> {
        return serverApi.getHospitalList(primaryKey)
    }

    fun fetchPharList(primaryKey: Int): Call<List<Pharmacy>> {
        return serverApi.getPharmacyList(primaryKey)
    }
}