package com.example.whatsapp.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.whatsapp.R
import com.example.whatsapp.databinding.ActivityOtpactivityBinding
import com.example.whatsapp.databinding.ActivityWelcomeBinding

class welcomeActivity : AppCompatActivity() {

    private lateinit var binding:ActivityWelcomeBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding=ActivityWelcomeBinding.inflate(layoutInflater)
        setContentView(binding.root)



        binding.button2.setOnClickListener {

            val intent = Intent(this,NumberActivity::class.java)
            startActivity(intent)
        }
    }
}