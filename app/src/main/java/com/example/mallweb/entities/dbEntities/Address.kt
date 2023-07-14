package com.example.mallweb.entities.dbEntities

data class Address(
    var idClient: Int = 0,
    var street: String = "",
    var number: String = "",
    var floor: String = "",
    var door: String = "",
    var postalCode: String = "",
    var province: String = "",
    var locality: String = "",
)
