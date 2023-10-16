package com.jump.test.model

data class DataManual(
    var establishmentSettings: Establishment,
    var paymentMethods: Array<PaymentMethods>,
    var prices: Array<Prices>
)
