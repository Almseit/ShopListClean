package com.almseit.shoplistclean.domain

class GetShopItemUseCase(private val shopListRepository: ShopListRepository) {
    fun getShopItem(shopItemID: Int): ShopItem {
        return shopListRepository.getShopItem(shopItemID)
    }
}