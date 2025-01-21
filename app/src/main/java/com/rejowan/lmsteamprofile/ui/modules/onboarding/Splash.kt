package com.rejowan.lmsteamprofile.ui.modules.onboarding

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.View
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.tabs.TabLayout
import com.rejowan.lmsteamprofile.R
import com.rejowan.lmsteamprofile.databinding.ActivitySplashBinding
import com.rejowan.lmsteamprofile.ui.modules.home.Home
import com.rejowan.lmsteamprofile.utils.auth.AuthCallback
import com.rejowan.lmsteamprofile.utils.auth.AuthManager

class Splash : AppCompatActivity() {


    private val binding by lazy {
        ActivitySplashBinding.inflate(layoutInflater)
    }

    private lateinit var authManager: AuthManager
    private val handler = Handler(Looper.getMainLooper())

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(binding.root)

        initLayout()

        listeners()

    }


    private fun initLayout() {
        binding.textLogin.visibility = View.VISIBLE
        binding.biometricLayout.visibility = View.GONE
    }

    private fun reverseInitLayout() {
        binding.textLogin.visibility = View.GONE
        binding.biometricLayout.visibility = View.VISIBLE

        setupTabLayout()
    }

    private fun listeners() {
        binding.textLogin.setOnClickListener {
            reverseInitLayout()
        }

        authManager = AuthManager(this, object : AuthCallback {
            override fun onSuccess() {
                runOnUiThread {
                    Toast.makeText(this@Splash, "Authentication Success", Toast.LENGTH_SHORT).show()
                    startActivity(Intent(this@Splash, Home::class.java))
                    finish()
                }
            }

            override fun onFailure() {
                runOnUiThread {
                    Toast.makeText(this@Splash, "Authentication Failed", Toast.LENGTH_SHORT).show()
                }

            }

            override fun onError(errorCode: Int, errorMessage: String) {
                runOnUiThread {
                    Toast.makeText(this@Splash, errorMessage, Toast.LENGTH_SHORT).show()
                }

            }

            override fun onNoBiometricSupport() {
                runOnUiThread {
                    Toast.makeText(this@Splash, "Biometric not supported", Toast.LENGTH_SHORT).show()
                }

            }

            override fun onNoBiometricEnrolled() {
                runOnUiThread {
                    Toast.makeText(this@Splash, "Biometric not enrolled", Toast.LENGTH_SHORT).show()
                }

            }

        })


    }

    private fun setupTabLayout() {

        val fingerTab = binding.tabLayout.getTabAt(0)
        val faceTab = binding.tabLayout.getTabAt(1)
        fingerTab?.select()
        setupFingerTab()

        binding.tabLayout.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab?) {
                when (tab) {
                    fingerTab -> {
                        setupFingerTab()
                    }

                    faceTab -> {
                        setupFaceTab()
                    }
                }
            }

            override fun onTabUnselected(tab: TabLayout.Tab?) {
            }

            override fun onTabReselected(tab: TabLayout.Tab?) {
            }
        })

    }

    private fun setupFingerTab() {
        authManager.configurePrompt(true)
        handler.removeCallbacksAndMessages(null)
        binding.imgFingerprint.visibility = View.VISIBLE
        binding.textCancelFace.visibility = View.GONE
        binding.textDescription.text = getString(R.string.scan_your_finger)

        // with a delay 1 sec, prompt the fingerprint dialog
        handler.postDelayed({
            authManager.authenticate()
        }, 1000)

        // imgFingerprint can also be clicked to prompt the fingerprint dialog
        binding.imgFingerprint.setOnClickListener {
            authManager.authenticate()
        }
    }

    private fun setupFaceTab() {
        authManager.configurePrompt(false)
        handler.removeCallbacksAndMessages(null)

        binding.imgFingerprint.visibility = View.GONE
        binding.textCancelFace.visibility = View.VISIBLE
        binding.textDescription.text = getString(R.string.use_face_recognition)

        // with a delay 1 sec, prompt the face dialog
        handler.postDelayed({
            authManager.authenticate()
        }, 1000)

        // textCancelFace can cancel the face dialog
        binding.textCancelFace.setOnClickListener {
            handler.removeCallbacksAndMessages(null)
        }
    }

}