package com.partos.ipet.logic

import android.app.Activity
import android.icu.util.Calendar
import android.os.Handler
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import com.partos.ipet.MyApp
import com.partos.ipet.R
import com.partos.ipet.activities.MainActivity
import com.partos.ipet.db.DataBaseHelper
import com.partos.ipet.logic.viewhelpers.ProgressViews
import com.partos.ipet.models.Date

class MainLoop() {

    private lateinit var image: ImageView
    private lateinit var db: DataBaseHelper
    private lateinit var foodButton: ImageView
    private lateinit var playButton: ImageView
    private lateinit var foodText: TextView
    private lateinit var playText: TextView
    private lateinit var moneyText: TextView
    private lateinit var ageText: TextView
    private lateinit var foodIncome: TextView
    private lateinit var playIncome: TextView

    fun handleMainLoop(rootView: View) {
        initViews(rootView)
        var looperThread = TimerThread()
        looperThread.start()
        Handler().postDelayed({
            var threadHandler = Handler(looperThread.looper)
            var position = 0
            var isReady = 0
            foodText.text = MyApp.pet.foodAmount.toString()
            playText.text = MyApp.pet.funAmount.toString()
            moneyText.text = FormatsHelper().formatMoney(MyApp.pet.points)
            foodIncome.text = MyApp.pet.foodIncome.toString()
            playIncome.text = MyApp.pet.funIncome.toString()
            val activity = (rootView.context as MainActivity)
            initIcons(activity, rootView)
            showAge(rootView)
            ProgressViews().showProgress(rootView.context, rootView)
            threadHandler.post(object : Runnable {
                override fun run() {
                    if (isReady == 2) {
                        PetIconsHelper().setNormalIcon(
                            image,
                            rootView.context,
                            activity,
                            MyApp.pet.look.petType
                        )
                        MyApp.pet.hungerLvl -= 1
                        MyApp.pet.funLvl -= 2
                        if (MyApp.pet.funLvl < 0) {
                            MyApp.pet.funLvl = 0
                        }
                        if (MyApp.pet.hungerLvl < 0) {
                            MyApp.pet.hungerLvl = 0
                        }
                        (rootView.context as MainActivity).runOnUiThread {
                            ProgressViews().showProgress(rootView.context, rootView)
                            showAge(rootView)
                        }
                        isReady = 0
                        MyApp.pet.age++
                        updateDate()
                    } else {
                        PetIconsHelper().setNormalSecondIcon(
                            image,
                            rootView.context,
                            activity,
                            MyApp.pet.look.petType
                        )
                    }
                    isReady++
                    position++
                    db.updatePet(MyApp.pet)
                    if (!checkIfDead()) {
                        threadHandler.postDelayed(this, 500)
                    } else {
                        MyApp.pet.isAlive = 0
                        (rootView.context as MainActivity).runOnUiThread {
                            image.setImageDrawable(rootView.context.getDrawable(R.drawable.death))
                        }
                        threadHandler.looper.quitSafely()
                    }
                }
            })
        }, 300)
    }

    private fun initViews(rootView: View) {
        db = DataBaseHelper(rootView.context)
        image = rootView.findViewById(R.id.dog_image)
        foodButton = rootView.findViewById(R.id.button_food)
        playButton = rootView.findViewById(R.id.button_play)
        foodText = rootView.findViewById(R.id.text_food)
        playText = rootView.findViewById(R.id.text_play)
        moneyText = rootView.findViewById(R.id.text_money)
        ageText = rootView.findViewById(R.id.text_time)
        foodIncome = rootView.findViewById(R.id.text_food_income)
        playIncome = rootView.findViewById(R.id.text_play_income)
    }

    private fun initIcons(activity: Activity, rootView: View) {
        PetIconsHelper().setFoodIcon(foodButton, rootView.context, activity, MyApp.pet.look.petType)
        PetIconsHelper().setFunIcon(playButton, rootView.context, activity, MyApp.pet.look.petType)
    }

    private fun showAge(rootView: View) {
        ageText.text = FormatsHelper().formatAge(MyApp.pet.age, rootView.context)
    }

    private fun checkIfDead(): Boolean {
        if (MyApp.pet.hungerLvl == 0) {
            return true
        }
        return false
    }

    private fun updateDate() {
        var currentDate = Calendar.getInstance()
        MyApp.date.year = currentDate.get(Calendar.YEAR)
        MyApp.date.month = currentDate.get(Calendar.MONTH)
        MyApp.date.day = currentDate.get(Calendar.DAY_OF_MONTH)
        MyApp.date.hour = currentDate.get(Calendar.HOUR_OF_DAY)
        MyApp.date.minute = currentDate.get(Calendar.MINUTE)
        MyApp.date.second = currentDate.get(Calendar.SECOND)
        db.updateDate(MyApp.date)
    }
}