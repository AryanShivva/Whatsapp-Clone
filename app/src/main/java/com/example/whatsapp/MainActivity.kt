package com.example.whatsapp

import com.example.whatsapp.activity.NumberActivity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import com.example.whatsapp.adapter.ViewPagerAdapter
import com.example.whatsapp.databinding.ActivityMainBinding
import com.example.whatsapp.ui.CallFragment
import com.example.whatsapp.ui.ChatFragment
import com.example.whatsapp.ui.StatusFragment
import com.google.firebase.auth.FirebaseAuth

class MainActivity : AppCompatActivity() {

    private var binding : ActivityMainBinding? = null
    private lateinit var auth: FirebaseAuth
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(R.layout.activity_main)
        setContentView(binding!!.root)

        val fragmentArrayList = ArrayList<Fragment>()

        fragmentArrayList.add(ChatFragment())
        fragmentArrayList.add(CallFragment())
        fragmentArrayList.add(StatusFragment())

        auth = FirebaseAuth.getInstance()
        if(auth.currentUser==null)
        {
            startActivity(Intent(this, NumberActivity::class.java))
            finish()
        }


        val adapter = ViewPagerAdapter(this,supportFragmentManager,fragmentArrayList)

        binding!!.viewPager.adapter = adapter

        binding!!.tabsd.setupWithViewPager(binding!!.viewPager)

    }
}