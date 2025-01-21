package com.rejowan.lmsteamprofile

import android.app.Application
import android.util.Log
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.ProcessLifecycleOwner
import com.rejowan.lmsteamprofile.di.mainModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import org.koin.core.logger.Level

class MyApplication : Application() {


    override fun onCreate() {
        super.onCreate()


        startKoin {
            androidLogger(Level.ERROR)
            androidContext(this@MyApplication)
            modules(listOf(mainModule))
        }


        ProcessLifecycleOwner.get().lifecycle.addObserver(object : LifecycleEventObserver {
            override fun onStateChanged(source: LifecycleOwner, event: Lifecycle.Event) {
                when (event) {
                    Lifecycle.Event.ON_STOP -> {
                        // App goes to background
                        Log.e("MyApplication", "App moved to background")
                        saveLastActiveTime()
                    }

                    Lifecycle.Event.ON_START -> {
                        // App comes to foreground
                        Log.e("MyApplication", "App moved to foreground")
                        checkInactivity()
                    }

                    else -> {
                        // Handle other lifecycle events if needed
                    }
                }
            }

        })

    }

    private fun saveLastActiveTime() {
        val sharedPref = getSharedPreferences("MySharedPref", MODE_PRIVATE)
        val editor = sharedPref.edit()
        editor.putLong("lastActiveTime", System.currentTimeMillis())
        editor.apply()

    }

    private fun checkInactivity() {
        val sharedPref = getSharedPreferences("MySharedPref", MODE_PRIVATE)
        val lastActiveTime = sharedPref.getLong("lastActiveTime", 0)
        val currentTime = System.currentTimeMillis()
        val inactivityDuration = currentTime - lastActiveTime
        // app inactive for more than 10 seconds
        if (inactivityDuration > 1 * 10 * 1000) {
            Log.e("MyApplication", "App was inactive for more than 5 minutes")
            moveToSplashScreen()
        }
    }

    private fun moveToSplashScreen() {
        // Move to splash screen
//        Handler(Looper.getMainLooper()).postDelayed({
//            startActivity(Intent(this, Splash::class.java).
//            addFlags(Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK))
//        }, 300)
    }

}

