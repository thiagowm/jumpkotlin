package com.jump.test.model

import com.google.gson.annotations.SerializedName

data class User(
    var userId: Int,
    var uuid: String,
    var userCode: String,
    var name: String,
    var email: String,
    var phone: String,
    var document: String,
    var profileId: Int,
    var mainUser: Int,
//    var userRegistration: null,
    var establishments: Array<Int>,
//    "userRestrictions": null,
//    "appRestrictions": null,
//    "lastEstablishment": null,
//    "serviceOrderCount": 0,
//    "rpsCount": 0,
//    "status": 1,
    var accessToken: String
)
