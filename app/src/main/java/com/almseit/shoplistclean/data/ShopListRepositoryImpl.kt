package com.almseit.shoplistclean.data

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.almseit.shoplistclean.domain.ShopItem
import com.almseit.shoplistclean.domain.ShopListRepository

object ShopListRepositoryImpl : ShopListRepository {

    private val shopList = mutableListOf<ShopItem>()
    private var autoIncrementID = 0
    private val shopListLD = MutableLiveData<List<ShopItem>>()

    init {
        for (i in 0 until 10){
            val item = ShopItem("Name: $i",i, true)
            addShopItem(item)
        }

    }

    override fun getShopList(): LiveData<List<ShopItem>> {
       return shopListLD
    }

    override fun getShopItem(shopItemID: Int): ShopItem {
        return shopList.find {
            it.id == shopItemID
        } ?: throw RuntimeException("Element with id $shopItemID not found")
    }

    override fun addShopItem(shopItem: ShopItem) {
        if (shopItem.id == ShopItem.UNDEFINED_ID){
            shopItem.id = autoIncrementID++
        }
        shopList.add(shopItem)
        updateList()
    }

    override fun deleteShopItem(shopItem: ShopItem) {
        shopList.remove(shopItem)
        updateList()
    }

    override fun editShopItem(shopItem: ShopItem) {
       val oldElement = getShopItem(shopItem.id)
       shopList.remove(oldElement)
       addShopItem(shopItem)
    }

    private fun updateList(){
        shopListLD.value = shopList.toList()

    }
}