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
import com.partos.ipet.logic.viewhelpers.BaseListenersHelper
import com.partos.ipet.logic.viewhelpers.ProgressViews
import com.partos.ipet.logic.viewhelpers.ShopListenersHelper
import com.partos.ipet.logic.viewhelpers.UpgradeListenersHelper
import com.partos.ipet.models.*

class BaseFragmentLogic(val rootView: View) {

    private lateinit var db: DataBaseHelper

    fun initFragment() {
        db = DataBaseHelper(rootView.context)
        MyAppInitHelper().initMyApp(rootView)
        initListeners()
        checkDateDiff()
        Handler().postDelayed({
            MainLoop().handleMainLoop(rootView)
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
        PetHelper().setTimeDiff(diff)
        ProgressViews().showProgress(rootView.context, rootView)
        db.updatePet(MyApp.pet)
    }


    private fun initListeners() {
        BaseListenersHelper().handleBaseListeners(rootView)
        UpgradeListenersHelper().handleUpgradeListeners(rootView)
        ShopListenersHelper().handleShopListeners(rootView)
    }

}