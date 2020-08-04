package com.partos.ipet

import android.app.Application
import com.partos.ipet.models.Ball
import com.partos.ipet.models.Pet

class MyApp : Application() {
    companion object {
        lateinit var pet: Pet
        lateinit var balls: ArrayList<Ball>
    }
}