package com.jms.searchpharmacy.data.model.stations

import kotlinx.parcelize.Parcelize


abstract class Line {
    abstract val num: Int
    abstract val color : String
    abstract val stationList : List<String>
    abstract val name : String
}