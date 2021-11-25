package com.arsenii.my_choice.ui

import android.app.AlertDialog
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.arsenii.my_choice.MainActivity
import com.arsenii.my_choice.R


class RecyclerAdapter(organizationsList: MutableList<String>, organizationsTypesList: MutableList<String>, organizationsAddressesList: MutableList<String>) : RecyclerView.Adapter<RecyclerAdapter.ViewHolder>() {

    private val organizations: MutableList<String> = organizationsList
    private val organizationsTypes: MutableList<String> = organizationsTypesList
    private val organizationsAddresses: MutableList<String> = organizationsAddressesList

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.card_all, parent, false)
        return ViewHolder(v)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.itemTitle.text = organizations[position]
        holder.itemType.text = organizationsTypes[position]
        holder.itemImage.setImageResource(R.drawable.ic_launcher_background)
    }

    override fun getItemCount(): Int {
        return organizations.size
    }

    inner class ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){
        var itemImage: ImageView
        var itemTitle: TextView
        var itemType: TextView

        init {
            itemImage = itemView.findViewById(R.id.item_image)
            itemTitle = itemView.findViewById(R.id.item_title)
            itemType = itemView.findViewById(R.id.item_type)

            itemView.setOnClickListener{
                val position: Int = adapterPosition
                val alertDialog = AlertDialog.Builder(itemView.context)

                alertDialog.apply {
                    setIcon(R.drawable.ic_launcher_background)
                    setTitle("Кружок \"${organizations[position]}\"")
                    setMessage("Деятелность: ${organizationsTypes[position]}\nАдрес: ${organizationsAddresses[position]}")
                    setPositiveButton("Закрыть") { dialog, _ ->
                        dialog.cancel()
                    }
                }.create().show()
            }
        }
    }
}