package hu.bme.aut.alghulaway.db

import androidx.room.*

@Dao
interface DrinkDao {
    @Insert
    fun insert(drink: Drink): Long

    @Update
    fun update(drink: Drink)

    @Delete
    fun delete(drink: Drink)

    @Query("SELECT * FROM drinks")
    fun getAll(): List<Drink>

    //TODO: other queries if needed
}