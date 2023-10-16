package com.jump.test.model

data class Prices(
    var establishmentId: Int,
    var typePrice: String,
    var covenant: Int,
    var invisible: Int,
    var major: Int,
    var tolerance: Int,
    var maximumPeriod: Int,
    var maximumValue: String,
    var items: Array<Items>
)

//var establishmentId: Int,
//var typePrice: String,
//var covenant: Int,
//var invisible: Int,
//var major: Int,
//var tolerance: Int,
//var maximumPeriod: Int,
//var maximumValue: String,
//var items: Array<Items>