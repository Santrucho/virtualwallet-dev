package com.santrucho.virtualwalletdev.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.santrucho.virtualwalletdev.data.model.Card

@Database(entities = [Card::class], version = 1)
abstract class WalletDatabase : RoomDatabase() {
    abstract fun cardDao() : CardDao
}