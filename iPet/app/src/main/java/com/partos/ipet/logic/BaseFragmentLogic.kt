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
                pet.hungerLvl += 5
                if (pet.hungerLvl > pet.maxHungerLvl) {
                    pet.hungerLvl = pet.maxHungerLvl
                }
                showProgress()
            }
        }
        playButton.setOnClickListener {
            if (pet.isAlive) {
                pet.funLvl += 10
                if (pet.funLvl > pet.maxFunLvl) {
                    pet.funLvl = pet.maxFunLvl
                }
                showProgress()
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
    }

    private fun initPet() {
        pet = Pet(100, 100, 100, 100, Look("dog", 1, 1), 0, 0, true)
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
                    pet.hungerLvl -= 11
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

}