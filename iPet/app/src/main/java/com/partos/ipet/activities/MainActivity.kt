package com.partos.ipet.activities

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.partos.ipet.R
import com.partos.ipet.fragments.BaseFragment

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val baseFragment = BaseFragment.newInstance()
        supportFragmentManager
            .beginTransaction()
            .add(R.id.main_frame_layout, baseFragment)
            .commit()
    }
}