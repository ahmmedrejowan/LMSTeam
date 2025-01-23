package com.rejowan.lmsteamprofile

import android.app.Application
import android.content.Intent
import android.os.Handler
import android.os.Looper
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.ProcessLifecycleOwner
import com.rejowan.lmsteamprofile.di.mainModule
import com.rejowan.lmsteamprofile.ui.modules.onboarding.Splash
import com.rejowan.lmsteamprofile.utils.SessionManager
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import org.koin.core.logger.Level

class MyApplication : Application() {

    private val sessionManager by lazy {
        SessionManager(this)
    }

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
                        if (sessionManager.isLoggedIn()) {
                            sessionManager.saveLastActiveTime()
                        }
                    }

                    Lifecycle.Event.ON_START -> {
                        if (sessionManager.isLoggedIn() && sessionManager.checkInactivity()) {
                            moveToSplashScreen()
                        }
                    }

                    else -> {
                        // Handle other lifecycle events if needed
                    }
                }
            }

        })

    }


    private fun moveToSplashScreen() {
        Handler(Looper.getMainLooper()).postDelayed({
            startActivity(
                Intent(this, Splash::class.java).addFlags(Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK)
            )
        }, 100)
    }

}

