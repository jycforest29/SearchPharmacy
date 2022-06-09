package com.jms.searchpharmacy.ui.viewmodel

import androidx.lifecycle.ViewModel
import com.jms.searchpharmacy.data.model.stations.Line
import com.jms.searchpharmacy.data.model.stations.Line1
import com.jms.searchpharmacy.data.model.stations.Line2

class MainViewModel: ViewModel() {

    val lineList: List<Line> = listOf(Line1(), Line2())

}