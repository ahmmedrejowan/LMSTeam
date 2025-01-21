package com.rejowan.lmsteamprofile.ui.modules.home

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.drawable.BitmapDrawable
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

import com.rejowan.lmsteamprofile.R
import com.rejowan.lmsteamprofile.databinding.ActivityHomeBinding

class Home : AppCompatActivity() {

    private val binding by lazy {
        ActivityHomeBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(binding.drawerLayout) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, 0, systemBars.right, systemBars.bottom)
            insets
        }

        setToolbar()

    }

    private fun setToolbar() {
        val bitmap = BitmapFactory.decodeResource(resources, R.drawable.logo)
        val resizedBitmap = Bitmap.createScaledBitmap(bitmap, dpToPx(32), dpToPx(32), true)
        binding.materialToolbar.logo = BitmapDrawable(resources, resizedBitmap)

    }

    private fun dpToPx(dp: Int): Int {
        return (dp * resources.displayMetrics.density).toInt()
    }

}