package com.partos.ipet.logic

import android.content.Context
import android.os.Handler
import android.view.View
import android.view.animation.AnimationUtils
import androidx.cardview.widget.CardView
import com.partos.ipet.MyApp
import com.partos.ipet.R
import com.partos.ipet.activities.MainActivity
import kotlin.random.Random

class AnimationsHelper() {

    fun animateFood(context: Context) {
        var number: Int
        do {
            number = Random.nextInt(0, 10)
        } while (MyApp.balls[number].isUsed)
        MyApp.balls[number].isUsed = true
        val animation = AnimationUtils.loadAnimation(context, R.anim.fall_down)
        MyApp.balls[number].imageView.visibility = View.VISIBLE
        PetIconsHelper().setFoodIcon(
            MyApp.balls[number].imageView,
            context,
            context as MainActivity,
            MyApp.pet.look.petType
        )
        MyApp.balls[number].imageView.startAnimation(animation)
        Handler().postDelayed({
            MyApp.balls[number].imageView.visibility = View.INVISIBLE
            MyApp.balls[number].isUsed = false
        }, 570)
    }
    fun animateBall(context: Context) {
        var number: Int
        do {
            number = Random.nextInt(0, 10)
        } while (MyApp.balls[number].isUsed)
        MyApp.balls[number].isUsed = true
        val animation = AnimationUtils.loadAnimation(context, R.anim.fall_down)
        MyApp.balls[number].imageView.visibility = View.VISIBLE
        PetIconsHelper().setFunIcon(
            MyApp.balls[number].imageView,
            context,
            context as MainActivity,
            MyApp.pet.look.petType
        )
        MyApp.balls[number].imageView.startAnimation(animation)
        Handler().postDelayed({
            MyApp.balls[number].imageView.visibility = View.INVISIBLE
            MyApp.balls[number].isUsed = false
        }, 570)
    }

    fun animateEnterLeft(cardView: CardView, context: Context) {
        val animation = AnimationUtils.loadAnimation(context, R.anim.enter_left)
        cardView.startAnimation(animation)
    }

    fun animateEnterRight(cardView: CardView, context: Context) {
        val animation = AnimationUtils.loadAnimation(context, R.anim.enter_right)
        cardView.startAnimation(animation)
    }

    fun animateExitLeft(cardView: CardView, context: Context) {
        val animation = AnimationUtils.loadAnimation(context, R.anim.exit_left)
        cardView.startAnimation(animation)
    }

    fun animateExitRight(cardView: CardView, context: Context) {
        val animation = AnimationUtils.loadAnimation(context, R.anim.exit_right)
        cardView.startAnimation(animation)
    }
}