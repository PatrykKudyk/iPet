package com.partos.ipet.models

data class Pet(
    var hungerLvl: Int,
    var maxHungerLvl: Int,
    var funLvl: Int,
    var maxFunLvl: Int,
    var look: Look,
    var points: Int,
    var age: Long,
    var isAlive: Boolean
)