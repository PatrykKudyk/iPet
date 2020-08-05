package com.partos.ipet.logic.viewhelpers

import android.view.View
import android.widget.*
import androidx.cardview.widget.CardView
import com.partos.ipet.MyApp
import com.partos.ipet.R
import com.partos.ipet.activities.MainActivity
import com.partos.ipet.db.DataBaseHelper
import com.partos.ipet.logic.FormatsHelper
import com.partos.ipet.logic.PetHelper
import com.partos.ipet.logic.PetIconsHelper
import com.partos.ipet.logic.ToastHelper

class UpgradeListenersHelper () {

    private lateinit var db: DataBaseHelper
    private lateinit var upgradeButton: ImageView
    private lateinit var hungerProgress: ProgressBar
    private lateinit var funProgress: ProgressBar
    private lateinit var foodText: TextView
    private lateinit var playText: TextView
    private lateinit var moneyText: TextView
    private lateinit var upgradesCard: CardView
    private lateinit var upgradeFood: CardView
    private lateinit var upgradeFoodMax: CardView
    private lateinit var upgradePlay: CardView
    private lateinit var upgradePlayMax: CardView
    private lateinit var upgradeFoodCost: TextView
    private lateinit var upgradeFoodMaxCost: TextView
    private lateinit var upgradePlayCost: TextView
    private lateinit var upgradePlayMaxCost: TextView
    private lateinit var upgradeFoodIncome: CardView
    private lateinit var upgradeFoodIncomeCost: TextView
    private lateinit var upgradePlayIncome: CardView
    private lateinit var upgradePlayIncomeCost: TextView
    private lateinit var upgradeFoodImage: ImageView
    private lateinit var upgradeFoodMaxImage: ImageView
    private lateinit var upgradeFoodIncomeImage: ImageView
    private lateinit var upgradePlayImage: ImageView
    private lateinit var upgradePlayMaxImage: ImageView
    private lateinit var upgradePlayIncomeImage: ImageView
    private lateinit var shopCard: CardView
    private lateinit var foodIncome: TextView
    private lateinit var playIncome: TextView

    fun handleUpgradeListeners(rootView: View) {
        initViews(rootView)

        upgradeButton.setOnClickListener {
            if (shopCard.visibility == View.VISIBLE) {
                shopCard.visibility = View.GONE
            }
            if (upgradesCard.visibility == View.GONE) {
                upgradesCard.visibility = View.VISIBLE
                upgradeFoodCost.text =
                    FormatsHelper().formatMoney(MyApp.pet.upgradePrices.hungerAmount)
                upgradeFoodMaxCost.text =
                    FormatsHelper().formatMoney(MyApp.pet.upgradePrices.hungerMaxAmount)
                upgradePlayCost.text =
                    FormatsHelper().formatMoney(MyApp.pet.upgradePrices.funAmount)
                upgradePlayMaxCost.text =
                    FormatsHelper().formatMoney(MyApp.pet.upgradePrices.funMaxAmount)
                upgradeFoodIncomeCost.text =
                    FormatsHelper().formatMoney(MyApp.pet.upgradePrices.hungerIncome)
                upgradePlayIncomeCost.text =
                    FormatsHelper().formatMoney(MyApp.pet.upgradePrices.funIncome)
                PetIconsHelper().setFoodIcon(
                    upgradeFoodImage,
                    rootView.context,
                    rootView.context as MainActivity,
                    MyApp.pet.look.petType
                )
                PetIconsHelper().setFoodIcon(
                    upgradeFoodMaxImage,
                    rootView.context,
                    rootView.context as MainActivity,
                    MyApp.pet.look.petType
                )
                PetIconsHelper().setFoodIcon(
                    upgradeFoodIncomeImage,
                    rootView.context,
                    rootView.context as MainActivity,
                    MyApp.pet.look.petType
                )
                PetIconsHelper().setFunIcon(
                    upgradePlayImage,
                    rootView.context,
                    rootView.context as MainActivity,
                    MyApp.pet.look.petType
                )
                PetIconsHelper().setFunIcon(
                    upgradePlayMaxImage,
                    rootView.context,
                    rootView.context as MainActivity,
                    MyApp.pet.look.petType
                )
                PetIconsHelper().setFunIcon(
                    upgradePlayIncomeImage,
                    rootView.context,
                    rootView.context as MainActivity,
                    MyApp.pet.look.petType
                )
            } else {
                upgradesCard.visibility = View.GONE
            }
        }

        upgradeFood.setOnClickListener {
            if (MyApp.pet.points >= MyApp.pet.upgradePrices.hungerAmount) {
                PetHelper().handleUpgradeFood()
                moneyText.text = FormatsHelper().formatMoney(MyApp.pet.points)
                upgradeFoodCost.text =
                    FormatsHelper().formatMoney(MyApp.pet.upgradePrices.hungerAmount)
                foodText.text = MyApp.pet.foodAmount.toString()
                db.updatePet(MyApp.pet)
            } else {
                ToastHelper().showNoMoneyToast(rootView.context)
            }
        }

        upgradeFoodMax.setOnClickListener {
            if (MyApp.pet.points >= MyApp.pet.upgradePrices.hungerMaxAmount) {
                PetHelper().handleUpgradeFoodMax()
                moneyText.text = FormatsHelper().formatMoney(MyApp.pet.points)
                upgradeFoodMaxCost.text =
                    FormatsHelper().formatMoney(MyApp.pet.upgradePrices.hungerMaxAmount)
                hungerProgress.max = MyApp.pet.maxHungerLvl
                db.updatePet(MyApp.pet)
                ProgressViews().showProgress(rootView.context, rootView)
            } else {
                ToastHelper().showNoMoneyToast(rootView.context)
            }
        }

        upgradePlay.setOnClickListener {
            if (MyApp.pet.points >= MyApp.pet.upgradePrices.funAmount) {
                PetHelper().handleUpgradePlay()
                moneyText.text = FormatsHelper().formatMoney(MyApp.pet.points)
                upgradePlayCost.text =
                    FormatsHelper().formatMoney(MyApp.pet.upgradePrices.funAmount)
                playText.text = MyApp.pet.funAmount.toString()
                db.updatePet(MyApp.pet)
            } else {
                ToastHelper().showNoMoneyToast(rootView.context)
            }
        }

        upgradePlayMax.setOnClickListener {
            if (MyApp.pet.points >= MyApp.pet.upgradePrices.funMaxAmount) {
                PetHelper().handleUpgradePlayMax()
                moneyText.text = FormatsHelper().formatMoney(MyApp.pet.points)
                upgradePlayMaxCost.text =
                    FormatsHelper().formatMoney(MyApp.pet.upgradePrices.funMaxAmount)
                funProgress.max = MyApp.pet.maxFunLvl
                db.updatePet(MyApp.pet)
                ProgressViews().showProgress(rootView.context, rootView)
            } else {
                ToastHelper().showNoMoneyToast(rootView.context)
            }
        }

        upgradeFoodIncome.setOnClickListener {
            if (MyApp.pet.points >= MyApp.pet.upgradePrices.hungerIncome) {
                PetHelper().handleUpgradeFoodIncome()
                moneyText.text = FormatsHelper().formatMoney(MyApp.pet.points)
                upgradeFoodIncomeCost.text =
                    FormatsHelper().formatMoney(MyApp.pet.upgradePrices.hungerIncome)
                foodIncome.text = MyApp.pet.foodIncome.toString()
                db.updatePet(MyApp.pet)
            } else {
                ToastHelper().showNoMoneyToast(rootView.context)
            }
        }

        upgradePlayIncome.setOnClickListener {
            if (MyApp.pet.points >= MyApp.pet.upgradePrices.funIncome) {
                PetHelper().handleUpgradePlayIncome()
                moneyText.text = FormatsHelper().formatMoney(MyApp.pet.points)
                upgradePlayIncomeCost.text =
                    FormatsHelper().formatMoney(MyApp.pet.upgradePrices.funIncome)
                playIncome.text = MyApp.pet.funIncome.toString()
                db.updatePet(MyApp.pet)
            } else {
                ToastHelper().showNoMoneyToast(rootView.context)
            }
        }

    }

    private fun initViews(rootView: View) {
        db = DataBaseHelper(rootView.context)
        upgradeButton = rootView.findViewById(R.id.button_upgrades)
        hungerProgress = rootView.findViewById(R.id.progress_hunger)
        funProgress = rootView.findViewById(R.id.progress_fun)
        foodText = rootView.findViewById(R.id.text_food)
        playText = rootView.findViewById(R.id.text_play)
        moneyText = rootView.findViewById(R.id.text_money)
        upgradesCard = rootView.findViewById(R.id.upgrades_card)
        upgradeFood = rootView.findViewById(R.id.upgrade_food)
        upgradeFoodMax = rootView.findViewById(R.id.upgrade_food_max)
        upgradePlay = rootView.findViewById(R.id.upgrade_play)
        upgradePlayMax = rootView.findViewById(R.id.upgrade_play_max)
        upgradeFoodCost = rootView.findViewById(R.id.upgrade_food_cost)
        upgradeFoodMaxCost = rootView.findViewById(R.id.upgrade_food_max_cost)
        upgradePlayCost = rootView.findViewById(R.id.upgrade_play_cost)
        upgradePlayMaxCost = rootView.findViewById(R.id.upgrade_play_max_cost)
        shopCard = rootView.findViewById(R.id.shop_card)
        upgradeFoodIncome = rootView.findViewById(R.id.upgrade_food_income)
        upgradeFoodIncomeCost = rootView.findViewById(R.id.upgrade_food_income_cost)
        upgradePlayIncome = rootView.findViewById(R.id.upgrade_play_income)
        upgradePlayIncomeCost = rootView.findViewById(R.id.upgrade_play_income_cost)
        foodIncome = rootView.findViewById(R.id.text_food_income)
        playIncome = rootView.findViewById(R.id.text_play_income)
        upgradeFoodImage = rootView.findViewById(R.id.upgrade_food_image)
        upgradeFoodMaxImage = rootView.findViewById(R.id.upgrade_food_max_image)
        upgradeFoodIncomeImage = rootView.findViewById(R.id.upgrade_food_income_image)
        upgradePlayImage = rootView.findViewById(R.id.upgrade_play_image)
        upgradePlayMaxImage = rootView.findViewById(R.id.upgrade_play_max_image)
        upgradePlayIncomeImage = rootView.findViewById(R.id.upgrade_play_income_image)
    }
}