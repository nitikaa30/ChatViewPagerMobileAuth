package com.example.viewpager.fragments.mobileAuth

import android.content.IntentFilter
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.runtime.*
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.runtime.remember
import com.example.viewpager.R
import com.example.viewpager.databinding.FragmentOtpFillBinding
import com.example.viewpager.fragments.chat.ChatMessage
import com.google.android.gms.auth.api.phone.SmsRetriever
import com.google.firebase.FirebaseException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.PhoneAuthCredential
import com.google.firebase.auth.PhoneAuthProvider
import com.mukeshsolanki.OTP_VIEW_TYPE_BORDER
import com.mukeshsolanki.OtpView

class OtpFill : Fragment(), SmsListener {
    private lateinit var binding: FragmentOtpFillBinding
    private lateinit var auth: FirebaseAuth
    private var verificationId: String? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding= FragmentOtpFillBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        verificationId = arguments?.getString("verificationId")
        Log.d("ID",verificationId.toString())

        auth = FirebaseAuth.getInstance()

        val client = SmsRetriever.getClient(requireActivity())
        val task = client.startSmsRetriever()
        val receiver = SmsBroadcastReceiver(this)
        val intentFilter = IntentFilter(SmsRetriever.SMS_RETRIEVED_ACTION)
        context?.registerReceiver(receiver, intentFilter)
        task.addOnSuccessListener {
            Log.d("Success",intentFilter.toString())
            Toast.makeText(context,"Received SMS",Toast.LENGTH_LONG).show()
            // Successfully started the SMS retriever
        }
        task.addOnFailureListener {
            Toast.makeText(context,"Failed to start SMS retriever",Toast.LENGTH_LONG).show()
        }

        binding.verifyButton.setOnClickListener {
            val otp = binding.otpView.text.toString()
            val credential = PhoneAuthProvider.getCredential(verificationId.toString(), otp)
            signInWithCredential(credential)
        }
    }
    private fun signInWithCredential(credential: PhoneAuthCredential) {
        auth.signInWithCredential(credential)
            .addOnCompleteListener(requireActivity()) { task ->
                if (task.isSuccessful) {
                    callbacks
                    Toast.makeText(context,"Signed in successfully", Toast.LENGTH_LONG).show()
                    val fragmentManager = requireActivity().supportFragmentManager
                    val fragmentTransaction = fragmentManager.beginTransaction()
                    fragmentTransaction.replace(R.id.fragment_container, ChatMessage())
                    fragmentTransaction.addToBackStack(null)
                    fragmentTransaction.commit()
                    // Sign in successful
                    val user = auth.currentUser

                    // ...
                } else {
                    Toast.makeText(context,"OTP incorrect",Toast.LENGTH_LONG).show()
                    Log.d("Err",task.result.toString())
                    // Sign in failed
                    // ...
                }
            }
    }
    private val callbacks = object : PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
        override fun onVerificationCompleted(credential: PhoneAuthCredential) {
            // Verification successful, sign in with the credential
            Log.d("Credential",credential.toString())
            signInWithCredential(credential)
        }

        override fun onVerificationFailed(exception: FirebaseException) {
            Log.d("VerifyFail",exception.message.toString())
            Toast.makeText(context,exception.message,Toast.LENGTH_LONG).show()
            // Verification failed
            // ...
        }

        override fun onCodeSent(
            verificationId: String,
            token: PhoneAuthProvider.ForceResendingToken
        ) {
            // Code sent to the device, save the verification ID and update UI
           val verify = verificationId
            // ...
        }
    }

    override fun onMessageReceived(otp: String) {
        binding.otpView.setText(otp)
        Log.d("OTP4",otp)
        val credential = PhoneAuthProvider.getCredential(verificationId.toString(), otp)
        signInWithCredential(credential)
    }


}