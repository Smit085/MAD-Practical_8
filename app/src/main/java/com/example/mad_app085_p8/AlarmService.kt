package com.example.mad_app085_p8

import android.app.Service
import android.content.Intent
import android.media.MediaPlayer
import android.os.IBinder

abstract class AlarmService: Service() {
    lateinit var mp: MediaPlayer

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {

        if(intent != null){
            mp = MediaPlayer.create(this, R.raw.alarm)
            mp?.start()
        }

        return START_STICKY
    }

    override fun onDestroy() {
        mp?.stop()
        super.onDestroy()
    }
}
