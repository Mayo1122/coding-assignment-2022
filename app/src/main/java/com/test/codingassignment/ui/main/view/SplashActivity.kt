package com.test.codingassignment.ui.main.view

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import com.test.codingassignment.R
import com.test.codingassignment.ui.main.view.account.SignUpActivity

class SplashActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        Handler().postDelayed(
            {
                val intent = Intent(this, SignUpActivity::class.java)
                startActivity(intent)
                finish()
            }, 2000)
    }
}