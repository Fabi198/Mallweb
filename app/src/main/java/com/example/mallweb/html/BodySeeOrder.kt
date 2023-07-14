package com.example.mallweb.html

import android.content.Context
import com.example.mallweb.db.DbMallweb
import com.example.mallweb.entities.dbEntities.Order
import com.example.mallweb.html.ProductsTable.generarTablaProductosForAbbandonedOrders

object BodySeeOrder {

    fun body(order: Order, context: Context): String {

        val dbMallweb = DbMallweb(context)
        var shipping = ""

        if (order.shipping == "si") {
            shipping = "Envío a domicilio"
        } else if (order.shipping == "no") {
            shipping = "Retiro en local"
        }

        return "<!DOCTYPE html>\n" +
                "<html>\n" +
                "<head>\n" +
                "  <title>Two Cards</title>\n" +
                "  <style>\n" +
                "    .card {\n" +
                "      background-color: #f1f1f1;\n" +
                "      border: 1px solid #ddd;\n" +
                "      border-radius: 4px;\n" +
                "      padding: 10px;\n" +
                "      margin-bottom: 10px;\n" +
                "    }\n" +
                "    .santander-rio {\n" +
                "      color: #447446;\n" +
                "    }\n" +
                "  </style>\n" +
                "</head>\n" +
                "<body>\n" +
                "  <div class=\"card\">\n" +
                "    <h2>Mi pedido</h2>\n" +
                "    \n" +
                "    <p>Estado del pedido: ${order.state}</p>\n" +
                "    <p>ID de pedido: #${order.numOrder}</p>\n" +
                "    <p>Fecha: ${order.date}</p>\n" +
                "    <p>Forma de pago: ${dbMallweb.queryForPayment(order.payMethod).name}</p>\n" +
                "    <p>Forma de envío: $shipping</p>\n" +
                "    <p>Productos:</p>\n" +
                "    ${generarTablaProductosForAbbandonedOrders(context, order.numOrder)}" +
                "  </div>\n" +
                "\n" +
                "</body>\n" +
                "</html>\n"
    }
}