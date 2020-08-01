package com.partos.ipet.models

data class Pet(
    var id: Long,
    var hungerLvl: Int,
    var maxHungerLvl: Int,
    var funLvl: Int,
    var maxFunLvl: Int,
    var look: Look,
    var points: Long,
    var age: Long,
    var isAlive: Int,
    var foodAmount: Int,
    var funAmount: Int,
    var upgradePrices: UpgradePrices
)