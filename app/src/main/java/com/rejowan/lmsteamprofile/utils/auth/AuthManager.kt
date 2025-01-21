package com.rejowan.lmsteamprofile.utils.auth

import androidx.appcompat.app.AppCompatActivity
import androidx.biometric.BiometricManager
import androidx.biometric.BiometricPrompt
import androidx.biometric.BiometricPrompt.PromptInfo
import androidx.core.content.ContextCompat
import java.util.concurrent.Executor

class AuthManager(
    private val activity: AppCompatActivity, private val callback: AuthCallback
) {

    private val executor: Executor = ContextCompat.getMainExecutor(activity)
    private val biometricManager = BiometricManager.from(activity)
    private lateinit var biometricPrompt: BiometricPrompt
    private lateinit var promptInfo: PromptInfo

    private var isBiometricSupported: Boolean = true

    init {
        // Check biometric support and set the flag or trigger callbacks
        when (biometricManager.canAuthenticate(BiometricManager.Authenticators.BIOMETRIC_STRONG or BiometricManager.Authenticators.BIOMETRIC_WEAK)) {
            BiometricManager.BIOMETRIC_ERROR_NO_HARDWARE -> {
                callback.onNoBiometricSupport()
                isBiometricSupported = false
            }
            BiometricManager.BIOMETRIC_ERROR_HW_UNAVAILABLE -> {
                callback.onError(-1, "Biometric hardware is currently unavailable. Please try again later.")
                isBiometricSupported = false
            }
            BiometricManager.BIOMETRIC_ERROR_NONE_ENROLLED -> {
                callback.onNoBiometricEnrolled()
                isBiometricSupported = false
            }
            BiometricManager.BIOMETRIC_STATUS_UNKNOWN -> {
                callback.onError(-1, "Biometric status is unknown.")
                isBiometricSupported = false
            }
        }

        if (isBiometricSupported) {
            biometricPrompt = BiometricPrompt(
                activity,
                executor,
                object : BiometricPrompt.AuthenticationCallback() {
                    override fun onAuthenticationSucceeded(result: BiometricPrompt.AuthenticationResult) {
                        super.onAuthenticationSucceeded(result)
                        callback.onSuccess()
                    }

                    override fun onAuthenticationFailed() {
                        super.onAuthenticationFailed()
                        callback.onFailure()
                    }

                    override fun onAuthenticationError(errorCode: Int, errString: CharSequence) {
                        super.onAuthenticationError(errorCode, errString)
                        callback.onError(errorCode, errString.toString())
                    }
                }
            )
        }
    }

    fun configurePrompt(isStrongAuth: Boolean) {
        promptInfo = PromptInfo.Builder()
            .setTitle("Authentication")
            .setSubtitle(if (isStrongAuth) "Scan your finger" else "Use face recognition")
            .setDescription("Place your finger on the sensor")
            .setNegativeButtonText("Cancel")
            .setAllowedAuthenticators(if (isStrongAuth) BiometricManager.Authenticators.BIOMETRIC_STRONG else BiometricManager.Authenticators.BIOMETRIC_WEAK)
            .setConfirmationRequired(isStrongAuth)
            .build()
    }


    fun authenticate() {
        if (!isBiometricSupported) {
            callback.onError(-1, "Biometric authentication is not supported on this device.")
            return
        }

        if (::promptInfo.isInitialized) {
            biometricPrompt.authenticate(promptInfo)
        } else {
            callback.onError(-1, "Authentication prompt is not configured.")
        }
    }


}