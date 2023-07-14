package com.example.mallweb.html

import android.content.Context
import com.example.mallweb.db.DbMallweb
import com.example.mallweb.entities.dbEntities.Client

object ProductsTable {

    fun generarTablaProductos(context: Context, client: Client): String {
        val dbMallweb = DbMallweb(context)
        val dollar = "$"
        val tablaHtml = StringBuilder()
        val productos = dbMallweb.queryForProductsOnCart(client.id)

        tablaHtml.append("<table>")
        tablaHtml.append("<tr>")
        tablaHtml.append("<th>Código</th>")
        tablaHtml.append("<th>Producto</th>")
        tablaHtml.append("<th>Valor</th>")
        tablaHtml.append("</tr>")

        for (producto in productos) {
            with(dbMallweb.queryForProduct(producto.idProduct)) {
                tablaHtml.append("<tr>")
                tablaHtml.append("<td>${codFab}</td>")
                tablaHtml.append("<td>${name} x${producto.quantity}</td>")
                tablaHtml.append("<td>U${dollar}S ${String.format("%.2f", price * producto.quantity)}</td>")
                tablaHtml.append("</tr>")
            }
        }

        tablaHtml.append("</table>")

        return tablaHtml.toString()
    }

    fun generarTablaProductosForAbbandonedOrders(context: Context, numOrder: Int): String {
        val dbMallweb = DbMallweb(context)
        val tablaHtml = StringBuilder()
        val productos = dbMallweb.queryForDetails(numOrder)

        tablaHtml.append("<table>")
        tablaHtml.append("<tr>")
        tablaHtml.append("<th>Código</th>")
        tablaHtml.append("<th>Producto</th>")
        tablaHtml.append("</tr>")

        for (producto in productos) {
            with(producto) {
                tablaHtml.append("<tr>")
                tablaHtml.append("<td>${idProduct}</td>")
                tablaHtml.append("<td>${nameProduct} x${cant}</td>")
                tablaHtml.append("</tr>")
            }
        }

        tablaHtml.append("</table>")

        return tablaHtml.toString()
    }
}