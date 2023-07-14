package com.example.mallweb.db

import com.example.mallweb.entities.dbEntities.Payment

object ArrayPayments {

    val arrayPayments = arrayOf(
        Payment(1, "Efectivo"),
        Payment(2, "Transferencia"),
        Payment(3, "Mercado Pago"),
        Payment(4, "Mobbex - Tarjeta de Credito"),
        Payment(5, "Mobbex - Tarjeta de Debito")
    )


}