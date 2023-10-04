package com.example.mad_app085_p8

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent

class AlarmBroadcastReceiver: BroadcastReceiver() {
    override fun onReceive(context: Context, intent: Intent) {
        val action = intent.getStringExtra("com.example.mad_app085_p8.AlarmService")
        if(action == "Start" || action == "Stop"){
            val intentservice = Intent(context,AlarmService::class.java)
            intentservice.putExtra("com.example.mad_app085_p8.AlarmService",intent.getStringExtra("com.example.mad_app085_p8.AlarmService"))

            if(action == "Start"){
                context.startService(intentservice)
            }
            else if(action == "Stop"){
                context.stopService(intentservice)
            }
        }
    }

}