package com.partos.ipet.db


import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.provider.BaseColumns
import com.partos.ipet.models.Date
import com.partos.ipet.models.Look
import com.partos.ipet.models.Pet
import com.partos.ipet.models.UpgradePrices

object TableInfo : BaseColumns {
    const val DATABASE_NAME = "iPet"
    const val TABLE_NAME_PET = "pet"
    const val TABLE_COLUMN_PET_HUNGER_LVL = "hungerLevel"
    const val TABLE_COLUMN_PET_HUNGER_LVL_MAX = "hungerLevelMax"
    const val TABLE_COLUMN_PET_FUN_LVL = "funLevel"
    const val TABLE_COLUMN_PET_FUN_LVL_MAX = "funLevelMax"
    const val TABLE_COLUMN_PET_LOOK_TYPE = "lookType"
    const val TABLE_COLUMN_PET_LOOK_COLOR = "lookColor"
    const val TABLE_COLUMN_PET_LOOK_COLLAR = "lookCollar"
    const val TABLE_COLUMN_PET_POINTS = "points"
    const val TABLE_COLUMN_PET_AGE = "age"
    const val TABLE_COLUMN_PET_IS_ALIVE = "isAlive"
    const val TABLE_COLUMN_PET_FOOD_AMOUNT = "foodAmount"
    const val TABLE_COLUMN_PET_FUN_AMOUNT = "funAmount"
    const val TABLE_COLUMN_PET_UPGRADES_HUNGER_AMOUNT = "upgradesHungerAmount"
    const val TABLE_COLUMN_PET_UPGRADES_HUNGER_AMOUNT_MAX = "upgradesHungerAmountMax"
    const val TABLE_COLUMN_PET_UPGRADES_FUN_AMOUNT = "upgradesFunAmount"
    const val TABLE_COLUMN_PET_UPGRADES_FUN_AMOUNT_MAX = "upgradesFunAmountMax"
    const val TABLE_NAME_DATE = "date"
    const val TABLE_COLUMN_DATE_YEAR = "year"
    const val TABLE_COLUMN_DATE_MONTH = "month"
    const val TABLE_COLUMN_DATE_DAY = "day"
    const val TABLE_COLUMN_DATE_HOUR = "hour"
    const val TABLE_COLUMN_DATE_MINUTE = "minute"
    const val TABLE_COLUMN_DATE_SECOND = "second"


}

object BasicCommand {
    const val SQL_CREATE_TABLE_PET =
        "CREATE TABLE ${TableInfo.TABLE_NAME_PET} (" +
                "${BaseColumns._ID} INTEGER PRIMARY KEY," +
                "${TableInfo.TABLE_COLUMN_PET_HUNGER_LVL} INTEGER NOT NULL," +
                "${TableInfo.TABLE_COLUMN_PET_HUNGER_LVL_MAX} INTEGER NOT NULL," +
                "${TableInfo.TABLE_COLUMN_PET_FUN_LVL} INTEGER NOT NULL," +
                "${TableInfo.TABLE_COLUMN_PET_FUN_LVL_MAX} INTEGER NOT NULL," +
                "${TableInfo.TABLE_COLUMN_PET_LOOK_TYPE} TEXT NOT NULL," +
                "${TableInfo.TABLE_COLUMN_PET_LOOK_COLOR} INTEGER NOT NULL," +
                "${TableInfo.TABLE_COLUMN_PET_LOOK_COLLAR} INTEGER NOT NULL," +
                "${TableInfo.TABLE_COLUMN_PET_POINTS} INTEGER NOT NULL," +
                "${TableInfo.TABLE_COLUMN_PET_AGE} INTEGER NOT NULL," +
                "${TableInfo.TABLE_COLUMN_PET_IS_ALIVE} INTEGER NOT NULL," +
                "${TableInfo.TABLE_COLUMN_PET_FOOD_AMOUNT} INTEGER NOT NULL," +
                "${TableInfo.TABLE_COLUMN_PET_FUN_AMOUNT} INTEGER NOT NULL," +
                "${TableInfo.TABLE_COLUMN_PET_UPGRADES_HUNGER_AMOUNT} INTEGER NOT NULL," +
                "${TableInfo.TABLE_COLUMN_PET_UPGRADES_HUNGER_AMOUNT_MAX} INTEGER NOT NULL," +
                "${TableInfo.TABLE_COLUMN_PET_UPGRADES_FUN_AMOUNT} INTEGER NOT NULL," +
                "${TableInfo.TABLE_COLUMN_PET_UPGRADES_FUN_AMOUNT_MAX} INTEGER NOT NULL)"

    const val SQL_CREATE_TABLE_DATE =
        "CREATE TABLE ${TableInfo.TABLE_NAME_DATE} (" +
                "${BaseColumns._ID} INTEGER PRIMARY KEY," +
                "${TableInfo.TABLE_COLUMN_DATE_YEAR} INTEGER NOT NULL," +
                "${TableInfo.TABLE_COLUMN_DATE_MONTH} INTEGER NOT NULL," +
                "${TableInfo.TABLE_COLUMN_DATE_DAY} INTEGER NOT NULL," +
                "${TableInfo.TABLE_COLUMN_DATE_HOUR} INTEGER NOT NULL," +
                "${TableInfo.TABLE_COLUMN_DATE_MINUTE} INTEGER NOT NULL," +
                "${TableInfo.TABLE_COLUMN_DATE_SECOND} INTEGER NOT NULL)"

    const val SQL_DELETE_TABLE_PET = "DROP TABLE IF EXISTS ${TableInfo.TABLE_NAME_PET}"
    const val SQL_DELETE_TABLE_DATE = "DROP TABLE IF EXISTS ${TableInfo.TABLE_NAME_DATE}"
}

class DataBaseHelper(context: Context) :
    SQLiteOpenHelper(context, TableInfo.DATABASE_NAME, null, 1) {
    override fun onCreate(db: SQLiteDatabase?) {
        db?.execSQL(BasicCommand.SQL_CREATE_TABLE_PET)
        db?.execSQL(BasicCommand.SQL_CREATE_TABLE_DATE)
    }

    override fun onUpgrade(db: SQLiteDatabase?, p1: Int, p2: Int) {
        db?.execSQL(BasicCommand.SQL_DELETE_TABLE_PET)
        db?.execSQL(BasicCommand.SQL_DELETE_TABLE_DATE)
        onCreate(db)
    }

    fun getPets(): ArrayList<Pet> {
        var petList = ArrayList<Pet>()
        val db = readableDatabase
        val selectQuery = "Select * from ${TableInfo.TABLE_NAME_PET}"
        val result = db.rawQuery(selectQuery, null)
        if (result.moveToFirst()) {
            do {
                var myPet = Pet(
                    result.getInt(result.getColumnIndex(BaseColumns._ID)).toLong(),
                    result.getInt(result.getColumnIndex(TableInfo.TABLE_COLUMN_PET_HUNGER_LVL)),
                    result.getInt(result.getColumnIndex(TableInfo.TABLE_COLUMN_PET_HUNGER_LVL_MAX)),
                    result.getInt(result.getColumnIndex(TableInfo.TABLE_COLUMN_PET_FUN_LVL)),
                    result.getInt(result.getColumnIndex(TableInfo.TABLE_COLUMN_PET_FUN_LVL_MAX)),
                    Look(
                        result.getString(result.getColumnIndex(TableInfo.TABLE_COLUMN_PET_LOOK_TYPE)),
                        result.getInt(result.getColumnIndex(TableInfo.TABLE_COLUMN_PET_LOOK_COLOR)),
                        result.getInt(result.getColumnIndex(TableInfo.TABLE_COLUMN_PET_LOOK_COLLAR))
                    ),
                    result.getInt(result.getColumnIndex(TableInfo.TABLE_COLUMN_PET_POINTS))
                        .toLong(),
                    result.getInt(result.getColumnIndex(TableInfo.TABLE_COLUMN_PET_AGE)).toLong(),
                    result.getInt(result.getColumnIndex(TableInfo.TABLE_COLUMN_PET_IS_ALIVE)),
                    result.getInt(result.getColumnIndex(TableInfo.TABLE_COLUMN_PET_FOOD_AMOUNT)),
                    result.getInt(result.getColumnIndex(TableInfo.TABLE_COLUMN_PET_FUN_AMOUNT)),
                    UpgradePrices(
                        result.getInt(result.getColumnIndex(TableInfo.TABLE_COLUMN_PET_UPGRADES_HUNGER_AMOUNT))
                            .toLong(),
                        result.getInt(result.getColumnIndex(TableInfo.TABLE_COLUMN_PET_UPGRADES_HUNGER_AMOUNT_MAX))
                            .toLong(),
                        result.getInt(result.getColumnIndex(TableInfo.TABLE_COLUMN_PET_UPGRADES_FUN_AMOUNT))
                            .toLong(),
                        result.getInt(result.getColumnIndex(TableInfo.TABLE_COLUMN_PET_UPGRADES_FUN_AMOUNT_MAX))
                            .toLong()
                    )
                )
                petList.add(myPet)
            } while (result.moveToNext())
        }
        result.close()
        db.close()
        return petList
    }

    fun addPet(pet: Pet) {
        val db = this.writableDatabase
        val values = ContentValues()
        values.put(TableInfo.TABLE_COLUMN_PET_HUNGER_LVL, pet.hungerLvl)
        values.put(TableInfo.TABLE_COLUMN_PET_HUNGER_LVL_MAX, pet.maxHungerLvl)
        values.put(TableInfo.TABLE_COLUMN_PET_FUN_LVL, pet.funLvl)
        values.put(TableInfo.TABLE_COLUMN_PET_FUN_LVL_MAX, pet.maxFunLvl)
        values.put(TableInfo.TABLE_COLUMN_PET_LOOK_TYPE, pet.look.petType)
        values.put(TableInfo.TABLE_COLUMN_PET_LOOK_COLOR, pet.look.petColor)
        values.put(TableInfo.TABLE_COLUMN_PET_LOOK_COLLAR, pet.look.petCollarColor)
        values.put(TableInfo.TABLE_COLUMN_PET_POINTS, pet.points)
        values.put(TableInfo.TABLE_COLUMN_PET_AGE, pet.age)
        values.put(TableInfo.TABLE_COLUMN_PET_IS_ALIVE, pet.isAlive)
        values.put(TableInfo.TABLE_COLUMN_PET_FOOD_AMOUNT, pet.foodAmount)
        values.put(TableInfo.TABLE_COLUMN_PET_FUN_AMOUNT, pet.funAmount)
        values.put(
            TableInfo.TABLE_COLUMN_PET_UPGRADES_HUNGER_AMOUNT,
            pet.upgradePrices.hungerAmount
        )
        values.put(
            TableInfo.TABLE_COLUMN_PET_UPGRADES_HUNGER_AMOUNT_MAX,
            pet.upgradePrices.hungerMaxAmount
        )
        values.put(TableInfo.TABLE_COLUMN_PET_UPGRADES_FUN_AMOUNT, pet.upgradePrices.funAmount)
        values.put(
            TableInfo.TABLE_COLUMN_PET_UPGRADES_FUN_AMOUNT_MAX,
            pet.upgradePrices.funMaxAmount
        )
        db.insert(TableInfo.TABLE_NAME_PET, null, values)
        db.close()
    }

    fun updatePet(pet: Pet) {
        val db = this.writableDatabase
        val values = ContentValues()
        values.put(TableInfo.TABLE_COLUMN_PET_HUNGER_LVL, pet.hungerLvl)
        values.put(TableInfo.TABLE_COLUMN_PET_HUNGER_LVL_MAX, pet.maxHungerLvl)
        values.put(TableInfo.TABLE_COLUMN_PET_FUN_LVL, pet.funLvl)
        values.put(TableInfo.TABLE_COLUMN_PET_FUN_LVL_MAX, pet.maxFunLvl)
        values.put(TableInfo.TABLE_COLUMN_PET_LOOK_TYPE, pet.look.petType)
        values.put(TableInfo.TABLE_COLUMN_PET_LOOK_COLOR, pet.look.petColor)
        values.put(TableInfo.TABLE_COLUMN_PET_LOOK_COLLAR, pet.look.petCollarColor)
        values.put(TableInfo.TABLE_COLUMN_PET_POINTS, pet.points)
        values.put(TableInfo.TABLE_COLUMN_PET_AGE, pet.age)
        values.put(TableInfo.TABLE_COLUMN_PET_IS_ALIVE, pet.isAlive)
        values.put(TableInfo.TABLE_COLUMN_PET_FOOD_AMOUNT, pet.foodAmount)
        values.put(TableInfo.TABLE_COLUMN_PET_FUN_AMOUNT, pet.funAmount)
        values.put(
            TableInfo.TABLE_COLUMN_PET_UPGRADES_HUNGER_AMOUNT,
            pet.upgradePrices.hungerAmount
        )
        values.put(
            TableInfo.TABLE_COLUMN_PET_UPGRADES_HUNGER_AMOUNT_MAX,
            pet.upgradePrices.hungerMaxAmount
        )
        values.put(TableInfo.TABLE_COLUMN_PET_UPGRADES_FUN_AMOUNT, pet.upgradePrices.funAmount)
        values.put(
            TableInfo.TABLE_COLUMN_PET_UPGRADES_FUN_AMOUNT_MAX,
            pet.upgradePrices.funMaxAmount
        )
        db.update(
            TableInfo.TABLE_NAME_PET, values, BaseColumns._ID + "=?",
            arrayOf(pet.id.toString())
        )
    }

    fun deletePet(petId: Long): Boolean {
        val db = this.writableDatabase
        val success =
            db.delete(
                TableInfo.TABLE_NAME_PET,
                BaseColumns._ID + "=?",
                arrayOf(petId.toString())
            )
                .toLong()
        db.close()
        return Integer.parseInt("$success") != -1
    }

    fun getDate(): ArrayList<Date> {
        var datesList = ArrayList<Date>()
        val db = readableDatabase
        val selectQuery = "Select * from ${TableInfo.TABLE_NAME_DATE}"
        val result = db.rawQuery(selectQuery, null)
        if (result.moveToFirst()) {
            do {
                var myDate = Date(
                    result.getInt(result.getColumnIndex(BaseColumns._ID)).toLong(),
                    result.getInt(result.getColumnIndex(TableInfo.TABLE_COLUMN_DATE_YEAR)),
                    result.getInt(result.getColumnIndex(TableInfo.TABLE_COLUMN_DATE_MONTH)),
                    result.getInt(result.getColumnIndex(TableInfo.TABLE_COLUMN_DATE_DAY)),
                    result.getInt(result.getColumnIndex(TableInfo.TABLE_COLUMN_DATE_HOUR)),
                    result.getInt(result.getColumnIndex(TableInfo.TABLE_COLUMN_DATE_MINUTE)),
                    result.getInt(result.getColumnIndex(TableInfo.TABLE_COLUMN_DATE_SECOND))
                )
                datesList.add(myDate)
            } while (result.moveToNext())
        }
        result.close()
        db.close()
        return datesList
    }

    fun addDate(date: Date) {
        val db = this.writableDatabase
        val values = ContentValues()
        values.put(TableInfo.TABLE_COLUMN_DATE_YEAR, date.year)
        values.put(TableInfo.TABLE_COLUMN_DATE_MONTH, date.month)
        values.put(TableInfo.TABLE_COLUMN_DATE_DAY, date.day)
        values.put(TableInfo.TABLE_COLUMN_DATE_HOUR, date.hour)
        values.put(TableInfo.TABLE_COLUMN_DATE_MINUTE, date.minute)
        values.put(TableInfo.TABLE_COLUMN_DATE_SECOND, date.second)
        db.insert(TableInfo.TABLE_NAME_DATE, null, values)
        db.close()
    }

    fun updateDate(date: Date) {
        val db = this.writableDatabase
        val values = ContentValues()
        values.put(TableInfo.TABLE_COLUMN_DATE_YEAR, date.year)
        values.put(TableInfo.TABLE_COLUMN_DATE_MONTH, date.month)
        values.put(TableInfo.TABLE_COLUMN_DATE_DAY, date.day)
        values.put(TableInfo.TABLE_COLUMN_DATE_HOUR, date.hour)
        values.put(TableInfo.TABLE_COLUMN_DATE_MINUTE, date.minute)
        values.put(TableInfo.TABLE_COLUMN_DATE_SECOND, date.second)
        db.update(
            TableInfo.TABLE_NAME_DATE, values, BaseColumns._ID + "=?",
            arrayOf(date.id.toString())
        )
        db.close()
    }

    fun deleteDate(dateId: Long): Boolean {
        val db = this.writableDatabase
        val success =
            db.delete(
                TableInfo.TABLE_NAME_DATE,
                BaseColumns._ID + "=?",
                arrayOf(dateId.toString())
            )
                .toLong()
        db.close()
        return Integer.parseInt("$success") != -1
    }
}
