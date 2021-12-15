package hu.bme.aut.alghulaway.db.drink

import androidx.room.*

@Dao
interface DrinkDao {
    @Insert
    fun insert(drink: Drink): Long

    @Update
    fun update(drink: Drink)

    @Delete
    fun delete(drink: Drink)

    @Query("DELETE FROM drinks")
    fun deleteAll()

    @Query("SELECT * FROM drinks")
    fun getAll(): List<Drink>

    @Query("SELECT SUM(abv*amount) FROM drinks")
    fun getAlcoholAmount(): Double


    @Query("SELECT SUM(price) FROM drinks")
    fun getPriceSum(): Int?

    @Query("SELECT SUM(amount) FROM drinks")
    fun getDrinkAmount(): Double

    @Query("SELECT COUNT(*) FROM drinks")
    fun getDrinkCount(): Int

    @Query("SELECT MAX(abv*amount) FROM drinks")
    fun getMaxAlcAmount() : Double

    @Query("SELECT COUNT(*) FROM drinks WHERE abv = 0")
    fun getNonAlcCount() : Int

    @Query("SELECT SUM(amount) FROM drinks WHERE abv = 0")
    fun getNonAlcAmount() : Double

    @Query("SELECT SUM(calories) FROM drinks")
    fun getCalorieSum(): Int

    @Query("SELECT COUNT(*) FROM drinks WHERE price = 0")
    fun getFreeDrinkCount() : Int

    //TODO: other queries if needed
}