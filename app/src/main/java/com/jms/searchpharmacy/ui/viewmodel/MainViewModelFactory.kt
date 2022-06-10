package com.jms.searchpharmacy.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.jms.searchpharmacy.repository.NaverMapSearchRepository
import java.lang.IllegalArgumentException

class MainViewModelFactory(
    private val naverMapSearchRepository: NaverMapSearchRepository
): ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if(modelClass.isAssignableFrom(MainViewModel::class.java)) {
                return MainViewModel(naverMapSearchRepository) as T
        }
            throw IllegalArgumentException("ViewModel class Not Found")
    }

}