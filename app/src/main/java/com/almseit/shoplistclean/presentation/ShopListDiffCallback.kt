package com.almseit.shoplistclean.presentation

import androidx.recyclerview.widget.DiffUtil
import com.almseit.shoplistclean.domain.ShopItem


class ShopListDiffCallback(
    private val oldList: List<ShopItem>,
    private val newList: List<ShopItem>
) : DiffUtil.Callback() {

    override fun getOldListSize(): Int {
        return oldList.size
    }

    override fun getNewListSize(): Int {
        return newList.size
    }

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        val shopItemOld = oldList[oldItemPosition]
        val shopItemNew = newList[newItemPosition]
        return shopItemOld.id == shopItemNew.id
    }

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        val shopItemOld = oldList[oldItemPosition]
        val shopItemNew = newList[newItemPosition]
        return shopItemOld == shopItemNew
    }
}