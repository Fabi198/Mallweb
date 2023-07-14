package com.example.mallweb.objects

import android.content.Context
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.FragmentActivity
import com.example.mallweb.db.DbMallweb

object Session {

    fun sessionFromFragment(activity: FragmentActivity): Boolean {
        val prefs: SharedPreferences = activity.getSharedPreferences("MY PREF", AppCompatActivity.MODE_PRIVATE)
        val email = prefs.getString("email", null)
        val provider = prefs.getString("provider", null)
        return email != null && provider != null
    }

    fun getUserID(context: Context, activity: FragmentActivity): Int {
        val dbMallweb = DbMallweb(context)
        var id = 0
        val prefs: SharedPreferences = activity.getSharedPreferences("MY PREF", AppCompatActivity.MODE_PRIVATE)
        val email = prefs.getString("email", null)
        val client = email?.let { dbMallweb.queryForClient(it) }
        if (client != null) {
            id = client.id
        }
        return id
    }


}