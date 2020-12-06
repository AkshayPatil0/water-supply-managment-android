package com.watersystem.main

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.watersystem.client.R
import kotlinx.android.synthetic.main.activity_main.*

class ActivityMain : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        if(MyPreferences(this).getUserName()!=""){
            startActivity(Intent(this, ActivityMenu::class.java))
            finish()
        }else {
            btn_login.setOnClickListener {
                startActivity(Intent(this, ActivityLogin::class.java))
            }

            btn_register.setOnClickListener {
                startActivity(Intent(this, ActivityRegister::class.java))
            }
        }

    }
}
