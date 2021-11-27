package com.arsenii.my_choice.ui

import android.app.AlertDialog
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.arsenii.my_choice.R


class RecyclerAdapter(organizationsList: MutableList<String>, organizationsAddressesList: MutableList<String>) : RecyclerView.Adapter<RecyclerAdapter.ViewHolder>() {

    private val organizations: MutableList<String> = organizationsList
    private val organizationsAddresses: MutableList<String> = organizationsAddressesList

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.card_all, parent, false)
        return ViewHolder(v)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.itemTitle.text = organizations[position]
        holder.itemImage.setImageResource(R.mipmap.ic_launcher)
    }

    override fun getItemCount(): Int {
        return organizations.size
    }

    inner class ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){
        var itemImage: ImageView = itemView.findViewById(R.id.item_image)
        var itemTitle: TextView = itemView.findViewById(R.id.item_title)

        init {

            itemView.setOnClickListener{
                val position: Int = adapterPosition
                val alertDialog = AlertDialog.Builder(itemView.context)

                alertDialog.apply {
                    setIcon(R.mipmap.ic_launcher)
                    setTitle("Кружок \"${organizations[position]}\"")
                    setMessage("Адрес: ${organizationsAddresses[position]}")
                    setPositiveButton("Закрыть") { dialog, _ ->
                        dialog.cancel()
                    }
                }.create().show()
            }
        }
    }
}