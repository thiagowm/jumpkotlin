package com.jump.test.data

import android.content.Intent
import com.jump.test.model.Data

interface APICallback {

//    fun onSuccess(response: List<String>)
    fun onSuccess(response: Data?)

    fun onError(response: String)

    fun onComplete()
}