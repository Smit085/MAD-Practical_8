package com.example.mad_app085_p8

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.util.Log

class AlarmBroadcastReceiver: BroadcastReceiver() {
    override fun onReceive(context: Context, intent: Intent) {
        val action = intent.getStringExtra("BroadcastService")
        if(action == "Start"){
            Log.i("BroadcastService:","Start")
            Intent(context,AlarmService::class.java).putExtra("AlarmService","Play").also {  context.startService(it)}
        }
        else if(action == "Stop"){
            Log.i("BroadcastService:","Stop")
            Intent(context,AlarmService::class.java).putExtra("AlarmService","Stop").also {  context.stopService(it)}
        }
    }
}