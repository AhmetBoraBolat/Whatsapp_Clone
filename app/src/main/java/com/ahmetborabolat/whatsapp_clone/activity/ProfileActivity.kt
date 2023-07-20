package com.ahmetborabolat.whatsapp_clone.activity

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import com.ahmetborabolat.whatsapp_clone.MainActivity
import com.ahmetborabolat.whatsapp_clone.R
import com.ahmetborabolat.whatsapp_clone.databinding.ActivityNumberBinding
import com.ahmetborabolat.whatsapp_clone.databinding.ActivityProfileBinding
import com.ahmetborabolat.whatsapp_clone.model.UserModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.FirebaseStorage
import java.util.Date

class ProfileActivity : AppCompatActivity() {

    private lateinit var binding : ActivityProfileBinding
    private lateinit var auth : FirebaseAuth
    private lateinit var database : FirebaseDatabase
    private lateinit var storage : FirebaseStorage
    private lateinit var selectedImg : Uri
    private lateinit var dialog: AlertDialog.Builder
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityProfileBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        dialog = AlertDialog.Builder(this)
            .setMessage("Updating Profile...")
            .setCancelable(false)

        database = FirebaseDatabase.getInstance()
        storage = FirebaseStorage.getInstance()
        auth = FirebaseAuth.getInstance()


        binding.userImage.setOnClickListener{
            val intent = Intent()
            intent.action = Intent.ACTION_GET_CONTENT
            intent.type = "image/*"
            startActivityForResult(intent,1)
        }

        binding.continueBtn.setOnClickListener{
            if(binding.userName.text!!.isEmpty()){
                Toast.makeText(this, "Please enter your name", Toast.LENGTH_SHORT).show()
            }
            else if(selectedImg == null){
                Toast.makeText(this, "Please select your image", Toast.LENGTH_SHORT).show()
            }else uploadData()
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if(data !=null){
            if(data.data != null){
                selectedImg = data.data!!

                binding.userImage.setImageURI(selectedImg)
            }
        }
    }

    private fun uploadData(){
        val reference = storage.reference.child("Profile").child(Date().time.toString())
        reference.putFile(selectedImg).addOnCompleteListener{
            if(it.isSuccessful){
                reference.downloadUrl.addOnSuccessListener {task ->
                    uploadInfo(task.toString())
                }
            }
        }
    }

    private fun uploadInfo(imgUrl: String) {
        val user = UserModel(auth.uid.toString(),binding.userName.text.toString(),auth.currentUser!!.phoneNumber.toString(),imgUrl)

        database.reference.child("users")
            .child(auth.uid.toString())
            .setValue(user)
            .addOnSuccessListener {
                Toast.makeText(this, "Data inserted", Toast.LENGTH_SHORT).show()
                startActivity(Intent(this,MainActivity::class.java))
                finish()
            }
    }
}