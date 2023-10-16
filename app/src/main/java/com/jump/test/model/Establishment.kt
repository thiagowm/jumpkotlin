package com.jump.test.model

data class Establishment(
    var establishmentId: Int,
    var cashReserve: String,
    var recurrentClientEntryOption: Int,
    var withdrawal: Int,
    var serviceOnly: Int,
    var print: Int,
//    var printNote: null,
    var manualCovenant: Int,
    var manualTime: Int,
    var accountId: Int,
    var prePaidExit: Int,
    var requireReceiptCovantTypePrice: Int,
    var requireReceiptExpense: Int,
    var pathLogo: String,
    var reserveActivation: Int
//    "preReserveToleranceTime": null,
//    "rpsAutomaticGenerate": null
)