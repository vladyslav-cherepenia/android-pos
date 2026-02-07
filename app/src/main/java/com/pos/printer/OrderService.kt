package com.pos.printer

import android.app.*
import android.content.Intent
import android.os.IBinder
import okhttp3.*
import org.json.JSONArray
import java.util.concurrent.Executors
import java.util.concurrent.TimeUnit

class OrderService : Service() {

    private val executor = Executors.newSingleThreadScheduledExecutor()

    override fun onCreate() {
        super.onCreate()
        startForeground(1, notification())

        executor.scheduleAtFixedRate({
            try {
                val orders = ApiClient.getOrders()
                orders.forEach {
                    EscPosPrinter.print(it)
                    ApiClient.markPrinted(it.getInt("order_id"))
                }
            } catch (_: Exception) {}
        }, 5, 10, TimeUnit.SECONDS)
    }

    override fun onBind(intent: Intent?): IBinder? = null

    private fun notification(): Notification {
        val channelId = "pos_channel"
        val channel = NotificationChannel(
            channelId,
            "POS Printer",
            NotificationManager.IMPORTANCE_LOW
        )
        getSystemService(NotificationManager::class.java)
            .createNotificationChannel(channel)

        return Notification.Builder(this, channelId)
            .setContentTitle("POS Printer")
            .setContentText("Очікування замовлень…")
            .setSmallIcon(android.R.drawable.stat_sys_print)
            .build()
    }
}
