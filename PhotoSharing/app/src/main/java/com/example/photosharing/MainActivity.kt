package com.example.photosharing

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.WindowManager
import android.widget.Toast
import com.example.photosharing.databinding.ActivityMainBinding
import com.google.firebase.auth.FirebaseAuth

class MainActivity : AppCompatActivity() {

    lateinit var binding: ActivityMainBinding
    private lateinit var auth : FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView (view)

        auth = FirebaseAuth.getInstance()

        val guncelkullanici = auth.currentUser
        if (guncelkullanici != null ){
            val intent = Intent(this , MainPageActivity::class.java)
            startActivity(intent)
            finish()
        }

        supportActionBar?.hide()

    }


    fun login(view : View){

        auth.signInWithEmailAndPassword(binding.etEmail.text.toString(),binding.etPassword.text.toString()).addOnCompleteListener { task ->
            if (task.isSuccessful){

                val guncelkullanici = auth.currentUser?.email.toString()
                Toast.makeText(this,"Hoşgeldin : ${guncelkullanici}",Toast.LENGTH_LONG).show()

                val intent = Intent(this,MainPageActivity::class.java)
                startActivity(intent)
                finish()
            }

        }

    }

    fun register(view : View){
        val email = binding.etEmail.text.toString()
        val sifre = binding.etPassword.text.toString()

        auth.createUserWithEmailAndPassword(email, sifre).addOnCompleteListener { task ->
            //asenkron

            if (task.isSuccessful){
                // Diger aktivity ye geç
                val intent = Intent(this,MainPageActivity::class.java)
                startActivity(intent)
                finish()

            }
        }.addOnFailureListener { exception ->
            Toast.makeText(applicationContext, exception.localizedMessage,Toast.LENGTH_LONG).show()
        }

    }


}