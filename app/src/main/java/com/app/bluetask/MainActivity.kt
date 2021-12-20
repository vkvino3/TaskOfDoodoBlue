package com.app.bluetask

import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import com.app.bluetask.databinding.ActivityMainBinding
import com.app.bluetask.fragment.*
import com.google.android.material.navigation.NavigationBarView

class MainActivity : AppCompatActivity(), NavigationBarView.OnItemSelectedListener {
    private val TAG = "MainActivity"
    private lateinit var binding: ActivityMainBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        binding.bottomView.setOnItemSelectedListener(this)
        binding.bottomView.selectedItemId = R.id.price
    }


    fun switchFragment(fragment: Fragment) {
        val fragmentManager = supportFragmentManager
        fragmentManager.beginTransaction().replace(R.id.container, fragment).commit()
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.price -> {
                switchFragment(PriceFragment())
            }

            R.id.portfolio -> {
                switchFragment(PortfolioFragment())
            }

            R.id.news -> {
                switchFragment(NewsFragment())
            }

            R.id.fav -> {
                switchFragment(FavoritesFragment())
            }

            R.id.invest -> {
                switchFragment(InvestFragment())
            }
            else -> {
                switchFragment(PriceFragment())
            }
        }
        return true
    }

}