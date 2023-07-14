package com.example.mallweb.entities.dbEntities

data class Order (
    var idClient: Int = 0,
    var numOrder: Int = 0,
    var date: String = "",
    var total: Double = 0.0,
    var state: String = "",
    var shipping: String = "",
    var payMethod: Int = 0
        )
