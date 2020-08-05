package com.partos.ipet.logic

import android.icu.util.Calendar
import android.view.View
import android.widget.ImageView
import com.partos.ipet.MyApp
import com.partos.ipet.R
import com.partos.ipet.db.DataBaseHelper
import com.partos.ipet.models.*

class MyAppInitHelper() {

    private lateinit var db: DataBaseHelper
    private lateinit var ball1: ImageView
    private lateinit var ball2: ImageView
    private lateinit var ball3: ImageView
    private lateinit var ball4: ImageView
    private lateinit var ball5: ImageView
    private lateinit var ball6: ImageView
    private lateinit var ball7: ImageView
    private lateinit var ball8: ImageView
    private lateinit var ball9: ImageView
    private lateinit var ball10: ImageView

    fun initMyApp(rootView: View) {
        initViews(rootView)
        initBallsArray()
        getPet()
        getDate()
    }

    private fun initBallsArray() {
        MyApp.balls = ArrayList()
        MyApp.balls.add(Ball(false, ball1))
        MyApp.balls.add(Ball(false, ball2))
        MyApp.balls.add(Ball(false, ball3))
        MyApp.balls.add(Ball(false, ball4))
        MyApp.balls.add(Ball(false, ball5))
        MyApp.balls.add(Ball(false, ball6))
        MyApp.balls.add(Ball(false, ball7))
        MyApp.balls.add(Ball(false, ball8))
        MyApp.balls.add(Ball(false, ball9))
        MyApp.balls.add(Ball(false, ball10))
    }

    private fun getPet() {
        var somePet = db.getPets()
        if (somePet.size != 0) {
            MyApp.pet = somePet[0]
        } else {
            MyApp.pet = Pet(
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
                1000,
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
            db.addPet(MyApp.pet)
        }
    }

    private fun getDate() {
        val today = Calendar.getInstance()
        MyApp.date = Date(
            0,
            today.get(Calendar.YEAR),
            today.get(Calendar.MONTH),
            today.get(Calendar.DAY_OF_MONTH),
            today.get(Calendar.HOUR_OF_DAY),
            today.get(Calendar.MINUTE),
            today.get(Calendar.SECOND)
        )
        val someDate = db.getDate()
        if (someDate.size == 0) {
            db.addDate(MyApp.date)
            MyApp.date = db.getDate()[0]
        } else {
            MyApp.date = someDate[0]
        }
    }

    private fun initViews(rootView: View) {
        db = DataBaseHelper(rootView.context)
        ball1 = rootView.findViewById(R.id.ball_image_1)
        ball2 = rootView.findViewById(R.id.ball_image_2)
        ball3 = rootView.findViewById(R.id.ball_image_3)
        ball4 = rootView.findViewById(R.id.ball_image_4)
        ball5 = rootView.findViewById(R.id.ball_image_5)
        ball6 = rootView.findViewById(R.id.ball_image_6)
        ball7 = rootView.findViewById(R.id.ball_image_7)
        ball8 = rootView.findViewById(R.id.ball_image_8)
        ball9 = rootView.findViewById(R.id.ball_image_9)
        ball10 = rootView.findViewById(R.id.ball_image_10)
    }
}