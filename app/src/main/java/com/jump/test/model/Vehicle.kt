package com.jump.test.model

data class Vehicle(
    var vehicle_id: Int,
    var vehicle: String,
    var plate: String,
    var model: String,
    var color: String,

    var type_payment: String,
    var courtyard: Int,
    var time_enter: String,
    var time_out: String,
    var total: String,

)
