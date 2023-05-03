package com.example.viewpager.fragments.mobileAuth

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.util.Log
import com.google.android.gms.auth.api.phone.SmsRetriever
import com.google.android.gms.common.api.CommonStatusCodes
import com.google.android.gms.common.api.Status

class SmsBroadcastReceiver(private val smsListener: SmsListener) : BroadcastReceiver() {
    override fun onReceive(context: Context?, intent: Intent?) {
//        Log.d("Retrieve",smsRetrievalCallback.toString())
        if (SmsRetriever.SMS_RETRIEVED_ACTION == intent?.action) {
            val data = intent.extras
            Log.d("Broadcaster",data.toString())
            if (data != null) {
                val status = data.get(SmsRetriever.EXTRA_STATUS) as Status?
                Log.d("Status",status.toString())
                var timedOut = false
                var otpCode: String? = null
                if (status != null) {
                    when (status.statusCode) {
                        CommonStatusCodes.SUCCESS -> {
                            // Extract the OTP from the message
                            val message = data.get(SmsRetriever.EXTRA_SMS_MESSAGE) as String
                            otpCode = extractOtpFromMessage(message)
                            smsListener.onMessageReceived(otpCode)
                            Log.d("SMSsuccess",message)
                            Log.d("OTP3",otpCode)
                        }
                        CommonStatusCodes.TIMEOUT -> {
                            Log.d("SMSnooo",CommonStatusCodes.getStatusCodeString(1))
                            timedOut = true
                        }
                    }
                }
//                smsRetrievalCallback.invoke(timedOut, otpCode)
            }
        }
    }

    private fun extractOtpFromMessage(message: String): String {
        val regex = "(\\\\d{6}) is your verification code for viewpager-c647c.firebaseapp.com".toRegex()
        val matchResult = regex.find(message)
        return matchResult?.groupValues?.getOrNull(1) ?: ""
    }
}

