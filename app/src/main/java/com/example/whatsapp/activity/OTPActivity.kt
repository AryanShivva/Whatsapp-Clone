package com.example.whatsapp.activity

import android.app.AlertDialog
import android.app.Dialog
import android.content.Intent
import android.net.Uri.Builder
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.whatsapp.R
import com.example.whatsapp.databinding.ActivityNumberBinding
import com.example.whatsapp.databinding.ActivityOtpactivityBinding
import com.google.firebase.FirebaseException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.PhoneAuthCredential
import com.google.firebase.auth.PhoneAuthOptions
import com.google.firebase.auth.PhoneAuthProvider
import java.util.concurrent.TimeUnit

class OTPActivity : AppCompatActivity() {

    private lateinit var binding: ActivityOtpactivityBinding
    private lateinit var auth: FirebaseAuth
    private lateinit var verificationid: String
    private lateinit var dialog: AlertDialog

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding=ActivityOtpactivityBinding.inflate(layoutInflater)
        setContentView(binding.root)


        auth = FirebaseAuth.getInstance()

        val builder = AlertDialog.Builder(this)

        builder.setMessage("please wait")
        builder.setTitle("loading")
        builder.setCancelable(false)

        dialog = builder.create()
        dialog.show()

        val phonenumber = "+91" + intent.getStringExtra("number")

        val options = PhoneAuthOptions.newBuilder(auth)
            .setPhoneNumber(phonenumber)
            .setTimeout(60L,TimeUnit.SECONDS)
            .setActivity(this)
            .setCallbacks(object : PhoneAuthProvider.OnVerificationStateChangedCallbacks(){
                override fun onVerificationCompleted(p0: PhoneAuthCredential) {

                }

                override fun onVerificationFailed(p0: FirebaseException) {
                    dialog.dismiss()
                    Toast.makeText(this@OTPActivity,"p+${p0}",Toast.LENGTH_LONG).show()


                }

                override fun onCodeSent(p0: String, p1: PhoneAuthProvider.ForceResendingToken) {
                    super.onCodeSent(p0, p1)

                    dialog.dismiss()
                    verificationid = p0
                }


            }).build()

        PhoneAuthProvider.verifyPhoneNumber(options)

            binding.nextbutton.setOnClickListener{

                if(binding.otp.text!!.isEmpty())
                {
                    Toast.makeText(this,"please enter otp",Toast.LENGTH_SHORT).show()
                }
                else{
                    dialog.show()

                    val crudential = PhoneAuthProvider.getCredential(verificationid,binding.otp.text!!.toString())

                    auth.signInWithCredential(crudential)
                        .addOnCompleteListener{
                            if(it.isSuccessful){
                                dialog.dismiss()
                                startActivity(Intent(this, ProfileActivity::class.java))
                                finish()
                            }
                            else{
                                dialog.dismiss()
                                Toast.makeText(this,"error ${it.exception}",Toast.LENGTH_SHORT).show()
                            }
                        }

                }


            }













    }
}