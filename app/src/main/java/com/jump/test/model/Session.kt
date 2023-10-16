package com.jump.test.model

data class Session(
    var sessionId: Int,
    var establishmentId: Int,
    var startDateTime: String,
    var endDateTime: String,
    var userId: Int,
    var active: Int,
    var versionName: String,
    var status: Int,
    var created_at: String,
    var updated_at: String
)
