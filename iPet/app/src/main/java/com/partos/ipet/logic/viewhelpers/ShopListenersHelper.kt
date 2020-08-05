package com.partos.ipet.logic.viewhelpers

import android.os.Handler
import android.view.View
import android.widget.*
import androidx.cardview.widget.CardView
import androidx.constraintlayout.widget.ConstraintLayout
import com.partos.ipet.MyApp
import com.partos.ipet.R
import com.partos.ipet.db.DataBaseHelper
import com.partos.ipet.logic.MainLoop
import com.partos.ipet.logic.ToastHelper

class ShopListenersHelper () {

    private lateinit var db: DataBaseHelper
    private lateinit var lookButton: ImageView
    private lateinit var upgradesCard: CardView
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


    fun handleShopListeners(rootView: View) {
        initViews(rootView)

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
                    MainLoop().handleMainLoop(rootView)
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
                        MainLoop().handleMainLoop(rootView)
                    }, 1000)
                    shopCard.visibility = View.GONE
                    shopQuestion.visibility = View.GONE
                }
                shopNo.setOnClickListener {
                    shopQuestion.visibility = View.GONE
                    shopChoice.visibility = View.VISIBLE
                }
            } else {
                ToastHelper().showNoMoneyToast(rootView.context)
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
                        MainLoop().handleMainLoop(rootView)
                    }, 1000)
                    shopCard.visibility = View.GONE
                    shopQuestion.visibility = View.GONE
                }
                shopNo.setOnClickListener {
                    shopQuestion.visibility = View.GONE
                    shopChoice.visibility = View.VISIBLE
                }
            } else {
                ToastHelper().showNoMoneyToast(rootView.context)
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
                        MainLoop().handleMainLoop(rootView)
                    }, 1000)
                    shopCard.visibility = View.GONE
                    shopQuestion.visibility = View.GONE
                }
                shopNo.setOnClickListener {
                    shopQuestion.visibility = View.GONE
                    shopChoice.visibility = View.VISIBLE
                }
            } else {
                ToastHelper().showNoMoneyToast(rootView.context)
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
                        MainLoop().handleMainLoop(rootView)
                    }, 1000)
                    shopCard.visibility = View.GONE
                    shopQuestion.visibility = View.GONE
                }
                shopNo.setOnClickListener {
                    shopQuestion.visibility = View.GONE
                    shopChoice.visibility = View.VISIBLE
                }
            } else {
                ToastHelper().showNoMoneyToast(rootView.context)
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
                        MainLoop().handleMainLoop(rootView)
                    }, 1000)
                    shopCard.visibility = View.GONE
                    shopQuestion.visibility = View.GONE
                }
                shopNo.setOnClickListener {
                    shopQuestion.visibility = View.GONE
                    shopChoice.visibility = View.VISIBLE
                }
            } else {
                ToastHelper().showNoMoneyToast(rootView.context)
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
                        MainLoop().handleMainLoop(rootView)
                    }, 1000)
                    shopCard.visibility = View.GONE
                    shopQuestion.visibility = View.GONE
                }
                shopNo.setOnClickListener {
                    shopQuestion.visibility = View.GONE
                    shopChoice.visibility = View.VISIBLE
                }
            } else {
                ToastHelper().showNoMoneyToast(rootView.context)
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
                        MainLoop().handleMainLoop(rootView)
                    }, 1000)
                    shopCard.visibility = View.GONE
                    shopQuestion.visibility = View.GONE
                }
                shopNo.setOnClickListener {
                    shopQuestion.visibility = View.GONE
                    shopChoice.visibility = View.VISIBLE
                }
            } else {
                ToastHelper().showNoMoneyToast(rootView.context)
            }
        }
    }

    private fun activatePet() {
        MyApp.pet.hungerLvl = MyApp.pet.maxHungerLvl
        MyApp.pet.funLvl = MyApp.pet.maxFunLvl
        MyApp.pet.age = 0
        MyApp.pet.isAlive = 1
        db.updatePet(MyApp.pet)
    }


    private fun initViews(rootView: View) {
        db = DataBaseHelper(rootView.context)
        lookButton = rootView.findViewById(R.id.button_look)
        upgradesCard = rootView.findViewById(R.id.upgrades_card)
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
}