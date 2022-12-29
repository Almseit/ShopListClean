package com.almseit.shoplistclean.domain

import androidx.lifecycle.LiveData

interface ShopListRepository {

    fun getShopList() : LiveData<List<ShopItem>>
    fun getShopItem(shopItemID: Int): ShopItem
    fun addShopItem(shopItem: ShopItem)
    fun deleteShopItem(shopItem: ShopItem)
    fun editShopItem(shopItem: ShopItem)
}