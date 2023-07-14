package com.example.mallweb.entities.dbEntities

data class Client (
    var id: Int = 0,
    var email: String = "",
    var name: String = "",
    var birthday: String = "",
    var codArea: String = "",
    var numCelular: String = "",
    var dni: String = "",
    var cuit: String = "",
    var wantABill: String = "",
    var ivaCondition: String = ""
        )
