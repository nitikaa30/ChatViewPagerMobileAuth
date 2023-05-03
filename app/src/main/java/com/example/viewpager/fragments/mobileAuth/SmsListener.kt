package com.example.viewpager.fragments.mobileAuth

interface SmsListener {
    fun onMessageReceived(otp: String)
}