package com.partos.ipet.logic

import android.app.Activity
import android.content.Context
import android.widget.ImageView
import com.partos.ipet.MyApp
import com.partos.ipet.R

class PetIconsHelper() {

    fun setFoodIcon(view: ImageView, context: Context, activity: Activity, petType: String) {
        when (petType) {
            "dog" -> activity.runOnUiThread {
                view.setImageDrawable(context.getDrawable(R.drawable.dog_food))
            }
            "cat" -> activity.runOnUiThread {
                view.setImageDrawable(context.getDrawable(R.drawable.cat_food))
            }
            "fish" -> activity.runOnUiThread {
                view.setImageDrawable(context.getDrawable(R.drawable.fish_food))
            }
            "frog" -> activity.runOnUiThread {
                view.setImageDrawable(context.getDrawable(R.drawable.bug))
            }
            "mouse" -> activity.runOnUiThread {
                view.setImageDrawable(context.getDrawable(R.drawable.cheese))
            }
            "rabbit" -> activity.runOnUiThread {
                view.setImageDrawable(context.getDrawable(R.drawable.carrot))
            }
            "panda" -> activity.runOnUiThread {
                view.setImageDrawable(context.getDrawable(R.drawable.bamboo))
            }
            "penguin" -> activity.runOnUiThread {
                view.setImageDrawable(context.getDrawable(R.drawable.fish))
            }
        }
    }

    fun setNormalIcon(view: ImageView, context: Context, activity: Activity, petType: String) {
        when (petType) {
            "dog" -> activity.runOnUiThread {
                view.setImageDrawable(context.getDrawable(R.drawable.dog_normal))
            }
            "cat" -> activity.runOnUiThread {
                view.setImageDrawable(context.getDrawable(R.drawable.cat_normal))
            }
            "fish" -> activity.runOnUiThread {
                view.setImageDrawable(context.getDrawable(R.drawable.fish_normal))
            }
            "frog" -> activity.runOnUiThread {
                view.setImageDrawable(context.getDrawable(R.drawable.frog_normal))
            }
            "mouse" -> activity.runOnUiThread {
                view.setImageDrawable(context.getDrawable(R.drawable.mouse_normal))
            }
            "rabbit" -> activity.runOnUiThread {
                view.setImageDrawable(context.getDrawable(R.drawable.rabbit_normal))
            }
            "panda" -> activity.runOnUiThread {
                view.setImageDrawable(context.getDrawable(R.drawable.panda_normal))
            }
            "penguin" -> activity.runOnUiThread {
                view.setImageDrawable(context.getDrawable(R.drawable.penguin_normal))
            }
        }
    }

    fun setNormalSecondIcon(view: ImageView, context: Context, activity: Activity, petType: String) {
        when (MyApp.pet.look.petType) {
            "dog" -> activity.runOnUiThread {
                view.setImageDrawable(context.getDrawable(R.drawable.dog_normal_2))
            }
            "cat" -> activity.runOnUiThread {
                view.setImageDrawable(context.getDrawable(R.drawable.cat_normal_2))
            }
            "fish" -> activity.runOnUiThread {
                view.setImageDrawable(context.getDrawable(R.drawable.fish_normal_2))
            }
            "frog" -> activity.runOnUiThread {
                view.setImageDrawable(context.getDrawable(R.drawable.frog_normal_2))
            }
            "mouse" -> activity.runOnUiThread {
                view.setImageDrawable(context.getDrawable(R.drawable.mouse_normal_2))
            }
            "rabbit" -> activity.runOnUiThread {
                view.setImageDrawable(context.getDrawable(R.drawable.rabbit_normal_2))
            }
            "panda" -> activity.runOnUiThread {
                view.setImageDrawable(context.getDrawable(R.drawable.panda_normal_2))
            }
            "penguin" -> activity.runOnUiThread {
                view.setImageDrawable(context.getDrawable(R.drawable.penguin_normal_2))
            }
        }
    }
}