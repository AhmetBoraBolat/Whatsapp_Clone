package com.ahmetborabolat.whatsapp_clone.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.ahmetborabolat.whatsapp_clone.MainActivity
import com.ahmetborabolat.whatsapp_clone.R
import com.ahmetborabolat.whatsapp_clone.databinding.ActivityMainBinding
import com.ahmetborabolat.whatsapp_clone.databinding.ActivityNumberBinding
import com.google.firebase.auth.FirebaseAuth

class NumberActivity : AppCompatActivity() {

    private lateinit var binding : ActivityNumberBinding
    private lateinit var auth : FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityNumberBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        auth = FirebaseAuth.getInstance()

        if(auth.currentUser != null){
            startActivity(Intent(this,MainActivity::class.java))
            finish()
        }

        binding.button.setOnClickListener {
            if (binding.PhoneNumber.text!!.isEmpty()) {
                Toast.makeText(this, "Please enter your Number!!", Toast.LENGTH_SHORT).show()
            }
            else{
                var intent = Intent(this,OTPActivity::class.java)
                intent.putExtra("number",binding.PhoneNumber.text!!)
                startActivity(intent)
            }
        }
    }
}