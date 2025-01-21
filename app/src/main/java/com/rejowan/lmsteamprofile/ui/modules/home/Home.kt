package com.rejowan.lmsteamprofile.ui.modules.home

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Color
import android.graphics.drawable.BitmapDrawable
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.addCallback
import androidx.appcompat.app.AppCompatActivity
import androidx.viewpager2.widget.ViewPager2.OnPageChangeCallback
import com.rejowan.lmsteamprofile.R
import com.rejowan.lmsteamprofile.databinding.ActivityHomeBinding
import com.rejowan.lmsteamprofile.ui.shared.adapter.FragmentAdapter


class Home : AppCompatActivity() {

    private val binding by lazy {
        ActivityHomeBinding.inflate(layoutInflater)
    }

    private var backPressedTime: Long = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        setupWindow()

        setToolbar()

        setupNavigationDrawer()

        setupMenu()

        setupBottomNavigation()

        backPressHandler()

    }

    private fun setupWindow() {
        window.statusBarColor = Color.TRANSPARENT
        window?.decorView?.systemUiVisibility = (View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                or View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN)
    }

    private fun setToolbar() {
        val bitmap = BitmapFactory.decodeResource(resources, R.drawable.logo)
        val resizedBitmap = Bitmap.createScaledBitmap(bitmap, dpToPx(32), dpToPx(32), true)
        binding.materialToolbar.logo = BitmapDrawable(resources, resizedBitmap)

    }

    private fun dpToPx(dp: Int): Int {
        return (dp * resources.displayMetrics.density).toInt()
    }

    private fun setupNavigationDrawer() {

        binding.materialToolbar.setNavigationOnClickListener {
            binding.drawerLayout.openDrawer(binding.navigationView)
        }



        binding.navigationView.setNavigationItemSelectedListener {
            binding.drawerLayout.closeDrawers()
            true
        }

    }

    private fun setupMenu() {
        binding.materialToolbar.setOnMenuItemClickListener {
            when (it.itemId) {
                R.id.top_search -> {
                    Toast.makeText(this, "Search", Toast.LENGTH_SHORT).show()
                }

                R.id.top_notification -> {
                    Toast.makeText(this, "Notification", Toast.LENGTH_SHORT).show()
                }

                R.id.top_logout -> {
                    Toast.makeText(this, "Logout", Toast.LENGTH_SHORT).show()
                }
            }
            true
        }
    }


    private fun setupBottomNavigation() {

        val fragmentPositionMap = mapOf(
            R.id.navHome to 0, R.id.navProfile to 1, R.id.navLms to 2, R.id.navShop to 3, R.id.navMore to 4
        )

        val fragmentAdapter = FragmentAdapter(supportFragmentManager, lifecycle)
        binding.contentLayout.viewPager.adapter = fragmentAdapter
        binding.contentLayout.viewPager.isUserInputEnabled = false

        var currentItem = 0

        binding.bottomNavigation.setOnItemSelectedListener {
            val position = fragmentPositionMap[it.itemId] ?: 0
            if (position + 1 == currentItem || position - 1 == currentItem) {
                binding.contentLayout.viewPager.setCurrentItem(position, true)
            } else {
                binding.contentLayout.viewPager.setCurrentItem(position, false)
            }
            currentItem = position

            true
        }

        binding.contentLayout.viewPager.registerOnPageChangeCallback(object :
            OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                binding.bottomNavigation.menu.getItem(position).isChecked = true
            }
        })


    }

    private fun backPressHandler() {
        onBackPressedDispatcher.addCallback {
            if (binding.drawerLayout.isDrawerOpen(binding.navigationView)) {
                binding.drawerLayout.closeDrawers()
            } else if (binding.contentLayout.viewPager.currentItem != 0) {
                binding.contentLayout.viewPager.setCurrentItem(0, false)

            } else {
                if (System.currentTimeMillis() - backPressedTime < 2000) {
                    this@Home.finishAffinity()
                } else {
                    Toast.makeText(this@Home, "Press back again to exit", Toast.LENGTH_SHORT).show()
                    backPressedTime = System.currentTimeMillis()
                }
            }
        }

    }


}