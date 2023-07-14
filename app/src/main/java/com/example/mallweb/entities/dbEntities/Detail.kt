package com.example.mallweb.entities.dbEntities

data class Detail (
    var numOrder: Int = 0,
    var idProduct: Int = 0,
    var nameProduct: String = "",
    var cant: Int = 0,
    var price: Double = 0.0
)
