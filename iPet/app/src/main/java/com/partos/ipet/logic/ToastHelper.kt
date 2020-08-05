package com.partos.ipet.logic

import android.content.Context
import android.widget.Toast
import com.partos.ipet.R

class ToastHelper() {

    fun showNoMoneyToast(context: Context) {
        Toast.makeText(
            context,
            context.getString(R.string.toast_not_enough_money),
            Toast.LENGTH_SHORT
        ).show()
    }
}