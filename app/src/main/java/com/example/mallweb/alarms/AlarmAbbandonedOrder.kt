package com.example.mallweb.alarms

import android.annotation.SuppressLint
import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import java.util.*

object AlarmAbbandonedOrder {

    @SuppressLint("UnspecifiedImmutableFlag")
    fun setFirstAlarm(numOrder: Int, context: Context) {
        val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager
        val intent = Intent(context, OrderAlarmReceiver::class.java)
        intent.putExtra("numOrder", numOrder)
        val pendingIntent = PendingIntent.getBroadcast(context, 0, intent, 0)

        val calendar = Calendar.getInstance()
        calendar.timeInMillis = System.currentTimeMillis()
        calendar.add(Calendar.HOUR_OF_DAY, 24)

        alarmManager.set(AlarmManager.RTC_WAKEUP, calendar.timeInMillis, pendingIntent)
    }

    @SuppressLint("UnspecifiedImmutableFlag")
    fun setSecondAlarm(numOrder: Int, context: Context) {
        val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager
        val intent = Intent(context, OrderAlarmReceiver::class.java)
        intent.putExtra("numOrder", numOrder)
        val pendingIntent = PendingIntent.getBroadcast(context, 0, intent, 0)

        val calendar = Calendar.getInstance()
        calendar.timeInMillis = System.currentTimeMillis()
        calendar.add(Calendar.HOUR_OF_DAY, 24)

        alarmManager.set(AlarmManager.RTC_WAKEUP, calendar.timeInMillis, pendingIntent)
    }
}