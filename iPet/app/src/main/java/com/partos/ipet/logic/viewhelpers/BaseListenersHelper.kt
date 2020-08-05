package com.partos.ipet.logic.viewhelpers

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import com.partos.ipet.MyApp
import com.partos.ipet.R
import com.partos.ipet.db.DataBaseHelper
import com.partos.ipet.logic.AnimationsHelper
import com.partos.ipet.logic.FormatsHelper
import com.partos.ipet.logic.PetHelper

class BaseListenersHelper () {

    private lateinit var db: DataBaseHelper
    private lateinit var foodButton: ImageView
    private lateinit var playButton: ImageView
    private lateinit var moneyText: TextView

    fun handleBaseListeners(rootView: View) {
        initViews(rootView)
        foodButton.setOnClickListener {
            if (MyApp.pet.isAlive == 1) {
                PetHelper().handleFoodButton()
                AnimationsHelper().animateFood(rootView.context)
                db.updatePet(MyApp.pet)
                ProgressViews().showProgress(rootView.context, rootView)
                moneyText.text = FormatsHelper().formatMoney(MyApp.pet.points)
            }
        }

        playButton.setOnClickListener {
            if (MyApp.pet.isAlive == 1) {
                PetHelper().handleFunButton()
                AnimationsHelper().animateBall(rootView.context)
                db.updatePet(MyApp.pet)
                ProgressViews().showProgress(rootView.context, rootView)
                moneyText.text = FormatsHelper().formatMoney(MyApp.pet.points)
            }
        }
    }

    private fun initViews(rootView: View) {
        db = DataBaseHelper(rootView.context)
        foodButton = rootView.findViewById(R.id.button_food)
        playButton = rootView.findViewById(R.id.button_play)
        moneyText = rootView.findViewById(R.id.text_money)
    }
}