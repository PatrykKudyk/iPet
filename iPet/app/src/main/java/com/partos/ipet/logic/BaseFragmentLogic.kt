package com.partos.ipet.logic

import android.media.AudioAttributes
import android.media.SoundPool
import android.os.Handler
import android.view.View
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import com.partos.ipet.R
import com.partos.ipet.activities.MainActivity
import com.partos.ipet.models.Look
import com.partos.ipet.models.Pet

class BaseFragmentLogic(val rootView: View) {

    private var looperThread = TimerThread()
    private lateinit var image: ImageView
    private lateinit var soundPool: SoundPool
    private var soundBark = 0
    private lateinit var pet: Pet
    private lateinit var foodButton: ImageView
    private lateinit var upgradeButton: ImageView
    private lateinit var playButton: ImageView
    private lateinit var lookButton: ImageView
    private lateinit var hungerText: TextView
    private lateinit var hungerProgress: ProgressBar
    private lateinit var funText: TextView
    private lateinit var funProgress: ProgressBar
    private lateinit var foodText: TextView
    private lateinit var playText: TextView
    private lateinit var moneyText: TextView

    fun initFragment() {
        looperThread.start()
        initViews()
        initSoundPool()
        initPet()
        initListeners()
        image = rootView.findViewById(R.id.dog_image)
        Handler().postDelayed({
            animateDog()
        }, 300)
    }

    private fun initListeners() {
        foodButton.setOnClickListener {
            if (pet.isAlive) {
                pet.hungerLvl += pet.foodAmount
                if (pet.hungerLvl > pet.maxHungerLvl) {
                    pet.hungerLvl = pet.maxHungerLvl
                }
                showProgress()
            }
        }
        playButton.setOnClickListener {
            if (pet.isAlive) {
                if (pet.maxFunLvl - pet.funLvl >= pet.funAmount) {
                    pet.points += pet.funAmount
                } else {
                    pet.points += pet.maxFunLvl - pet.funLvl
                }
                pet.funLvl += pet.funAmount
                if (pet.funLvl > pet.maxFunLvl) {
                    pet.funLvl = pet.maxFunLvl
                }
                showProgress()
                moneyText.text = formatMoney()
            }
        }
    }

    private fun initViews() {
        foodButton = rootView.findViewById(R.id.button_food)
        upgradeButton = rootView.findViewById(R.id.button_upgrades)
        playButton = rootView.findViewById(R.id.button_play)
        lookButton = rootView.findViewById(R.id.button_look)
        hungerText = rootView.findViewById(R.id.text_hunger)
        hungerProgress = rootView.findViewById(R.id.progress_hunger)
        funText = rootView.findViewById(R.id.text_fun)
        funProgress = rootView.findViewById(R.id.progress_fun)
        foodText = rootView.findViewById(R.id.text_food)
        playText = rootView.findViewById(R.id.text_play)
        moneyText = rootView.findViewById(R.id.text_money)
    }

    private fun initPet() {
        pet = Pet(
            100,
            100,
            100,
            100,
            Look(
                "dog",
                1,
                1
            ),
            0,
            0,
            true,
            5,
            10
        )
        hungerText.text = rootView.context.getString(R.string.hunger) + " 100/100"
        funText.text = rootView.context.getString(R.string.`fun`) + " 100/100"
        hungerProgress.progress = 100
        funProgress.progress = 100
    }

    private fun initSoundPool() {
        val audioAttributes = AudioAttributes.Builder()
            .setUsage(AudioAttributes.USAGE_ASSISTANCE_SONIFICATION)
            .setContentType(AudioAttributes.CONTENT_TYPE_SONIFICATION)
            .build()
        soundPool = SoundPool.Builder()
            .setMaxStreams(2)
            .setAudioAttributes(audioAttributes)
            .build()

        soundBark = soundPool.load(rootView.context, R.raw.bark, 1)
    }

    private fun animateDog() {
        var threadHandler = Handler(looperThread.looper)
        var position = 0
        var isReady = 0
        foodText.text = pet.foodAmount.toString()
        playText.text = pet.funAmount.toString()
        moneyText.text = formatMoney()
        threadHandler.post(object : Runnable {
            override fun run() {
                when (position) {
                    0, 2, 4 -> image.setImageDrawable(rootView.context.getDrawable(R.drawable.normal_1))
                    1, 3, 6 -> image.setImageDrawable(rootView.context.getDrawable(R.drawable.normal_2))
                    5, 7 -> {
                        image.setImageDrawable(rootView.context.getDrawable(R.drawable.normal_bark))
                        soundPool.play(soundBark, 1F, 1F, 0, 0, 1F)
                    }
                    8 -> {
                        image.setImageDrawable(rootView.context.getDrawable(R.drawable.normal_2))
                        position = 0
                    }
                }
                if (isReady == 2) {
                    pet.hungerLvl -= 1
                    pet.funLvl -= 2
                    if (pet.funLvl < 0) {
                        pet.funLvl = 0
                    }
                    if (pet.hungerLvl < 0) {
                        pet.hungerLvl = 0
                    }
                    (rootView.context as MainActivity).runOnUiThread {
                        showProgress()
                    }
                    isReady = 0
                }
                isReady++
                position++
                if (!checkIfDead()) {
                    threadHandler.postDelayed(this, 500)
                } else {
                    pet.isAlive = false
                    (rootView.context as MainActivity).runOnUiThread {
                        image.setImageDrawable(rootView.context.getDrawable(R.drawable.death))
                    }
                    threadHandler.looper.quitSafely()
                }
            }
        })
    }

    private fun checkIfDead(): Boolean {
        if (pet.hungerLvl == 0) {
            return true
        }
        return false
    }

    private fun showProgress() {
        hungerText.text =
            rootView.context.getString(R.string.hunger) + " " + pet.hungerLvl.toString() +
                    "/" + pet.maxHungerLvl.toString()
        funText.text = rootView.context.getString(R.string.`fun`) + " " + pet.funLvl.toString() +
                "/" + pet.maxFunLvl.toString()
        hungerProgress.max = pet.maxHungerLvl
        hungerProgress.progress = pet.hungerLvl
        funProgress.max = pet.maxFunLvl
        funProgress.progress = pet.funLvl
    }

    private fun formatMoney(): String {
        var finalString = ""
        when (pet.points) {
            in 0..999 -> {
                finalString = pet.points.toString()
            }
            in 1000..999999 -> {
                val money1 = pet.points / 1000
                val money2 = pet.points % 1000
                finalString = money1.toString() + " " + money2.toString()
            }
            in 1000000..999999999 -> {
                val money1 = pet.points / 1000000
                val money2 = (pet.points % 1000000) / 1000
                val money3 = (pet.points % 1000)
                finalString = money1.toString() + " " + money2.toString() + " " + money3.toString()
            }
            in 1000000000..999999999999 -> {
                val money1 = pet.points / 1000000000
                val money2 = (pet.points % 1000000000) / 1000000
                val money3 = (pet.points % 1000000) / 1000
                val money4 = (pet.points % 1000)
                finalString =
                    money1.toString() + " " + money2.toString() + " " + money3.toString() +
                            " " + money4.toString()
            }
        }
        return finalString
    }

}