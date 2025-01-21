package com.rejowan.lmsteamprofile.utils.auth

interface AuthCallback {
    fun onSuccess()
    fun onFailure()
    fun onError(errorCode: Int, errorMessage: String)
    fun onNoBiometricSupport()
    fun onNoBiometricEnrolled()
}
