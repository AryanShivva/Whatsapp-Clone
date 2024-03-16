package com.example.whatsapp.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.whatsapp.MainActivity
import com.example.whatsapp.R
import com.example.whatsapp.databinding.ActivityMainBinding
import com.example.whatsapp.databinding.ActivityNumberBinding
import com.google.firebase.auth.FirebaseAuth

class NumberActivity : AppCompatActivity() {


    private lateinit var binding: ActivityNumberBinding
    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= ActivityNumberBinding.inflate(layoutInflater)
        setContentView(binding.root)

        auth = FirebaseAuth.getInstance()

        if(auth.currentUser!=null)
        {
            startActivity(Intent(this,MainActivity::class.java))
            finish()
        }

        binding.button.setOnClickListener{
            if(binding.phoneNuber.text!!.isEmpty()){
                Toast.makeText(this,"please enter your number",Toast.LENGTH_SHORT).show()

            }
            else
            {
                var intent = Intent(this, OTPActivity::class.java)
                intent.putExtra("number",binding.phoneNuber.text!!.toString())
                startActivity(intent)
            }
        }



    }
}