package com.jump.test.presentation

import com.jump.test.data.APICallback
import com.jump.test.model.Data
import com.jump.test.view.VehicleList

class VehicleListPresenter(
    private val view: VehicleList,
) : APICallback {

    override fun onSuccess(response: Data?) {
        TODO("Not yet implemented")
    }

    override fun onError(response: String) {
        TODO("Not yet implemented")
    }

    override fun onComplete() {
        TODO("Not yet implemented")
    }


}