package com.almseit.shoplistclean.presentation

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.almseit.shoplistclean.R
import com.almseit.shoplistclean.domain.ShopItem

class ShopListAdapter: RecyclerView.Adapter<ShopListAdapter.ShopItemViewHolder>() {

    var shopList = listOf<ShopItem>()
    set(value) {
        field = value
        notifyDataSetChanged()
    }



    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ShopItemViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_shop_disabled,parent,false)
        return ShopItemViewHolder(view)
    }

    override fun onBindViewHolder(holder: ShopItemViewHolder, position: Int) {
        val shopItem = shopList[position]
        val status = if (shopItem.enabled){
            "Active"
        }else{
            "No Active"
        }

        holder.itemView.setOnLongClickListener {
            true
        }
        if (shopItem.enabled){
            holder.tvName.text = "${shopItem.name} $status"
            holder.tvCount.text = shopItem.count.toString()
            holder.tvName.setTextColor(ContextCompat.getColor(holder.itemView.context,android.R.color.holo_red_dark))
        }/*else{ // 1 метод решения бага
            holder.tvName.text = ""
            holder.tvCount.text = ""
            holder.tvName.setTextColor(ContextCompat.getColor(holder.itemView.context,android.R.color.white))
        }*/
    }

    // 2 метод решения бага
    override fun onViewRecycled(holder: ShopItemViewHolder) {
        super.onViewRecycled(holder)
        holder.tvName.text = ""
        holder.tvCount.text = ""
        holder.tvName.setTextColor(ContextCompat.getColor(holder.itemView.context,android.R.color.white))
    }

    override fun getItemCount() = shopList.size


    class ShopItemViewHolder(itemView:View): RecyclerView.ViewHolder(itemView){
        val tvName = itemView.findViewById<TextView>(R.id.tvName)
        val tvCount = itemView.findViewById<TextView>(R.id.tvCount)


    }


}