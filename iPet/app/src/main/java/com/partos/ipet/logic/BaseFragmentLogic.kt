package com.partos.ipet.logic

import android.app.Activity
import android.icu.util.Calendar
import android.media.AudioAttributes
import android.media.SoundPool
import android.os.Handler
import android.view.View
import android.widget.*
import androidx.cardview.widget.CardView
import androidx.constraintlayout.widget.ConstraintLayout
import com.partos.ipet.MyApp
import com.partos.ipet.R
import com.partos.ipet.activities.MainActivity
import com.partos.ipet.db.DataBaseHelper
import com.partos.ipet.models.Date
import com.partos.ipet.models.Pet

class BaseFragmentLogic(val rootView: View) {


    private lateinit var image: ImageView
    private lateinit var soundPool: SoundPool
    private var soundBark = 0
    private lateinit var date: Date
    private lateinit var db: DataBaseHelper
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
    private lateinit var upgradeFoodIncome: CardView
    private lateinit var upgradeFoodIncomeCost: TextView
    private lateinit var upgradePlayIncome: CardView
    private lateinit var upgradePlayIncomeCost: TextView
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
    private lateinit var foodIncome: TextView
    private lateinit var playIncome: TextView


    fun initFragment() {
        db = DataBaseHelper(rootView.context)
        initViews()
        initSoundPool()
        initListeners()
        image = rootView.findViewById(R.id.dog_image)
        getPet()
        getDate()
        checkDateDiff()
        Handler().postDelayed({
            mainLoop()
        }, 300)
    }

    private fun checkDateDiff() {
        val nowCalendar = Calendar.getInstance()
        val now = Date(
            0,
            nowCalendar.get(Calendar.YEAR),
            nowCalendar.get(Calendar.MONTH),
            nowCalendar.get(Calendar.DAY_OF_MONTH),
            nowCalendar.get(Calendar.HOUR_OF_DAY),
            nowCalendar.get(Calendar.MINUTE),
            nowCalendar.get(Calendar.SECOND)
        )
        val then = db.getDate()[0]
        val diff = DateHelper().getDiffInSeconds(then, now)
        MyApp.pet.hungerLvl -= diff.toInt()
        if (MyApp.pet.hungerLvl < 0) {
            MyApp.pet.hungerLvl = 0
        }
        MyApp.pet.funLvl -= diff.toInt() * 2
        if (MyApp.pet.funLvl < 0) {
            MyApp.pet.funLvl = 0
        }
        if (MyApp.pet.hungerLvl != 0) {
            MyApp.pet.age += diff
        }
        showProgress()
        db.updatePet(MyApp.pet)
    }

    private fun getDate() {
        val today = Calendar.getInstance()
        date = Date(
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
            db.addDate(date)
            date = db.getDate()[0]
        } else {
            date = someDate[0]
        }
    }

    private fun getPet() {
        var somePet = db.getPets()
        if (somePet.size != 0) {
            MyApp.pet = somePet[0]
        } else {
            db.addPet(MyApp.pet)
        }
    }

    private fun initListeners() {
        foodButton.setOnClickListener {
            if (MyApp.pet.isAlive == 1) {
                if (MyApp.pet.maxHungerLvl - MyApp.pet.hungerLvl >= MyApp.pet.foodAmount) {
                    MyApp.pet.points += MyApp.pet.foodAmount * MyApp.pet.foodIncome
                } else {
                    MyApp.pet.points += (MyApp.pet.maxHungerLvl - MyApp.pet.hungerLvl) * MyApp.pet.foodIncome
                }
                MyApp.pet.hungerLvl += MyApp.pet.foodAmount
                if (MyApp.pet.hungerLvl > MyApp.pet.maxHungerLvl) {
                    MyApp.pet.hungerLvl = MyApp.pet.maxHungerLvl
                }
                db.updatePet(MyApp.pet)
                showProgress()
                moneyText.text = formatMoney(MyApp.pet.points)
            }
        }
        playButton.setOnClickListener {
            if (MyApp.pet.isAlive == 1) {
                if (MyApp.pet.maxFunLvl - MyApp.pet.funLvl >= MyApp.pet.funAmount) {
                    MyApp.pet.points += MyApp.pet.funAmount * MyApp.pet.funIncome
                } else {
                    MyApp.pet.points += (MyApp.pet.maxFunLvl - MyApp.pet.funLvl) * MyApp.pet.funIncome
                }
                MyApp.pet.funLvl += MyApp.pet.funAmount
                if (MyApp.pet.funLvl > MyApp.pet.maxFunLvl) {
                    MyApp.pet.funLvl = MyApp.pet.maxFunLvl
                }
                db.updatePet(MyApp.pet)
                showProgress()
                moneyText.text = formatMoney(MyApp.pet.points)
            }
        }
        upgradeButton.setOnClickListener {
            if (shopCard.visibility == View.VISIBLE) {
                shopCard.visibility = View.GONE
            }
            if (upgradesCard.visibility == View.GONE) {
                upgradesCard.visibility = View.VISIBLE
                upgradeFoodCost.text = formatMoney(MyApp.pet.upgradePrices.hungerAmount)
                upgradeFoodMaxCost.text = formatMoney(MyApp.pet.upgradePrices.hungerMaxAmount)
                upgradePlayCost.text = formatMoney(MyApp.pet.upgradePrices.funAmount)
                upgradePlayMaxCost.text = formatMoney(MyApp.pet.upgradePrices.funMaxAmount)
                upgradeFoodIncomeCost.text = formatMoney(MyApp.pet.upgradePrices.hungerIncome)
                upgradePlayIncomeCost.text = formatMoney(MyApp.pet.upgradePrices.funIncome)
            } else {
                upgradesCard.visibility = View.GONE
            }
        }

        upgradeFood.setOnClickListener {
            if (MyApp.pet.points >= MyApp.pet.upgradePrices.hungerAmount) {
                MyApp.pet.points -= MyApp.pet.upgradePrices.hungerAmount
                MyApp.pet.upgradePrices.hungerAmount =
                    (MyApp.pet.upgradePrices.hungerAmount * 1.1).toLong()
                MyApp.pet.foodAmount = ((MyApp.pet.foodAmount * 1.1) + 1).toInt()
                moneyText.text = formatMoney(MyApp.pet.points)
                upgradeFoodCost.text = formatMoney(MyApp.pet.upgradePrices.hungerAmount)
                foodText.text = MyApp.pet.foodAmount.toString()
                db.updatePet(MyApp.pet)
            } else {
                Toast.makeText(
                    rootView.context,
                    rootView.context.getString(R.string.toast_not_enough_money),
                    Toast.LENGTH_SHORT
                ).show()
            }
        }
        upgradeFoodMax.setOnClickListener {
            if (MyApp.pet.points >= MyApp.pet.upgradePrices.hungerMaxAmount) {
                MyApp.pet.points -= MyApp.pet.upgradePrices.hungerMaxAmount
                MyApp.pet.upgradePrices.hungerMaxAmount =
                    (MyApp.pet.upgradePrices.hungerMaxAmount * 1.1).toLong()
                MyApp.pet.maxHungerLvl = ((MyApp.pet.maxHungerLvl * 1.1) + 1).toInt()
                moneyText.text = formatMoney(MyApp.pet.points)
                upgradeFoodMaxCost.text = formatMoney(MyApp.pet.upgradePrices.hungerMaxAmount)
                hungerProgress.max = MyApp.pet.maxHungerLvl
                db.updatePet(MyApp.pet)
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
            if (MyApp.pet.points >= MyApp.pet.upgradePrices.funAmount) {
                MyApp.pet.points -= MyApp.pet.upgradePrices.funAmount
                MyApp.pet.upgradePrices.funAmount =
                    (MyApp.pet.upgradePrices.funAmount * 1.1).toLong()
                MyApp.pet.funAmount = ((MyApp.pet.funAmount * 1.1) + 1).toInt()
                moneyText.text = formatMoney(MyApp.pet.points)
                upgradePlayCost.text = formatMoney(MyApp.pet.upgradePrices.funAmount)
                playText.text = MyApp.pet.funAmount.toString()
                db.updatePet(MyApp.pet)
            } else {
                Toast.makeText(
                    rootView.context,
                    rootView.context.getString(R.string.toast_not_enough_money),
                    Toast.LENGTH_SHORT
                ).show()
            }
        }
        upgradePlayMax.setOnClickListener {
            if (MyApp.pet.points >= MyApp.pet.upgradePrices.funMaxAmount) {
                MyApp.pet.points -= MyApp.pet.upgradePrices.funMaxAmount
                MyApp.pet.upgradePrices.funMaxAmount =
                    (MyApp.pet.upgradePrices.funMaxAmount * 1.1).toLong()
                MyApp.pet.maxFunLvl = ((MyApp.pet.maxFunLvl * 1.1) + 1).toInt()
                moneyText.text = formatMoney(MyApp.pet.points)
                upgradePlayMaxCost.text = formatMoney(MyApp.pet.upgradePrices.funMaxAmount)
                funProgress.max = MyApp.pet.maxFunLvl
                db.updatePet(MyApp.pet)
                showProgress()
            } else {
                Toast.makeText(
                    rootView.context,
                    rootView.context.getString(R.string.toast_not_enough_money),
                    Toast.LENGTH_SHORT
                ).show()
            }
        }
        upgradeFoodIncome.setOnClickListener {
            if (MyApp.pet.points >= MyApp.pet.upgradePrices.hungerIncome) {
                MyApp.pet.points -= MyApp.pet.upgradePrices.hungerIncome
                MyApp.pet.upgradePrices.hungerIncome =
                    (MyApp.pet.upgradePrices.hungerIncome * 1.4).toLong()
                MyApp.pet.foodIncome += 1
                moneyText.text = formatMoney(MyApp.pet.points)
                upgradeFoodIncomeCost.text = formatMoney(MyApp.pet.upgradePrices.hungerIncome)
                foodIncome.text = MyApp.pet.foodIncome.toString()
                db.updatePet(MyApp.pet)
            } else {
                Toast.makeText(
                    rootView.context,
                    rootView.context.getString(R.string.toast_not_enough_money),
                    Toast.LENGTH_SHORT
                ).show()
            }
        }

        upgradePlayIncome.setOnClickListener {
            if (MyApp.pet.points >= MyApp.pet.upgradePrices.funIncome) {
                MyApp.pet.points -= MyApp.pet.upgradePrices.funIncome
                MyApp.pet.upgradePrices.funIncome =
                    (MyApp.pet.upgradePrices.funIncome * 1.4).toLong()
                MyApp.pet.funIncome += 1
                moneyText.text = formatMoney(MyApp.pet.points)
                upgradePlayIncomeCost.text = formatMoney(MyApp.pet.upgradePrices.funIncome)
                playIncome.text = MyApp.pet.funIncome.toString()
                db.updatePet(MyApp.pet)
            } else {
                Toast.makeText(
                    rootView.context,
                    rootView.context.getString(R.string.toast_not_enough_money),
                    Toast.LENGTH_SHORT
                ).show()
            }
        }

        lookButton.setOnClickListener {
            if (upgradesCard.visibility == View.VISIBLE) {
                upgradesCard.visibility = View.GONE
            }
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
                MyApp.pet.hungerLvl = 0
                db.updatePet(MyApp.pet)
                Handler().postDelayed({
                    MyApp.pet.look.petType = "dog"
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
            if (MyApp.pet.points >= 100) {
                shopChoice.visibility = View.GONE
                shopQuestion.visibility = View.VISIBLE
                shopYes.setOnClickListener {
                    MyApp.pet.points -= 100
                    MyApp.pet.hungerLvl = 0
                    db.updatePet(MyApp.pet)
                    Handler().postDelayed({
                        MyApp.pet.look.petType = "cat"
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
            if (MyApp.pet.points >= 300) {
                shopChoice.visibility = View.GONE
                shopQuestion.visibility = View.VISIBLE
                shopYes.setOnClickListener {
                    MyApp.pet.points -= 300
                    MyApp.pet.hungerLvl = 0
                    db.updatePet(MyApp.pet)
                    Handler().postDelayed({
                        MyApp.pet.look.petType = "fish"
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
            if (MyApp.pet.points >= 1000) {
                shopChoice.visibility = View.GONE
                shopQuestion.visibility = View.VISIBLE
                shopYes.setOnClickListener {
                    MyApp.pet.points -= 1000
                    MyApp.pet.hungerLvl = 0
                    db.updatePet(MyApp.pet)
                    Handler().postDelayed({
                        MyApp.pet.look.petType = "frog"
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
            if (MyApp.pet.points >= 5000) {
                shopChoice.visibility = View.GONE
                shopQuestion.visibility = View.VISIBLE
                shopYes.setOnClickListener {
                    MyApp.pet.points -= 5000
                    MyApp.pet.hungerLvl = 0
                    db.updatePet(MyApp.pet)
                    Handler().postDelayed({
                        MyApp.pet.look.petType = "mouse"
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
            if (MyApp.pet.points >= 20000) {
                shopChoice.visibility = View.GONE
                shopQuestion.visibility = View.VISIBLE
                shopYes.setOnClickListener {
                    MyApp.pet.points -= 20000
                    MyApp.pet.hungerLvl = 0
                    db.updatePet(MyApp.pet)
                    Handler().postDelayed({
                        MyApp.pet.look.petType = "rabbit"
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
            if (MyApp.pet.points >= 50000) {
                shopChoice.visibility = View.GONE
                shopQuestion.visibility = View.VISIBLE
                shopYes.setOnClickListener {
                    MyApp.pet.points -= 50000
                    MyApp.pet.hungerLvl = 0
                    db.updatePet(MyApp.pet)
                    Handler().postDelayed({
                        MyApp.pet.look.petType = "panda"
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
            if (MyApp.pet.points >= 100000) {
                shopChoice.visibility = View.GONE
                shopQuestion.visibility = View.VISIBLE
                shopYes.setOnClickListener {
                    MyApp.pet.points -= 100000
                    MyApp.pet.hungerLvl = 0
                    db.updatePet(MyApp.pet)
                    Handler().postDelayed({
                        MyApp.pet.look.petType = "penguin"
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
        MyApp.pet.hungerLvl = 100
        MyApp.pet.funLvl = 100
        MyApp.pet.age = 0
        MyApp.pet.isAlive = 1
        db.updatePet(MyApp.pet)
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
        upgradeFoodIncome = rootView.findViewById(R.id.upgrade_food_income)
        upgradeFoodIncomeCost = rootView.findViewById(R.id.upgrade_food_income_cost)
        upgradePlayIncome = rootView.findViewById(R.id.upgrade_play_income)
        upgradePlayIncomeCost = rootView.findViewById(R.id.upgrade_play_income_cost)
        foodIncome = rootView.findViewById(R.id.text_food_income)
        playIncome = rootView.findViewById(R.id.text_play_income)
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
            foodText.text = MyApp.pet.foodAmount.toString()
            playText.text = MyApp.pet.funAmount.toString()
            moneyText.text = formatMoney(MyApp.pet.points)
            foodIncome.text = MyApp.pet.foodIncome.toString()
            playIncome.text = MyApp.pet.funIncome.toString()
            val activity = (rootView.context as MainActivity)
            initIcons(activity)
            showAge()
            showProgress()
            threadHandler.post(object : Runnable {
                override fun run() {
                    when (position) {
                        0, 2, 4 -> {
                            when (MyApp.pet.look.petType) {
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
                            when (MyApp.pet.look.petType) {
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
                            when (MyApp.pet.look.petType) {
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
                        MyApp.pet.hungerLvl -= 1
                        MyApp.pet.funLvl -= 2
                        if (MyApp.pet.funLvl < 0) {
                            MyApp.pet.funLvl = 0
                        }
                        if (MyApp.pet.hungerLvl < 0) {
                            MyApp.pet.hungerLvl = 0
                        }
                        (rootView.context as MainActivity).runOnUiThread {
                            showProgress()
                            showAge()
                        }
                        isReady = 0
                        MyApp.pet.age++
                        updateDate()
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

    private fun updateDate() {
        var currentDate = Calendar.getInstance()
        date.year = currentDate.get(Calendar.YEAR)
        date.month = currentDate.get(Calendar.MONTH)
        date.day = currentDate.get(Calendar.DAY_OF_MONTH)
        date.hour = currentDate.get(Calendar.HOUR_OF_DAY)
        date.minute = currentDate.get(Calendar.MINUTE)
        date.second = currentDate.get(Calendar.SECOND)
        db.updateDate(date)
    }

    private fun initIcons(activity: Activity) {
        when (MyApp.pet.look.petType) {
            "dog" -> activity.runOnUiThread {
                foodButton.setImageDrawable(rootView.context.getDrawable(R.drawable.dog_food))
            }
            "cat" -> activity.runOnUiThread {
                foodButton.setImageDrawable(rootView.context.getDrawable(R.drawable.cat_food))
            }
            "fish" -> activity.runOnUiThread {
                foodButton.setImageDrawable(rootView.context.getDrawable(R.drawable.fish_food))
            }
            "frog" -> activity.runOnUiThread {
                foodButton.setImageDrawable(rootView.context.getDrawable(R.drawable.bug))
            }
            "mouse" -> activity.runOnUiThread {
                foodButton.setImageDrawable(rootView.context.getDrawable(R.drawable.cheese))
            }
            "rabbit" -> activity.runOnUiThread {
                foodButton.setImageDrawable(rootView.context.getDrawable(R.drawable.carrot))
            }
            "panda" -> activity.runOnUiThread {
                foodButton.setImageDrawable(rootView.context.getDrawable(R.drawable.bamboo))
            }
            "penguin" -> activity.runOnUiThread {
                foodButton.setImageDrawable(rootView.context.getDrawable(R.drawable.fish))
            }
        }
    }

    private fun showAge() {
        when (MyApp.pet.age) {
            in 0..59 -> {
                ageText.text =
                    MyApp.pet.age.toString() + " " + rootView.context.getString(R.string.seconds)
            }
            in 60..3599 -> {
                ageText.text =
                    (MyApp.pet.age / 60L).toString() + " " + rootView.context.getString(R.string.minutes) +
                            " " + (MyApp.pet.age % 60L).toString() + " " + rootView.context.getString(R.string.seconds)
            }
            in 3600..86399 -> {
                ageText.text =
                    (MyApp.pet.age / 3600L).toString() + " " + rootView.context.getString(R.string.hours) +
                            " " + ((MyApp.pet.age % 3600L) / 60).toString() + " " + rootView.context.getString(
                        R.string.minutes
                    ) +
                            " " + ((MyApp.pet.age % 3600L) % 60).toString() + " " + rootView.context.getString(
                        R.string.seconds
                    )
            }
            in 86400..604799 -> {
                ageText.text =
                    (MyApp.pet.age / 86400L).toString() + " " + rootView.context.getString(R.string.days) +
                            " " + ((MyApp.pet.age % 86400L) / 3600L).toString() + " " +
                            rootView.context.getString(R.string.hours) + " " +
                            (((MyApp.pet.age % 86400L) % 3600L) / 60).toString() + " " +
                            rootView.context.getString(R.string.minutes)

            }
            else -> {
                ageText.text =
                    (MyApp.pet.age / 604800L).toString() + " " + rootView.context.getString(R.string.weeks) +
                            " " + ((MyApp.pet.age % 604800L) / 86400L).toString() + " " +
                            rootView.context.getString(R.string.days) + " " +
                            (((MyApp.pet.age % 604800L) % 86400L) / 3600).toString() + " " +
                            rootView.context.getString(R.string.hours)
            }
        }
    }

    private fun checkIfDead(): Boolean {
        if (MyApp.pet.hungerLvl == 0) {
            return true
        }
        return false
    }

    private fun showProgress() {
        hungerText.text =
            rootView.context.getString(R.string.hunger) + " " + MyApp.pet.hungerLvl.toString() +
                    "/" + MyApp.pet.maxHungerLvl.toString()
        funText.text = rootView.context.getString(R.string.`fun`) + " " + MyApp.pet.funLvl.toString() +
                "/" + MyApp.pet.maxFunLvl.toString()
        hungerProgress.max = MyApp.pet.maxHungerLvl
        hungerProgress.progress = MyApp.pet.hungerLvl
        funProgress.max = MyApp.pet.maxFunLvl
        funProgress.progress = MyApp.pet.funLvl
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