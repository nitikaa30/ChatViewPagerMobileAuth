package com.example.viewpager.fragments.mobileAuth

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.example.viewpager.R
import com.example.viewpager.databinding.FragmentMobileAuthBinding
import com.example.viewpager.model.User
import com.google.firebase.FirebaseException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.PhoneAuthCredential
import com.google.firebase.auth.PhoneAuthOptions
import com.google.firebase.auth.PhoneAuthProvider
import java.util.concurrent.TimeUnit

class MobileAuthFragment : Fragment() {
    private lateinit var binding: FragmentMobileAuthBinding
    private lateinit var auth: FirebaseAuth

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding= FragmentMobileAuthBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        auth = FirebaseAuth.getInstance()
        binding.sendVerificationCodeButton.setOnClickListener {
            val cc = binding.countyCodePicker.selectedCountryCodeWithPlus
            val phoneNumber=binding.phoneNumber.text.toString()
            Log.d("Auth", "onViewCreated: $cc$phoneNumber")
            val options = PhoneAuthOptions.newBuilder(auth)
                .setPhoneNumber(cc.plus(phoneNumber))
                .setTimeout(60L, TimeUnit.SECONDS)
                .setActivity(requireActivity())
                .setCallbacks(callbacks)
                .build()
            PhoneAuthProvider.verifyPhoneNumber(options)

        }
    }
    private val callbacks = object : PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
        override fun onVerificationCompleted(credential: PhoneAuthCredential) {
            // Sign up the user with the credential
           // signUpWithCredential(credential)
        }

        override fun onVerificationFailed(e: FirebaseException) {
            Toast.makeText(context,"Wrong Phone number",Toast.LENGTH_SHORT).show()
            // Handle the exception
            // ...
        }

        override fun onCodeSent(
            verificationId: String,
            token: PhoneAuthProvider.ForceResendingToken
        ) {
            super.onCodeSent(verificationId, token)
            navigateToOtpAutoFill(verificationId)
            // Save the verification ID and token for later use
            // ...
        }
    }
    private fun signUpWithCredential(credential: PhoneAuthCredential) {
        val phoneNumber =binding.phoneNumber.text.toString()
        val user = User(phoneNumber)

        // Save the user data to your database or backend server here
        // ...

        // Sign the user in to your app
        auth.signInWithCredential(credential)
            .addOnCompleteListener(requireActivity()) { task ->
                if (task.isSuccessful) {
                    Toast.makeText(context,"Signed in successfully",Toast.LENGTH_LONG).show()
                    // User is signed in
                    // ...
                } else {

                    Log.d("Error", task.result.toString())
                    // Sign in failed
                    // ...
                }
            }
    }
    private fun navigateToOtpAutoFill(verificationId: String) {
        val otpAutoFillFragment = OtpFill()

        // Pass the verification ID to the OtpAutoFillFragment
        val args = Bundle()
        args.putString("verificationId", verificationId)
        otpAutoFillFragment.arguments = args

        // Use a FragmentManager to replace the current fragment with the OtpAutoFillFragment
        val fragmentManager = requireActivity().supportFragmentManager
        val fragmentTransaction = fragmentManager.beginTransaction()
        fragmentTransaction.replace(R.id.fragment_container, otpAutoFillFragment)
        fragmentTransaction.addToBackStack(null)
        fragmentTransaction.commit()
    }




}