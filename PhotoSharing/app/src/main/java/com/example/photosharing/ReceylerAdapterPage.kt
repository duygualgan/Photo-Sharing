package com.example.photosharing

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso

class ReceylerAdapterPage (val postList : ArrayList<Post>) : RecyclerView.Adapter<ReceylerAdapterPage.PotsHolder>() {

    class PotsHolder (itemView: View) : RecyclerView.ViewHolder(itemView){

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PotsHolder {
        val inflater = LayoutInflater.from(parent.context)
        val view = inflater.inflate(R.layout.recycler_row,parent,false)
        return PotsHolder(view)
    }

    override fun onBindViewHolder(holder: PotsHolder, position: Int) {
        holder.itemView.findViewById<TextView>(R.id.recycler_row_kullanici_email).text = postList[position].kullaniciEmail
        holder.itemView.findViewById<TextView>(R.id.recycler_row_yorum).text = postList[position].kullaniciYorum

        Picasso.get().load(postList[position].gorselUrl).into(holder.itemView.findViewById<ImageView>(R.id.recycler_row_imageview))
    }

    override fun getItemCount(): Int {

        return postList.size
    }

}