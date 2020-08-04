package com.partos.ipet.logic

import android.content.Context
import com.partos.ipet.R

class FormatsHelper() {

    fun formatMoney(moneyToFormat: Long): String {
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

    fun formatAge(age: Long, context: Context): String {
        when (age) {
            in 0..59 -> return age.toString() + " " + context.getString(R.string.seconds)

            in 60..3599 ->
                return (age / 60L).toString() + " " + context.getString(R.string.minutes) +
                        " " + (age % 60L).toString() + " " + context.getString(R.string.seconds)

            in 3600..86399 ->
                return (age / 3600L).toString() + " " + context.getString(R.string.hours) +
                        " " + ((age % 3600L) / 60).toString() + " " + context.getString(R.string.minutes) +
                        " " + ((age % 3600L) % 60).toString() + " " + context.getString(R.string.seconds)

            in 86400..604799 ->
                return (age / 86400L).toString() + " " + context.getString(R.string.days) +
                        " " + ((age % 86400L) / 3600L).toString() + " " +
                        context.getString(R.string.hours) + " " +
                        (((age % 86400L) % 3600L) / 60).toString() + " " +
                        context.getString(R.string.minutes)

            else ->
                return (age / 604800L).toString() + " " + context.getString(R.string.weeks) +
                        " " + ((age % 604800L) / 86400L).toString() + " " +
                        context.getString(R.string.days) + " " +
                        (((age % 604800L) % 86400L) / 3600).toString() + " " +
                        context.getString(R.string.hours)
        }
    }
}