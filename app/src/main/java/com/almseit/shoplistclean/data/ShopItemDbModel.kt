package com.almseit.shoplistclean.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity("shop_items")
data class ShopItemDbModel(
    @PrimaryKey(autoGenerate = true)
    var id: Int,
    val name: String,
    val count: Int,
    val enabled: Boolean
)
