package com.rejowan.lmsteamprofile.utils

import android.content.Context

class SessionManager(private val context: Context) {


    companion object {
        private const val MY_SHARED_PREF = "MySharedPref"
        private const val LAST_ACTIVE_TIME = "lastActiveTime"
        private const val IS_LOGGED_IN = "isLoggedIn"
    }

    private val sharedPref = context.getSharedPreferences(MY_SHARED_PREF, Context.MODE_PRIVATE)
    private val editor = sharedPref.edit()


    fun saveLastActiveTime() {
        editor.putLong(LAST_ACTIVE_TIME, System.currentTimeMillis())
        editor.apply()
    }

    fun checkInactivity(): Boolean {
        val currentTime = System.currentTimeMillis()
        val lastActiveTime = sharedPref.getLong(LAST_ACTIVE_TIME, 0)
        if (lastActiveTime == 0L) return false
        val diff = currentTime - lastActiveTime
        return diff > 2 * 60 * 1000
    }

    fun setLoggedIn(isLoggedIn: Boolean) {
        editor.putBoolean(IS_LOGGED_IN, isLoggedIn)
        editor.apply()
    }

    fun isLoggedIn(): Boolean {
        return sharedPref.getBoolean(IS_LOGGED_IN, false)
    }


}