package com.example.mad_app085_p8

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent

class AlarmBroadcastReceiver: BroadcastReceiver() {
    override fun onReceive(context: Context?, intent: Intent?) {
        val action = intent?.getStringExtra("AlarmService")
        if(action == "Play"){
            val intentservice = Intent(context,AlarmService::class.java)
            intentservice.putExtra("Service",intent.getStringExtra())
        }
        else if(action == "Stop"){

        }

    }

}