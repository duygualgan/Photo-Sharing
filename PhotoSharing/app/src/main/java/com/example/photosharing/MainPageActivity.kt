package com.example.photosharing

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.photosharing.databinding.ActivityMainPageBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query

class MainPageActivity : AppCompatActivity() {

    lateinit var binding: ActivityMainPageBinding
    private lateinit var auth : FirebaseAuth
    private lateinit var database : FirebaseFirestore
    private lateinit var recyclerViewAdapter : ReceylerAdapterPage

    var postListesi = ArrayList<Post>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainPageBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView (view)

        auth = FirebaseAuth.getInstance()
        database = FirebaseFirestore.getInstance()

        verileriAl()

        var layoutManager = LinearLayoutManager(this)
        binding.recyclerView.layoutManager = layoutManager

        recyclerViewAdapter = ReceylerAdapterPage(postListesi)
        binding.recyclerView.adapter = recyclerViewAdapter

    }


    fun verileriAl(){
        database.collection("Post").orderBy("tarih", Query.Direction.DESCENDING).addSnapshotListener { snapshot, exception ->

            if (exception != null ){
                Toast.makeText(this, exception.localizedMessage , Toast.LENGTH_LONG).show()
            }else{
                if (snapshot != null){
                    if (snapshot.isEmpty == false){

                        val documents = snapshot.documents

                        postListesi.clear()

                        for (documents in documents){
                            val kullaniciEmail = documents.get("kullaniciemail") as String
                            val kullaniciYorumu = documents.get("kullaniciyorum") as String
                            val gorselUrl = documents.get("gorselurl") as String


                            val indirilenPost = Post(kullaniciEmail, kullaniciYorumu, gorselUrl)
                            postListesi.add(indirilenPost)


                        }

                        recyclerViewAdapter.notifyDataSetChanged()

                    }
                }
            }
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {

        val menuInflater = menuInflater
        menuInflater.inflate(R.menu.option_menu , menu)

        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {

            if (item.itemId == R.id.foto_paylas){
                //foto paylaşma sayfasına git
                val intent = Intent(this, SharingActivity::class.java)
                startActivity(intent)

            }else if(item.itemId == R.id.cikis_yap){

                auth.signOut()
                val intent = Intent(this, MainActivity::class.java)
                startActivity(intent)
                finish()

            }

        return super.onOptionsItemSelected(item)
    }

}