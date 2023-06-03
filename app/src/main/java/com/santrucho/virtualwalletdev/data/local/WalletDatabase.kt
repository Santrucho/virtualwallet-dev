package com.santrucho.virtualwalletdev.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.santrucho.virtualwalletdev.data.model.Card
import com.santrucho.virtualwalletdev.data.model.Movement
import com.santrucho.virtualwalletdev.data.model.User

@Database(entities = [Card::class, Movement::class, User::class], version = 1)
abstract class WalletDatabase : RoomDatabase() {
    abstract fun cardDao() : CardDao
    abstract fun movementDao() : MovementDao
    abstract fun userDao() : UserDao
}