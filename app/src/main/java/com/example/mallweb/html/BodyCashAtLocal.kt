package com.example.mallweb.html

import android.content.Context
import com.example.mallweb.entities.dbEntities.Address
import com.example.mallweb.entities.dbEntities.Client
import com.example.mallweb.entities.dbEntities.Order
import com.example.mallweb.html.ProductsTable.generarTablaProductos

object BodyCashAtLocal {

    fun body(client: Client, address: Address, order: Order, context: Context): String {
        val dollar = "$"
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
                "    <h2>Confirmación de pedido</h2>\n" +
                "    <p>Hola ${client.name}, Gracias por tu pedido.</p>\n" +
                "    \n" +
                "    <h3>Información de contacto</h3>\n" +
                "    <p>Nombre: ${client.name}</p>\n" +
                "    <p>Email: ${client.email}</p>\n" +
                "    <p>Teléfono: ${client.codArea}-${client.numCelular}</p>\n" +
                "    <p>Dirección: ${address.street} ${address.number}</p>\n" +
                "    <p>Piso: ${address.floor}</p>\n" +
                "    <p>Puerta: ${address.door}</p>\n" +
                "    <p>Localidad: ${address.locality}</p>\n" +
                "    <p>Provincia: ${address.province}</p>\n" +
                "    <p>Codigo Postal: ${address.postalCode}</p>\n" +
                "    <p>Forma de pago: Efectivo</p>\n" +
                "    <p>Forma de envío: Retiro por Local</p>\n" +
                "    \n" +
                "    <h3>Detalles del pedido</h3>\n" +
                "    <p>ID de pedido: #${order.numOrder}</p>\n" +
                "    ${generarTablaProductos(context, client)}" +
                "    <p>Total: U${dollar}S ${String.format("%.2f", order.total - ((order.total/100)*5))}</p>\n" +
                "    \n" +
                "    <h3>IMPORTANTE:</h3>\n" +
                "    <p>Visita nuestro sitio web: <a href=\"http://www.mallweb.com.ar\">www.mallweb.com.ar</a></p>\n" +
                "    <p>Teléfono: 1147022760</p>\n" +
                "    <p>Dirección: Vidal 3854, Núñez, CABA.</p>\n" +
                "    <p>Horario: Lunes a Viernes de 09:00 a 18:00</p>\n" +
                "  </div>\n" +
                "\n" +
                "</body>\n" +
                "</html>\n"
    }




}