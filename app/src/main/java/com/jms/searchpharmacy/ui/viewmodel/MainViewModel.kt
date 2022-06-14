package com.jms.searchpharmacy.ui.viewmodel

import android.accounts.NetworkErrorException
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jms.a20220602_navermap.data.model.GeoInfo
import com.jms.searchpharmacy.data.model.server.Line
import com.jms.searchpharmacy.data.model.server.PharmacyLocation
import com.jms.searchpharmacy.data.model.server.Station
import retrofit2.Callback

import com.jms.searchpharmacy.repository.MainRepository
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Response

class MainViewModel(
    private val mainRepository: MainRepository
): ViewModel() {



    private val _searchResult = MutableLiveData<GeoInfo>()
    val searchResult: LiveData<GeoInfo> get() = _searchResult


    fun searchGeoInfo(query: String) = viewModelScope.launch {
        val response = mainRepository.searchGeoInfo(query)

        if(response.isSuccessful) {
            response.body()?.let { body ->
                if(body.meta?.totalCount!! > 0) {
                    _searchResult.postValue(body)
                }
            }
        }
    }

    private val _fetchedLines = MutableLiveData<List<Line>>()
    val fetchedLines: LiveData<List<Line>> get() = _fetchedLines

    fun fetchLines() = viewModelScope.launch {
        val call = mainRepository.fetchLines()

        call.enqueue(object : Callback<List<Line>> {
            override fun onResponse(
                call: Call<List<Line>>,
                response: Response<List<Line>>
            ) {
                response.body()?.let {
                    _fetchedLines.postValue(it)
                }
            }

            override fun onFailure(call: Call<List<Line>>, t: Throwable) {
                Log.d("TAG","List<Line> Callback.onFailure called")
            }
        })
    }

    private val _fetchedPLs = MutableLiveData<List<PharmacyLocation>>()
    val fetchedPLs: LiveData<List<PharmacyLocation>> get() = _fetchedPLs

    fun fetchPLs(dongName: String) = viewModelScope.launch {
        val call = mainRepository.fetchPLs(dongName)

        call.enqueue(object: Callback<List<PharmacyLocation>>{
            override fun onResponse(
                call: Call<List<PharmacyLocation>>,
                response: Response<List<PharmacyLocation>>
            ) {
                response.body()?.let{
                    _fetchedPLs.postValue(it)
                    for(i in it.indices) {
                        Log.d("TAG","받아온정보2: ${it[i].load_address}")
                        Log.d("TAG","받아온정보3: ${it[i].doctor_per_pharmacy}")
                    }
                }
            }

            override fun onFailure(call: Call<List<PharmacyLocation>>, t: Throwable) {
                Log.d("TAG","List<Line> Callback.onFailure called")
            }

        })

    }

//    fun fetchPLs(dongName: String) = viewModelScope.launch {
//        val response = mainRepository.fetchPLs(dongName)
//
//        if(response.isSuccessful) {
//            response.body()?.let {
//                for(i in it.indices) {
//                        Log.d("TAG","받아온정보2: ${it[i].conveniencecount}")
//                        Log.d("TAG","받아온정보3: ${it[i].loadaddress}")
//                    }
//            }
//        }
//    }


}