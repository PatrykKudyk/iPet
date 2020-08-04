package com.partos.ipet.logic

import com.partos.ipet.MyApp

class PetHelper () {

    fun setTimeDiff(diff: Long) {
        MyApp.pet.hungerLvl -= diff.toInt()
        if (MyApp.pet.hungerLvl < 0) {
            MyApp.pet.hungerLvl = 0
        }
        MyApp.pet.funLvl -= diff.toInt() * 2
        if (MyApp.pet.funLvl < 0) {
            MyApp.pet.funLvl = 0
        }
        if (MyApp.pet.hungerLvl != 0) {
            MyApp.pet.age += diff
        }
    }

    fun handleFoodButton() {
        if (MyApp.pet.maxHungerLvl - MyApp.pet.hungerLvl >= MyApp.pet.foodAmount) {
            MyApp.pet.points += MyApp.pet.foodAmount * MyApp.pet.foodIncome
        } else {
            MyApp.pet.points += (MyApp.pet.maxHungerLvl - MyApp.pet.hungerLvl) * MyApp.pet.foodIncome
        }
        MyApp.pet.hungerLvl += MyApp.pet.foodAmount
        if (MyApp.pet.hungerLvl > MyApp.pet.maxHungerLvl) {
            MyApp.pet.hungerLvl = MyApp.pet.maxHungerLvl
        }
    }

    fun handleFunButton() {
        if (MyApp.pet.maxFunLvl - MyApp.pet.funLvl >= MyApp.pet.funAmount) {
            MyApp.pet.points += MyApp.pet.funAmount * MyApp.pet.funIncome
        } else {
            MyApp.pet.points += (MyApp.pet.maxFunLvl - MyApp.pet.funLvl) * MyApp.pet.funIncome
        }
        MyApp.pet.funLvl += MyApp.pet.funAmount
        if (MyApp.pet.funLvl > MyApp.pet.maxFunLvl) {
            MyApp.pet.funLvl = MyApp.pet.maxFunLvl
        }
    }

    fun handleUpgradeFood() {
        MyApp.pet.points -= MyApp.pet.upgradePrices.hungerAmount
        MyApp.pet.upgradePrices.hungerAmount =
            (MyApp.pet.upgradePrices.hungerAmount * 1.1).toLong()
        MyApp.pet.foodAmount = ((MyApp.pet.foodAmount * 1.1) + 1).toInt()
    }

    fun handleUpgradeFoodMax() {
        MyApp.pet.points -= MyApp.pet.upgradePrices.hungerMaxAmount
        MyApp.pet.upgradePrices.hungerMaxAmount =
            (MyApp.pet.upgradePrices.hungerMaxAmount * 1.1).toLong()
        MyApp.pet.maxHungerLvl = ((MyApp.pet.maxHungerLvl * 1.1) + 1).toInt()
    }

    fun handleUpgradePlay() {
        MyApp.pet.points -= MyApp.pet.upgradePrices.funAmount
        MyApp.pet.upgradePrices.funAmount =
            (MyApp.pet.upgradePrices.funAmount * 1.1).toLong()
        MyApp.pet.funAmount = ((MyApp.pet.funAmount * 1.1) + 1).toInt()
    }

    fun handleUpgradePlayMax() {
        MyApp.pet.points -= MyApp.pet.upgradePrices.funMaxAmount
        MyApp.pet.upgradePrices.funMaxAmount =
            (MyApp.pet.upgradePrices.funMaxAmount * 1.1).toLong()
        MyApp.pet.maxFunLvl = ((MyApp.pet.maxFunLvl * 1.1) + 1).toInt()
    }

    fun handleUpgradeFoodIncome() {
        MyApp.pet.points -= MyApp.pet.upgradePrices.hungerIncome
        MyApp.pet.upgradePrices.hungerIncome =
            (MyApp.pet.upgradePrices.hungerIncome * 1.4).toLong()
        MyApp.pet.foodIncome += 1
    }

    fun handleUpgradePlayIncome() {
        MyApp.pet.points -= MyApp.pet.upgradePrices.funIncome
        MyApp.pet.upgradePrices.funIncome =
            (MyApp.pet.upgradePrices.funIncome * 1.4).toLong()
        MyApp.pet.funIncome += 1
    }
}