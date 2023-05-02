package com.example.viewpager.fragments.mobileAuth

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import com.google.android.gms.auth.api.phone.SmsRetriever
import com.google.android.gms.common.api.CommonStatusCodes
import com.google.android.gms.common.api.Status

class SmsBroadcastReceiver(private val smsRetrievalCallback: (timedOut: Boolean, otpCode: String?) -> Unit) : BroadcastReceiver() {
    override fun onReceive(context: Context?, intent: Intent?) {
        if (SmsRetriever.SMS_RETRIEVED_ACTION == intent?.action) {
            val data = intent.extras
            if (data != null) {
                val status = data.get(SmsRetriever.EXTRA_STATUS) as Status?
                var timedOut = false
                var otpCode: String? = null
                if (status != null) {
                    when (status.statusCode) {
                        CommonStatusCodes.SUCCESS -> {
                            val appMessage = data.get(SmsRetriever.EXTRA_SMS_MESSAGE) as String
                            otpCode = appMessage
                        }
                        CommonStatusCodes.TIMEOUT -> {
                            timedOut = true
                        }
                    }
                }
                smsRetrievalCallback.invoke(timedOut, otpCode)
            }
        }
    }
}

