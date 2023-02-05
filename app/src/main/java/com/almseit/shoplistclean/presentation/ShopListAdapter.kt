package com.almseit.shoplistclean.presentation


import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.almseit.shoplistclean.R
import com.almseit.shoplistclean.databinding.ItemShopDisabledBinding
import com.almseit.shoplistclean.databinding.ItemShopEnabledBinding
import com.almseit.shoplistclean.domain.ShopItem

class ShopListAdapter: RecyclerView.Adapter<ShopListAdapter.ShopItemViewHolder>() {

    var count = 0

    var shopList = listOf<ShopItem>()
    set(value) {
        val callback = ShopListDiffCallback(shopList,value)
        val diffResult = DiffUtil.calculateDiff(callback)
        diffResult.dispatchUpdatesTo(this)
        field = value

    }

    var onShopItemLongClickListener: ((ShopItem) -> Unit)? = null
    var onShopItemClickListener: ((ShopItem) -> Unit)? = null



    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ShopItemViewHolder {

        val layout = when(viewType) {
           VIEW_TYPE_ENABLED -> R.layout.item_shop_enabled
           VIEW_TYPE_DISABLED -> R.layout.item_shop_disabled
           else -> throw RuntimeException("Unknown view type $viewType")
        }
        val binding = DataBindingUtil.inflate<ViewDataBinding>(
            LayoutInflater.from(parent.context),
            layout,
            parent,
            false)
        return ShopItemViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ShopItemViewHolder, position: Int) {
        val shopItem = shopList[position]
        val binding = holder.binding
        binding.root.setOnLongClickListener {
            onShopItemLongClickListener?.invoke(shopItem)
            true
        }
        binding.root.setOnClickListener {
            onShopItemClickListener?.invoke(shopItem)
            true
        }

        when(binding){
            is ItemShopEnabledBinding -> {
                binding.tvName.text = shopItem.name
                binding.tvCount.text = shopItem.count.toString()
            }
            is ItemShopDisabledBinding -> {
                binding.tvName.text = shopItem.name
                binding.tvCount.text = shopItem.count.toString()
            }
        }

    }

    // 2 метод решения бага
   /* override fun onViewRecycled(holder: ShopItemViewHolder) {
        super.onViewRecycled(holder)
        val binding = holder.binding
        binding.tvName.text = ""
        binding.tvCount.text = ""
        binding.tvName.setTextColor(ContextCompat.getColor(holder.itemView.context,android.R.color.white))
    }*/

    override fun getItemCount() = shopList.size

    override fun getItemViewType(position: Int): Int {
        val item = shopList[position]
        return if (item.enabled){
            VIEW_TYPE_ENABLED
        }else{
            VIEW_TYPE_DISABLED
        }
    }

    class ShopItemViewHolder(
        val binding: ViewDataBinding
        ): RecyclerView.ViewHolder(binding.root)

    companion object{
        const val VIEW_TYPE_ENABLED = 101
        const val VIEW_TYPE_DISABLED = 100

        const val MAX_POOL_SIZE = 20
    }


}