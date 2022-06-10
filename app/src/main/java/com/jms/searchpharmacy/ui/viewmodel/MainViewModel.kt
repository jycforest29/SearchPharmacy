package com.jms.searchpharmacy.ui.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jms.a20220602_navermap.data.model.GeoInfo
import com.jms.searchpharmacy.data.model.stations.Line
import com.jms.searchpharmacy.data.model.stations.Line1
import com.jms.searchpharmacy.data.model.stations.Line2
import com.jms.searchpharmacy.repository.NaverMapSearchRepository
import kotlinx.coroutines.launch

class MainViewModel(
    private val naverMapSearchRepository: NaverMapSearchRepository
): ViewModel() {

    val lineList: List<Line> = listOf(Line1(), Line2())

    private val _searchResult = MutableLiveData<GeoInfo>()
    val searchResult: LiveData<GeoInfo> get() = _searchResult


    fun searchGeoInfo(query: String) = viewModelScope.launch {
        val response = naverMapSearchRepository.searchGeoInfo(query)

        if(response.isSuccessful) {
            response.body()?.let { body ->
                if(body.meta?.totalCount!! > 0) {
                    _searchResult.postValue(body)
                }
            }
        }
    }



}