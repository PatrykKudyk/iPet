package com.partos.ipet

import android.app.Application
import com.partos.ipet.models.Look
import com.partos.ipet.models.Pet
import com.partos.ipet.models.UpgradePrices

class MyApp (): Application() {
    companion object {
        var pet = Pet(
            1,
            100,
            100,
            100,
            100,
            Look(
                "dog",
                1,
                1
            ),
            100000,
            0,
            1,
            5,
            10,
            1,
            2,
            UpgradePrices(
                100,
                150,
                70,
                120,
                500,
                500
            )
        )
    }
}