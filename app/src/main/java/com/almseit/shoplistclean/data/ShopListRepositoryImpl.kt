package com.almseit.shoplistclean.data

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import com.almseit.shoplistclean.domain.ShopItem
import com.almseit.shoplistclean.domain.ShopListRepository

class ShopListRepositoryImpl(application: Application) : ShopListRepository {

   private val shopListDao = AppDataBase.getInstance(application).shopListDao()
   private val mapper = ShopListMapper()

    override fun getShopList(): LiveData<List<ShopItem>> = Transformations.map(
        shopListDao.getShopList()
    ){
        mapper.mapListDbModelToEntity(it)
    }

    override fun getShopItem(shopItemID: Int): ShopItem {
       val dbModel = shopListDao.getShopItem(shopItemID)
        return mapper.mapDbModelToEntity(dbModel)
    }

    override fun addShopItem(shopItem: ShopItem) {
        shopListDao.addShopItem(mapper.mapEntityToDbModel(shopItem))
    }

    override fun deleteShopItem(shopItem: ShopItem) {
       shopListDao.deleteShopItem(shopItem.id)
    }

    override fun editShopItem(shopItem: ShopItem) {
        shopListDao.addShopItem(mapper.mapEntityToDbModel(shopItem))
    }


}