package com.example.mallweb.entities.dbEntities


data class Product (
    var id: Int = 0,
    var codFab: String = "",
    var name: String = "",
    var idCategory: Int = 0,
    var idBrand: Int = 0,
    var stock: Int = 0,
    var price: Double = 0.0,
    var image: Int = 0
    )
