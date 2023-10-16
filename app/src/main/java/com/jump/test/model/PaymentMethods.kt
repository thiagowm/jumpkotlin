package com.jump.test.model

data class PaymentMethods(
    var establishmentPaymentMethodId: Int,
    var paymentMethodName: String,
    var primitivePaymentMethodId: Int,
    var receivingDays: Int,
    var receivingFee: String,
    var accountId: Int
)

//@Entity
//data class PaymentMethods(
//@PrimaryKey(autoGenerate = true) val id: Int = 0,
//@ColumnInfo(name = "establishment_payment_method_id") val establishmentPaymentMethodId: Int,
//@ColumnInfo(name = "payment_method_name") val paymentMethodName: String,
//@ColumnInfo(name = "primitive_payment_method_id") val primitivePaymentMethodId: Int,
//@ColumnInfo(name = "receiving_days") val receivingDays: Int,
//@ColumnInfo(name = "receiving_fee") val receivingFee: String,
//@ColumnInfo(name = "account_id") val accountId: Int)