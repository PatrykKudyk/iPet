package com.partos.ipet.logic.viewhelpers

import android.content.Context
import android.view.View
import android.widget.*
import com.partos.ipet.MyApp
import com.partos.ipet.R

class ProgressViews () {

    private lateinit var hungerText: TextView
    private lateinit var hungerProgress: ProgressBar
    private lateinit var funText: TextView
    private lateinit var funProgress: ProgressBar


    fun showProgress(context: Context, rootView: View) {
        initViews(rootView)
        hungerText.text =
            context.getString(R.string.hunger) + " " + MyApp.pet.hungerLvl.toString() +
                    "/" + MyApp.pet.maxHungerLvl.toString()
        funText.text =
            context.getString(R.string.`fun`) + " " + MyApp.pet.funLvl.toString() +
                    "/" + MyApp.pet.maxFunLvl.toString()
        hungerProgress.max = MyApp.pet.maxHungerLvl
        hungerProgress.progress = MyApp.pet.hungerLvl
        funProgress.max = MyApp.pet.maxFunLvl
        funProgress.progress = MyApp.pet.funLvl
    }

    private fun initViews(rootView: View) {
        hungerText = rootView.findViewById(R.id.text_hunger)
        hungerProgress = rootView.findViewById(R.id.progress_hunger)
        funText = rootView.findViewById(R.id.text_fun)
        funProgress = rootView.findViewById(R.id.progress_fun)
    }
}