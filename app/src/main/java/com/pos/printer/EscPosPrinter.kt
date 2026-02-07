package com.pos.printer

import org.json.JSONObject
import java.io.OutputStream
import java.net.Socket

object EscPosPrinter {

    private const val IP = "192.168.66.245"
    private const val PORT = 9100

    fun print(order: JSONObject) {
        val socket = Socket(IP, PORT)
        val out: OutputStream = socket.getOutputStream()

        fun w(s: String) = out.write(s.toByteArray(Charsets.UTF_8))

        w("\u001B@") // init
        w("ЗАМОВЛЕННЯ #${order.getInt("order_id")}\n")
        w("--------------------------\n")
        w("${order.getString("first_name")} ${order.getString("last_name")}\n")
        w("Тел: ${order.getString("phone")}\n")

        val items = order.getJSONArray("items")
        for (i in 0 until items.length()) {
            val it = items.getJSONObject(i)
            w("${it.getString("name")} x${it.getInt("qty")} ${it.getDouble("price")}\n")
        }

        w("--------------------------\n")
        w("РАЗОМ: ${order.getDouble("total")} ${order.getString("currency")}\n\n\n")
        w("\u001DV\u0000") // cut

        out.flush()
        out.close()
        socket.close()
    }
}
