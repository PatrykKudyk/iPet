package com.partos.ipet.logic

import android.media.AudioAttributes
import android.media.SoundPool
import android.os.Handler
import android.view.View
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import androidx.cardview.widget.CardView
import com.partos.ipet.R
import com.partos.ipet.activities.MainActivity
import com.partos.ipet.models.Look
import com.partos.ipet.models.Pet
import com.partos.ipet.models.UpgradePrices

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
    private lateinit var ageText: TextView
    private lateinit var upgradesCard: CardView
    private lateinit var upgradeFood: CardView
    private lateinit var upgradeFoodMax: CardView
    private lateinit var upgradePlay: CardView
    private lateinit var upgradePlayMax: CardView
    private lateinit var upgradeFoodCost: TextView
    private lateinit var upgradeFoodMaxCost: TextView
    private lateinit var upgradePlayCost: TextView
    private lateinit var upgradePlayMaxCost: TextView


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
                if (pet.maxHungerLvl - pet.hungerLvl >= pet.foodAmount) {
                    pet.points += pet.foodAmount
                } else {
                    pet.points += pet.maxHungerLvl - pet.hungerLvl
                }
                pet.hungerLvl += pet.foodAmount
                if (pet.hungerLvl > pet.maxHungerLvl) {
                    pet.hungerLvl = pet.maxHungerLvl
                }
                showProgress()
                moneyText.text = formatMoney(pet.points)
            }
        }
        playButton.setOnClickListener {
            if (pet.isAlive) {
                if (pet.maxFunLvl - pet.funLvl >= pet.funAmount) {
                    pet.points += pet.funAmount * 2
                } else {
                    pet.points += (pet.maxFunLvl - pet.funLvl) * 2
                }
                pet.funLvl += pet.funAmount
                if (pet.funLvl > pet.maxFunLvl) {
                    pet.funLvl = pet.maxFunLvl
                }
                showProgress()
                moneyText.text = formatMoney(pet.points)
            }
        }
        upgradeButton.setOnClickListener {
            if (upgradesCard.visibility == View.GONE) {
                upgradesCard.visibility = View.VISIBLE
                upgradeFoodCost.text = formatMoney(pet.upgradePrices.hungerAmount)
                upgradeFoodMaxCost.text = formatMoney(pet.upgradePrices.hungerMaxAmount)
                upgradePlayCost.text = formatMoney(pet.upgradePrices.funAmount)
                upgradePlayMaxCost.text = formatMoney(pet.upgradePrices.funMaxAmount)
                upgradeFood.setOnClickListener {
                    if (pet.points >= pet.upgradePrices.hungerAmount) {
                        pet.points -= pet.upgradePrices.hungerAmount
                        pet.upgradePrices.hungerAmount =
                            (pet.upgradePrices.hungerAmount * 1.1).toLong()
                        pet.foodAmount = ((pet.foodAmount * 1.1) + 1).toInt()
                        moneyText.text = formatMoney(pet.points)
                        upgradeFoodCost.text = formatMoney(pet.upgradePrices.hungerAmount)
                        foodText.text = pet.foodAmount.toString()
                    }
                }
                upgradeFoodMax.setOnClickListener {
                    if (pet.points >= pet.upgradePrices.hungerMaxAmount) {
                        pet.points -= pet.upgradePrices.hungerMaxAmount
                        pet.upgradePrices.hungerMaxAmount =
                            (pet.upgradePrices.hungerMaxAmount * 1.1).toLong()
                        pet.maxHungerLvl = ((pet.maxHungerLvl * 1.1) + 1).toInt()
                        moneyText.text = formatMoney(pet.points)
                        upgradeFoodMaxCost.text = formatMoney(pet.upgradePrices.hungerMaxAmount)
                        hungerProgress.max = pet.maxHungerLvl
                        showProgress()
                    }
                }
                upgradePlay.setOnClickListener {
                    if (pet.points >= pet.upgradePrices.funAmount) {
                        pet.points -= pet.upgradePrices.funAmount
                        pet.upgradePrices.funAmount =
                            (pet.upgradePrices.funAmount * 1.1).toLong()
                        pet.funAmount = ((pet.funAmount * 1.1) + 1).toInt()
                        moneyText.text = formatMoney(pet.points)
                        upgradePlayCost.text = formatMoney(pet.upgradePrices.funAmount)
                        playText.text = pet.funAmount.toString()
                    }
                }
                upgradePlayMax.setOnClickListener {
                    if (pet.points >= pet.upgradePrices.funMaxAmount) {
                        pet.points -= pet.upgradePrices.funMaxAmount
                        pet.upgradePrices.funMaxAmount =
                            (pet.upgradePrices.funMaxAmount * 1.1).toLong()
                        pet.maxFunLvl = ((pet.maxFunLvl * 1.1) + 1).toInt()
                        moneyText.text = formatMoney(pet.points)
                        upgradePlayMaxCost.text = formatMoney(pet.upgradePrices.funMaxAmount)
                        funProgress.max = pet.maxFunLvl
                        showProgress()
                    }
                }
            } else {
                upgradesCard.visibility = View.GONE
            }
        }
        image.setOnClickListener {
            pet.isBarking = true
        }
    }

    private fun initViews() {
        image = rootView.findViewById(R.id.dog_image)
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
        ageText = rootView.findViewById(R.id.text_time)
        upgradesCard = rootView.findViewById(R.id.upgrades_card)
        upgradeFood = rootView.findViewById(R.id.upgrade_food)
        upgradeFoodMax = rootView.findViewById(R.id.upgrade_food_max)
        upgradePlay = rootView.findViewById(R.id.upgrade_play)
        upgradePlayMax = rootView.findViewById(R.id.upgrade_play_max)
        upgradeFoodCost = rootView.findViewById(R.id.upgrade_food_cost)
        upgradeFoodMaxCost = rootView.findViewById(R.id.upgrade_food_max_cost)
        upgradePlayCost = rootView.findViewById(R.id.upgrade_play_cost)
        upgradePlayMaxCost = rootView.findViewById(R.id.upgrade_play_max_cost)
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
            100000,
            0,
            true,
            5,
            10,
            UpgradePrices(
                100,
                150,
                70,
                120
            ),
            false
        )
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
        var isBark = true
        foodText.text = pet.foodAmount.toString()
        playText.text = pet.funAmount.toString()
        moneyText.text = formatMoney(pet.points)
        showAge()
        showProgress()
        threadHandler.post(object : Runnable {
            override fun run() {
                if (pet.isBarking) {
                    if (isBark) {
                        position = 10
                    }
                    when (position) {
                        10 -> {
                            image.setImageDrawable(rootView.context.getDrawable(R.drawable.normal_bark))
                            soundPool.play(soundBark, 1F, 1F, 0, 0, 1F)
                            isBark = false
                        }
                        11 -> {
                            image.setImageDrawable(rootView.context.getDrawable(R.drawable.normal_2))
                            position = -1
                            pet.isBarking = false
                            isBark = true
                        }
                    }
                } else {
                    when (position) {
                        0, 2, 4 -> image.setImageDrawable(rootView.context.getDrawable(R.drawable.normal_1))
                        1, 3 -> image.setImageDrawable(rootView.context.getDrawable(R.drawable.normal_2))
                        5 -> {
                            image.setImageDrawable(rootView.context.getDrawable(R.drawable.normal_2))
                            position = -1
                        }
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
                        showAge()
                    }
                    isReady = 0
                    pet.age++
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

    private fun showAge() {
        when (pet.age) {
            in 0..59 -> {
                ageText.text =
                    pet.age.toString() + " " + rootView.context.getString(R.string.seconds)
            }
            in 60..3599 -> {
                ageText.text =
                    (pet.age / 60L).toString() + " " + rootView.context.getString(R.string.minutes) +
                            " " + (pet.age % 60L).toString() + " " + rootView.context.getString(R.string.seconds)
            }
            in 3600..86399 -> {
                ageText.text =
                    (pet.age / 3600L).toString() + " " + rootView.context.getString(R.string.hours) +
                            " " + ((pet.age % 3600L) / 60).toString() + " " + rootView.context.getString(
                        R.string.minutes
                    ) +
                            " " + ((pet.age % 3600L) % 60).toString() + " " + rootView.context.getString(
                        R.string.seconds
                    )
            }
            in 86400..604799 -> {
                ageText.text =
                    (pet.age / 86400L).toString() + " " + rootView.context.getString(R.string.days) +
                            " " + ((pet.age % 86400L) / 3600L).toString() + " " +
                            rootView.context.getString(R.string.hours) + " " +
                            (((pet.age % 86400L) % 3600L) / 60).toString() + " " +
                            rootView.context.getString(R.string.minutes)

            }
            else -> {
                ageText.text =
                    (pet.age / 604800L).toString() + " " + rootView.context.getString(R.string.weeks) +
                            " " + ((pet.age % 604800L) / 86400L).toString() + " " +
                            rootView.context.getString(R.string.days) + " " +
                            (((pet.age % 604800L) % 86400L) / 3600).toString() + " " +
                            rootView.context.getString(R.string.hours)
            }
        }
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

    private fun formatMoney(moneyToFormat: Long): String {
        var moneyForm = ""
        when (moneyToFormat) {
            in 0..999 -> moneyForm = moneyToFormat.toString()
            in 1000..999999 -> {
                val money1 = moneyToFormat / 1000
                var money2 = ""
                if (moneyToFormat % 1000 == 0L) {
                    money2 = "000"
                } else if (moneyToFormat % 1000 < 100) {
                    if (moneyToFormat % 1000 < 10) {
                        money2 = "00" + (moneyToFormat % 100).toString()
                    } else {
                        money2 = "0" + (moneyToFormat % 100).toString()
                    }
                } else {
                    money2 = (moneyToFormat % 1000).toString()
                }
                moneyForm = money1.toString() + "," + money2 + " k"
            }
            in 1000000..999999999 -> {
                val money1 = moneyToFormat / 1000000
                var money = moneyToFormat % 1000000
                money /= 1000
                var money2 = ""
                if (money % 1000 == 0L) {
                    money2 = "000"
                } else if (money % 1000 < 100) {
                    if (money % 1000 < 10) {
                        money2 = "00" + (money % 100).toString()
                    } else {
                        money2 = "0" + (money % 100).toString()
                    }
                } else {
                    money2 = (money % 1000).toString()
                }
                moneyForm = money1.toString() + "," + money2.toString() + " kk"
            }
            in 1000000000..999999999999 -> {
                val money1 = moneyToFormat / 1000000000
                var money = moneyToFormat % 1000000000
                money /= 1000000
                var money2 = ""
                if (money % 1000 == 0L) {
                    money2 = "000"
                } else if (money % 1000 < 100) {
                    if (money % 1000 < 10) {
                        money2 = "00" + (money % 100).toString()
                    } else {
                        money2 = "0" + (money % 100).toString()
                    }
                } else {
                    money2 = (money % 1000).toString()
                }
                moneyForm = money1.toString() + "," + money2 + " kkk"
            }
            in 1000000000000..999999999999999 -> {
                val money1 = moneyToFormat / 1000000000000
                var money = moneyToFormat % 1000000000000
                money /= 1000000000
                var money2 = ""
                if (money % 1000 == 0L) {
                    money2 = "000"
                } else if (money % 1000 < 100) {
                    if (money % 1000 < 10) {
                        money2 = "00" + (money % 100).toString()
                    } else {
                        money2 = "0" + (money % 100).toString()
                    }
                } else {
                    money2 = (money % 1000).toString()
                }
                moneyForm = money1.toString() + "," + money2 + " kkkk"
            }
            in 1000000000000000..999999999999999999 -> {
                val money1 = moneyToFormat / 1000000000000000
                var money = moneyToFormat % 1000000000000000
                money /= 1000000000000
                var money2 = ""
                if (money % 1000 == 0L) {
                    money2 = "000"
                } else if (money % 1000 < 100) {
                    if (money % 1000 < 10) {
                        money2 = "00" + (money % 100).toString()
                    } else {
                        money2 = "0" + (money % 100).toString()
                    }
                } else {
                    money2 = (money % 1000).toString()
                }
                moneyForm = money1.toString() + "," + money2 + " kkkkk"
            }
        }
        return moneyForm
    }

}