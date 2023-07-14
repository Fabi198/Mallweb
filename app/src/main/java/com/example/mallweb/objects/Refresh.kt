package com.example.mallweb.objects

import android.content.Context
import android.content.Intent
import androidx.fragment.app.FragmentActivity
import com.example.mallweb.MallWeb
import com.example.mallweb.R

object Refresh {

    fun refresh(activity: FragmentActivity, context: Context) {
        activity.startActivity(Intent(context, MallWeb::class.java).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP))
        activity.overridePendingTransition(R.anim.fade_in, R.anim.fade_out)
    }

}