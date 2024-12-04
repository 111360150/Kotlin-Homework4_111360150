package com.example.lab13

import android.app.Service
import android.content.Intent
import android.os.IBinder

class MyService : Service() {
    private var channel = ""
    private lateinit var thread: Thread

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        intent?.extras?.let {
            channel = it.getString("channel", "")
        }

        broadcastMessage(
            when (channel) {
                "music" -> "歡迎來到音樂頻道"
                "new" -> "歡迎來到新聞頻道"
                "sport" -> "歡迎來到體育頻道"
                "english" -> "歡迎來到英文頻道" // 英文頻道歡迎訊息
                else -> "頻道錯誤"
            }
        )

        if (::thread.isInitialized && thread.isAlive) thread.interrupt()

        thread = Thread {
            try {
                Thread.sleep(3000) // 延遲三秒
                broadcastMessage(
                    when (channel) {
                        "music" -> "即將播放本月TOP10音樂"
                        "new" -> "即將為您提供獨家新聞"
                        "sport" -> "即將播報本週NBA賽事"
                        "english" -> "即將播放英語會話" // 英文頻道內容
                        else -> "頻道錯誤"
                    }
                )
            } catch (e: InterruptedException) {
                e.printStackTrace()
            }
        }

        thread.start()
        return START_STICKY
    }

    override fun onBind(intent: Intent): IBinder? = null

    private fun broadcastMessage(msg: String) {
        sendBroadcast(Intent(channel).putExtra("msg", msg)) // 發送純訊息
    }
}