package com.partos.ipet.logic

import android.media.AudioAttributes
import android.media.SoundPool
import android.os.Handler
import android.view.View
import android.widget.*
import androidx.cardview.widget.CardView
import androidx.constraintlayout.widget.ConstraintLayout
import com.partos.ipet.R
import com.partos.ipet.activities.MainActivity
import com.partos.ipet.models.Look
import com.partos.ipet.models.Pet
import com.partos.ipet.models.UpgradePrices

class BaseFragmentLogic(val rootView: View) {


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
    private lateinit var shopCard: CardView
    private lateinit var shopCard1: CardView
    private lateinit var shopCard2: CardView
    private lateinit var shopCard3: CardView
    private lateinit var shopCard4: CardView
    private lateinit var shopCard5: CardView
    private lateinit var shopCard6: CardView
    private lateinit var shopCard7: CardView
    private lateinit var shopCard8: CardView
    private lateinit var shopChoice: LinearLayout
    private lateinit var shopQuestion: ConstraintLayout
    private lateinit var shopYes: Button
    private lateinit var shopNo: Button


    fun initFragment() {
        initViews()
        initSoundPool()
        initPet()
        initListeners()
        image = rootView.findViewById(R.id.dog_image)
        Handler().postDelayed({
            mainLoop()
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
            } else {
                upgradesCard.visibility = View.GONE
            }
        }

        upgradeFood.setOnClickListener {
            if (pet.points >= pet.upgradePrices.hungerAmount) {
                pet.points -= pet.upgradePrices.hungerAmount
                pet.upgradePrices.hungerAmount =
                    (pet.upgradePrices.hungerAmount * 1.1).toLong()
                pet.foodAmount = ((pet.foodAmount * 1.1) + 1).toInt()
                moneyText.text = formatMoney(pet.points)
                upgradeFoodCost.text = formatMoney(pet.upgradePrices.hungerAmount)
                foodText.text = pet.foodAmount.toString()
            } else {
                Toast.makeText(
                    rootView.context,
                    rootView.context.getString(R.string.toast_not_enough_money),
                    Toast.LENGTH_SHORT
                ).show()
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
            } else {
                Toast.makeText(
                    rootView.context,
                    rootView.context.getString(R.string.toast_not_enough_money),
                    Toast.LENGTH_SHORT
                ).show()
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
            } else {
                Toast.makeText(
                    rootView.context,
                    rootView.context.getString(R.string.toast_not_enough_money),
                    Toast.LENGTH_SHORT
                ).show()
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
            } else {
                Toast.makeText(
                    rootView.context,
                    rootView.context.getString(R.string.toast_not_enough_money),
                    Toast.LENGTH_SHORT
                ).show()
            }
        }

        lookButton.setOnClickListener {
            if (shopCard.visibility == View.GONE) {
                shopCard.visibility = View.VISIBLE
                shopChoice.visibility = View.VISIBLE
            } else {
                shopCard.visibility = View.GONE
                shopChoice.visibility = View.VISIBLE
            }
        }
        shopCard1.setOnClickListener {
            shopChoice.visibility = View.GONE
            shopQuestion.visibility = View.VISIBLE
            shopYes.setOnClickListener {
                pet.look.petType = "dog"
                pet.hungerLvl = 0
                Handler().postDelayed({
                    activatePet()
                    mainLoop()
                }, 1000)
                shopCard.visibility = View.GONE
                shopQuestion.visibility = View.GONE
            }
            shopNo.setOnClickListener {
                shopQuestion.visibility = View.GONE
                shopChoice.visibility = View.VISIBLE
            }
        }
        shopCard2.setOnClickListener {
            if (pet.points >= 100) {
                shopChoice.visibility = View.GONE
                shopQuestion.visibility = View.VISIBLE
                shopYes.setOnClickListener {
                    pet.points -= 100
                    pet.look.petType = "cat"
                    pet.hungerLvl = 0
                    Handler().postDelayed({
                        activatePet()
                        mainLoop()
                    }, 1000)
                    shopCard.visibility = View.GONE
                    shopQuestion.visibility = View.GONE
                }
                shopNo.setOnClickListener {
                    shopQuestion.visibility = View.GONE
                    shopChoice.visibility = View.VISIBLE
                }
            } else {
                Toast.makeText(
                    rootView.context,
                    rootView.context.getString(R.string.toast_not_enough_money),
                    Toast.LENGTH_SHORT
                ).show()
            }
        }
        shopCard3.setOnClickListener {
            if (pet.points >= 300) {
                shopChoice.visibility = View.GONE
                shopQuestion.visibility = View.VISIBLE
                shopYes.setOnClickListener {
                    pet.points -= 300
                    pet.look.petType = "fish"
                    pet.hungerLvl = 0
                    Handler().postDelayed({
                        activatePet()
                        mainLoop()
                    }, 1000)
                    shopCard.visibility = View.GONE
                    shopQuestion.visibility = View.GONE
                }
                shopNo.setOnClickListener {
                    shopQuestion.visibility = View.GONE
                    shopChoice.visibility = View.VISIBLE
                }
            } else {
                Toast.makeText(
                    rootView.context,
                    rootView.context.getString(R.string.toast_not_enough_money),
                    Toast.LENGTH_SHORT
                ).show()
            }
        }

        shopCard4.setOnClickListener {
            if (pet.points >= 1000) {
                shopChoice.visibility = View.GONE
                shopQuestion.visibility = View.VISIBLE
                shopYes.setOnClickListener {
                    pet.points -= 1000
                    pet.look.petType = "frog"
                    pet.hungerLvl = 0
                    Handler().postDelayed({
                        activatePet()
                        mainLoop()
                    }, 1000)
                    shopCard.visibility = View.GONE
                    shopQuestion.visibility = View.GONE
                }
                shopNo.setOnClickListener {
                    shopQuestion.visibility = View.GONE
                    shopChoice.visibility = View.VISIBLE
                }
            } else {
                Toast.makeText(
                    rootView.context,
                    rootView.context.getString(R.string.toast_not_enough_money),
                    Toast.LENGTH_SHORT
                ).show()
            }
        }
        shopCard5.setOnClickListener {
            if (pet.points >= 5000) {
                shopChoice.visibility = View.GONE
                shopQuestion.visibility = View.VISIBLE
                shopYes.setOnClickListener {
                    pet.points -= 5000
                    pet.look.petType = "mouse"
                    pet.hungerLvl = 0
                    Handler().postDelayed({
                        activatePet()
                        mainLoop()
                    }, 1000)
                    shopCard.visibility = View.GONE
                    shopQuestion.visibility = View.GONE
                }
                shopNo.setOnClickListener {
                    shopQuestion.visibility = View.GONE
                    shopChoice.visibility = View.VISIBLE
                }
            } else {
                Toast.makeText(
                    rootView.context,
                    rootView.context.getString(R.string.toast_not_enough_money),
                    Toast.LENGTH_SHORT
                ).show()
            }
        }
        shopCard6.setOnClickListener {
            if (pet.points >= 20000) {
                shopChoice.visibility = View.GONE
                shopQuestion.visibility = View.VISIBLE
                shopYes.setOnClickListener {
                    pet.points -= 20000
                    pet.look.petType = "rabbit"
                    pet.hungerLvl = 0
                    Handler().postDelayed({
                        activatePet()
                        mainLoop()
                    }, 1000)
                    shopCard.visibility = View.GONE
                    shopQuestion.visibility = View.GONE
                }
                shopNo.setOnClickListener {
                    shopQuestion.visibility = View.GONE
                    shopChoice.visibility = View.VISIBLE
                }
            } else {
                Toast.makeText(
                    rootView.context,
                    rootView.context.getString(R.string.toast_not_enough_money),
                    Toast.LENGTH_SHORT
                ).show()
            }
        }
        shopCard7.setOnClickListener {
            if (pet.points >= 50000) {
                shopChoice.visibility = View.GONE
                shopQuestion.visibility = View.VISIBLE
                shopYes.setOnClickListener {
                    pet.points -= 50000
                    pet.look.petType = "panda"
                    pet.hungerLvl = 0
                    Handler().postDelayed({
                        activatePet()
                        mainLoop()
                    }, 1000)
                    shopCard.visibility = View.GONE
                    shopQuestion.visibility = View.GONE
                }
                shopNo.setOnClickListener {
                    shopQuestion.visibility = View.GONE
                    shopChoice.visibility = View.VISIBLE
                }
            } else {
                Toast.makeText(
                    rootView.context,
                    rootView.context.getString(R.string.toast_not_enough_money),
                    Toast.LENGTH_SHORT
                ).show()
            }
        }
        shopCard8.setOnClickListener {
            if (pet.points >= 100000) {
                shopChoice.visibility = View.GONE
                shopQuestion.visibility = View.VISIBLE
                shopYes.setOnClickListener {
                    pet.points -= 100000
                    pet.look.petType = "penguin"
                    pet.hungerLvl = 0
                    Handler().postDelayed({
                        activatePet()
                        mainLoop()
                    }, 1000)
                    shopCard.visibility = View.GONE
                    shopQuestion.visibility = View.GONE
                }
                shopNo.setOnClickListener {
                    shopQuestion.visibility = View.GONE
                    shopChoice.visibility = View.VISIBLE
                }
            } else {
                Toast.makeText(
                    rootView.context,
                    rootView.context.getString(R.string.toast_not_enough_money),
                    Toast.LENGTH_SHORT
                ).show()
            }
        }
    }

    private fun activatePet() {
        pet.hungerLvl = 100
        pet.maxHungerLvl = 100
        pet.funLvl = 100
        pet.maxFunLvl = 100
        pet.age = 0
        pet.isAlive = true
        pet.foodAmount = 10
        pet.funAmount = 10
        pet.upgradePrices.hungerAmount = 100
        pet.upgradePrices.hungerMaxAmount = 150
        pet.upgradePrices.funAmount = 70
        pet.upgradePrices.funMaxAmount = 120
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
        shopCard = rootView.findViewById(R.id.shop_card)
        shopCard1 = rootView.findViewById(R.id.shop_card_1)
        shopCard2 = rootView.findViewById(R.id.shop_card_2)
        shopCard3 = rootView.findViewById(R.id.shop_card_3)
        shopCard4 = rootView.findViewById(R.id.shop_card_4)
        shopCard5 = rootView.findViewById(R.id.shop_card_5)
        shopCard6 = rootView.findViewById(R.id.shop_card_6)
        shopCard7 = rootView.findViewById(R.id.shop_card_7)
        shopCard8 = rootView.findViewById(R.id.shop_card_8)
        shopChoice = rootView.findViewById(R.id.shop_normal)
        shopQuestion = rootView.findViewById(R.id.shop_question)
        shopYes = rootView.findViewById(R.id.shop_button_yes)
        shopNo = rootView.findViewById(R.id.shop_button_no)
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
            10,
            UpgradePrices(
                100,
                150,
                70,
                120
            )
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

    private fun mainLoop() {
        var looperThread = TimerThread()
        looperThread.start()
        Handler().postDelayed({
            var threadHandler = Handler(looperThread.looper)
            var position = 0
            var isReady = 0
            foodText.text = pet.foodAmount.toString()
            playText.text = pet.funAmount.toString()
            moneyText.text = formatMoney(pet.points)
            val activity = (rootView.context as MainActivity)
            showAge()
            showProgress()
            threadHandler.post(object : Runnable {
                override fun run() {
                    when (position) {
                        0, 2, 4 -> {
                            when (pet.look.petType) {
                                "dog" -> activity.runOnUiThread {
                                    image.setImageDrawable(rootView.context.getDrawable(R.drawable.dog_normal))
                                }
                                "cat" -> activity.runOnUiThread {
                                    image.setImageDrawable(rootView.context.getDrawable(R.drawable.cat_normal))
                                }
                                "fish" -> activity.runOnUiThread {
                                    image.setImageDrawable(rootView.context.getDrawable(R.drawable.fish_normal))
                                }
                                "frog" -> activity.runOnUiThread {
                                    image.setImageDrawable(rootView.context.getDrawable(R.drawable.frog_normal))
                                }
                                "mouse" -> activity.runOnUiThread {
                                    image.setImageDrawable(rootView.context.getDrawable(R.drawable.mouse_normal))
                                }
                                "rabbit" -> activity.runOnUiThread {
                                    image.setImageDrawable(rootView.context.getDrawable(R.drawable.rabbit_normal))
                                }
                                "panda" -> activity.runOnUiThread {
                                    image.setImageDrawable(rootView.context.getDrawable(R.drawable.panda_normal))
                                }
                                "penguin" -> activity.runOnUiThread {
                                    image.setImageDrawable(rootView.context.getDrawable(R.drawable.penguin_normal))
                                }
                            }
                        }
                        1, 3 -> {
                            when (pet.look.petType) {
                                "dog" -> activity.runOnUiThread {
                                    image.setImageDrawable(rootView.context.getDrawable(R.drawable.dog_normal_2))
                                }
                                "cat" -> activity.runOnUiThread {
                                    image.setImageDrawable(rootView.context.getDrawable(R.drawable.cat_normal_2))
                                }
                                "fish" -> activity.runOnUiThread {
                                    image.setImageDrawable(rootView.context.getDrawable(R.drawable.fish_normal_2))
                                }
                                "frog" -> activity.runOnUiThread {
                                    image.setImageDrawable(rootView.context.getDrawable(R.drawable.frog_normal_2))
                                }
                                "mouse" -> activity.runOnUiThread {
                                    image.setImageDrawable(rootView.context.getDrawable(R.drawable.mouse_normal_2))
                                }
                                "rabbit" -> activity.runOnUiThread {
                                    image.setImageDrawable(rootView.context.getDrawable(R.drawable.rabbit_normal_2))
                                }
                                "panda" -> activity.runOnUiThread {
                                    image.setImageDrawable(rootView.context.getDrawable(R.drawable.panda_normal_2))
                                }
                                "penguin" -> activity.runOnUiThread {
                                    image.setImageDrawable(rootView.context.getDrawable(R.drawable.penguin_normal_2))
                                }
                            }
                        }
                        5 -> {
                            when (pet.look.petType) {
                                "dog" -> activity.runOnUiThread {
                                    image.setImageDrawable(rootView.context.getDrawable(R.drawable.dog_normal_2))
                                }
                                "cat" -> activity.runOnUiThread {
                                    image.setImageDrawable(rootView.context.getDrawable(R.drawable.cat_normal_2))
                                }
                                "fish" -> activity.runOnUiThread {
                                    image.setImageDrawable(rootView.context.getDrawable(R.drawable.fish_normal_2))
                                }
                                "frog" -> activity.runOnUiThread {
                                    image.setImageDrawable(rootView.context.getDrawable(R.drawable.frog_normal_2))
                                }
                                "mouse" -> activity.runOnUiThread {
                                    image.setImageDrawable(rootView.context.getDrawable(R.drawable.mouse_normal_2))
                                }
                                "rabbit" -> activity.runOnUiThread {
                                    image.setImageDrawable(rootView.context.getDrawable(R.drawable.rabbit_normal_2))
                                }
                                "panda" -> activity.runOnUiThread {
                                    image.setImageDrawable(rootView.context.getDrawable(R.drawable.panda_normal_2))
                                }
                                "penguin" -> activity.runOnUiThread {
                                    image.setImageDrawable(rootView.context.getDrawable(R.drawable.penguin_normal_2))
                                }
                            }
                            position = -1
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
        }, 300)
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