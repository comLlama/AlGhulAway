package hu.bme.aut.alghulaway.db

import android.app.Application
import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [Drink::class], version = 1)
abstract class DrinkDatabase : RoomDatabase() {
    abstract fun drinkDao() : DrinkDao

    companion object {
        fun getDatabase(applicationContext: Context): DrinkDatabase {
            return Room.databaseBuilder(
                applicationContext,
                DrinkDatabase::class.java,
                "drink"
            ).build()
        }
    }
}