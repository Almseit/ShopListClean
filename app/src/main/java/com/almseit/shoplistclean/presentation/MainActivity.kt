package com.almseit.shoplistclean.presentation

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import com.almseit.shoplistclean.R
import com.almseit.shoplistclean.databinding.ActivityMainBinding
import com.google.android.material.floatingactionbutton.FloatingActionButton

class MainActivity : AppCompatActivity() {

    private lateinit var viewModel: MainViewModel
    private lateinit var shopListAdapter: ShopListAdapter
    private lateinit var floatButton:FloatingActionButton
    private lateinit var binding: ActivityMainBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setupRecycler()
        viewModel = ViewModelProvider(this)[MainViewModel::class.java]
        viewModel.shopList.observe(this, Observer {
            shopListAdapter.shopList = it

        })

        binding.btAddShopItem.setOnClickListener {
            if (isOnePaneMode()){
                val intent = ShopItemActivity.newIntentAddItem(this)
                startActivity(intent)
            }else{
               launchFragment(ShopItemFragment.newInstanceAddItem())
            }

        }
    }

    private fun isOnePaneMode():Boolean{
        return binding.shopItemContainer == null
    }

    private fun launchFragment(fragment: Fragment){
        supportFragmentManager.popBackStack()
        supportFragmentManager.beginTransaction()
            .replace(R.id.shopItemContainer,fragment)
            .addToBackStack(null)
            .commit()
    }

    private fun setupRecycler(){

        shopListAdapter = ShopListAdapter()
        binding.rvShopList.adapter = shopListAdapter
        binding.rvShopList.recycledViewPool.setMaxRecycledViews(ShopListAdapter.VIEW_TYPE_ENABLED,ShopListAdapter.MAX_POOL_SIZE)
        binding.rvShopList.recycledViewPool.setMaxRecycledViews(ShopListAdapter.VIEW_TYPE_DISABLED,ShopListAdapter.MAX_POOL_SIZE)
        onLongClickListener()
        onClickListener()
        setupSwipeListener(binding.rvShopList)

    }

    private fun setupSwipeListener(rvShopList: RecyclerView) {
        val callback = object :
            ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.END or ItemTouchHelper.START) {
            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ): Boolean {
                return false
            }

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                val item = shopListAdapter.shopList[viewHolder.adapterPosition]
                viewModel.deleteShopItem(item)
            }

        }
        val itemTouchHelper = ItemTouchHelper(callback)
        itemTouchHelper.attachToRecyclerView(rvShopList)
    }

    private fun onClickListener() {
        shopListAdapter.onShopItemClickListener = {
            if (isOnePaneMode()){
                val intent = ShopItemActivity.newIntentEditItem(this,it.id)
                startActivity(intent)
            }else{
                launchFragment(ShopItemFragment.newInstanceEditItem(it.id))
            }

        }
    }

    private fun onLongClickListener() {
        shopListAdapter.onShopItemLongClickListener = {
            viewModel.changeEnabledState(it)
        }
    }


}