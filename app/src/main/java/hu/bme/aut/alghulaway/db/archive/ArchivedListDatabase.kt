package hu.bme.aut.alghulaway.db.archive

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import hu.bme.aut.alghulaway.db.drink.DrinkDatabase

@Database(entities = [ArchivedList::class], version = 1)
abstract class ArchivedListDatabase : RoomDatabase() {
    abstract fun listDao() : ArchivedListDao

    companion object {
        fun getDatabase(applicationContext: Context): ArchivedListDatabase{
            return Room.databaseBuilder(
                applicationContext,
                ArchivedListDatabase::class.java,
                "archive"
            ).build()
        }
    }
}