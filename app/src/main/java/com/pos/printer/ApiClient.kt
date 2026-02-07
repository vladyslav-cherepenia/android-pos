package com.pos.printer

import okhttp3.*
import org.json.JSONArray
import org.json.JSONObject

object ApiClient {

    private val client = OkHttpClient()

    private const val BASE_URL = "https://romsonapp.com/pi/api"

    fun getOrders(): List<JSONObject> {
        val req = Request.Builder()
            .url("$BASE_URL/orders.php?printed=0")
            .build()

        val res = client.newCall(req).execute()
        val arr = JSONArray(res.body!!.string())

        return List(arr.length()) { arr.getJSONObject(it) }
    }

    fun markPrinted(orderId: Int) {
        val body = FormBody.Builder()
            .add("order_id", orderId.toString())
            .build()

        val req = Request.Builder()
            .url("$BASE_URL/printed.php")
            .post(body)
            .build()

        client.newCall(req).execute()
    }
}
