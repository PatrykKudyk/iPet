package com.partos.ipet.logic

import android.app.Activity
import android.icu.util.Calendar
import android.os.Handler
import android.view.View
import android.widget.*
import androidx.cardview.widget.CardView
import androidx.constraintlayout.widget.ConstraintLayout
import com.partos.ipet.MyApp
import com.partos.ipet.R
import com.partos.ipet.activities.MainActivity
import com.partos.ipet.db.DataBaseHelper
import com.partos.ipet.models.*

class BaseFragmentLogic(val rootView: View) {


    private lateinit var image: ImageView
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


    fun initFragment() {
        db = DataBaseHelper(rootView.context)
        initViews()
        initBallsArray()
        initListeners()
        getPet()
        getDate()
        checkDateDiff()
        Handler().postDelayed({
            mainLoop()
        }, 300)
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
        PetHelper().setTimeDiff(diff)
        showProgress()
        db.updatePet(MyApp.pet)
//        val now = DateHelper().getNowDate()
//        val then = db.getDate()[0]
//        PetHelper().setTimeDiff(DateHelper().getDiffInSeconds(then, now))
//        showProgress()
//        db.updatePet(MyApp.pet)
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

//        date = DateHelper().getNowDate()
//        val someDate = db.getDate()
//        if (someDate.size == 0) {
//            db.addDate(date)
//            date = db.getDate()[0]
//        } else {
//            date = someDate[0]
//        }
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
                100000,
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

    private fun initListeners() {
        foodButton.setOnClickListener {
            if (MyApp.pet.isAlive == 1) {
                PetHelper().handleFoodButton()
                db.updatePet(MyApp.pet)
                showProgress()
                moneyText.text = FormatsHelper().formatMoney(MyApp.pet.points)
            }
        }

        playButton.setOnClickListener {
            if (MyApp.pet.isAlive == 1) {
                PetHelper().handleFunButton()
                AnimationsHelper().animateBall(rootView.context)
                db.updatePet(MyApp.pet)
                showProgress()
                moneyText.text = FormatsHelper().formatMoney(MyApp.pet.points)
            }
        }

        upgradeButton.setOnClickListener {
            if (shopCard.visibility == View.VISIBLE) {
                shopCard.visibility = View.GONE
            }
            if (upgradesCard.visibility == View.GONE) {
                upgradesCard.visibility = View.VISIBLE
                upgradeFoodCost.text =
                    FormatsHelper().formatMoney(MyApp.pet.upgradePrices.hungerAmount)
                upgradeFoodMaxCost.text =
                    FormatsHelper().formatMoney(MyApp.pet.upgradePrices.hungerMaxAmount)
                upgradePlayCost.text =
                    FormatsHelper().formatMoney(MyApp.pet.upgradePrices.funAmount)
                upgradePlayMaxCost.text =
                    FormatsHelper().formatMoney(MyApp.pet.upgradePrices.funMaxAmount)
                upgradeFoodIncomeCost.text =
                    FormatsHelper().formatMoney(MyApp.pet.upgradePrices.hungerIncome)
                upgradePlayIncomeCost.text =
                    FormatsHelper().formatMoney(MyApp.pet.upgradePrices.funIncome)
            } else {
                upgradesCard.visibility = View.GONE
            }
        }

        upgradeFood.setOnClickListener {
            if (MyApp.pet.points >= MyApp.pet.upgradePrices.hungerAmount) {
                PetHelper().handleUpgradeFood()
                moneyText.text = FormatsHelper().formatMoney(MyApp.pet.points)
                upgradeFoodCost.text =
                    FormatsHelper().formatMoney(MyApp.pet.upgradePrices.hungerAmount)
                foodText.text = MyApp.pet.foodAmount.toString()
                db.updatePet(MyApp.pet)
            } else {
                showNoMoneyToast()
            }
        }

        upgradeFoodMax.setOnClickListener {
            if (MyApp.pet.points >= MyApp.pet.upgradePrices.hungerMaxAmount) {
                PetHelper().handleUpgradeFoodMax()
                moneyText.text = FormatsHelper().formatMoney(MyApp.pet.points)
                upgradeFoodMaxCost.text =
                    FormatsHelper().formatMoney(MyApp.pet.upgradePrices.hungerMaxAmount)
                hungerProgress.max = MyApp.pet.maxHungerLvl
                db.updatePet(MyApp.pet)
                showProgress()
            } else {
                showNoMoneyToast()
            }
        }

        upgradePlay.setOnClickListener {
            if (MyApp.pet.points >= MyApp.pet.upgradePrices.funAmount) {
                PetHelper().handleUpgradePlay()
                moneyText.text = FormatsHelper().formatMoney(MyApp.pet.points)
                upgradePlayCost.text =
                    FormatsHelper().formatMoney(MyApp.pet.upgradePrices.funAmount)
                playText.text = MyApp.pet.funAmount.toString()
                db.updatePet(MyApp.pet)
            } else {
                showNoMoneyToast()
            }
        }

        upgradePlayMax.setOnClickListener {
            if (MyApp.pet.points >= MyApp.pet.upgradePrices.funMaxAmount) {
                PetHelper().handleUpgradePlayMax()
                moneyText.text = FormatsHelper().formatMoney(MyApp.pet.points)
                upgradePlayMaxCost.text =
                    FormatsHelper().formatMoney(MyApp.pet.upgradePrices.funMaxAmount)
                funProgress.max = MyApp.pet.maxFunLvl
                db.updatePet(MyApp.pet)
                showProgress()
            } else {
                showNoMoneyToast()
            }
        }

        upgradeFoodIncome.setOnClickListener {
            if (MyApp.pet.points >= MyApp.pet.upgradePrices.hungerIncome) {
                PetHelper().handleUpgradeFoodIncome()
                moneyText.text = FormatsHelper().formatMoney(MyApp.pet.points)
                upgradeFoodIncomeCost.text =
                    FormatsHelper().formatMoney(MyApp.pet.upgradePrices.hungerIncome)
                foodIncome.text = MyApp.pet.foodIncome.toString()
                db.updatePet(MyApp.pet)
            } else {
                showNoMoneyToast()
            }
        }

        upgradePlayIncome.setOnClickListener {
            if (MyApp.pet.points >= MyApp.pet.upgradePrices.funIncome) {
                PetHelper().handleUpgradePlayIncome()
                moneyText.text = FormatsHelper().formatMoney(MyApp.pet.points)
                upgradePlayIncomeCost.text =
                    FormatsHelper().formatMoney(MyApp.pet.upgradePrices.funIncome)
                playIncome.text = MyApp.pet.funIncome.toString()
                db.updatePet(MyApp.pet)
            } else {
                showNoMoneyToast()
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
                showNoMoneyToast()
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
                showNoMoneyToast()
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
                showNoMoneyToast()
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
                showNoMoneyToast()
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
                showNoMoneyToast()
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
                showNoMoneyToast()
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
                showNoMoneyToast()
            }
        }
    }


    private fun showNoMoneyToast() {
        Toast.makeText(
            rootView.context,
            rootView.context.getString(R.string.toast_not_enough_money),
            Toast.LENGTH_SHORT
        ).show()
    }

    private fun activatePet() {
        MyApp.pet.hungerLvl = MyApp.pet.maxHungerLvl
        MyApp.pet.funLvl = MyApp.pet.maxFunLvl
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

    private fun mainLoop() {
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
            initIcons(activity)
            showAge()
            showProgress()
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
                            showProgress()
                            showAge()
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

    private fun updateDate() {
        var currentDate = Calendar.getInstance()
        date.year = currentDate.get(Calendar.YEAR)
        date.month = currentDate.get(Calendar.MONTH)
        date.day = currentDate.get(Calendar.DAY_OF_MONTH)
        date.hour = currentDate.get(Calendar.HOUR_OF_DAY)
        date.minute = currentDate.get(Calendar.MINUTE)
        date.second = currentDate.get(Calendar.SECOND)
        db.updateDate(date)

//        date = DateHelper().getNowDate()
//        db.updateDate(date)
    }

    private fun initIcons(activity: Activity) {
        PetIconsHelper().setFoodIcon(foodButton, rootView.context, activity, MyApp.pet.look.petType)
    }

    private fun showAge() {
        ageText.text = FormatsHelper().formatAge(MyApp.pet.age, rootView.context)
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
        funText.text =
            rootView.context.getString(R.string.`fun`) + " " + MyApp.pet.funLvl.toString() +
                    "/" + MyApp.pet.maxFunLvl.toString()
        hungerProgress.max = MyApp.pet.maxHungerLvl
        hungerProgress.progress = MyApp.pet.hungerLvl
        funProgress.max = MyApp.pet.maxFunLvl
        funProgress.progress = MyApp.pet.funLvl
    }

}