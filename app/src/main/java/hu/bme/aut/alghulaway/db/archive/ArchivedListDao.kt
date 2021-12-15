package hu.bme.aut.alghulaway.db.archive

import androidx.room.*
import java.util.*

@Dao
interface ArchivedListDao {
    @Insert
    fun insert(list: ArchivedList) : Long

    @Update
    fun update(list: ArchivedList)

    @Delete
    fun delete(list: ArchivedList)

    @Query("SELECT * FROM archive")
    fun getAll() : List<ArchivedList>


    @Query("SELECT SUM(alcSum) FROM archive")
    fun getAlcSum() : Double?

    @Query("SELECT MAX(alcSum) FROM archive")
    fun getMaxAlcSum() : Double?
/*
    @Query("SELECT archivalDate FROM archive WHERE alcSum = (SELECT MAX(alcSum) FROM archive)")
    fun getWhenMaxAlcSum() : List<Date>
*/

    @Query("SELECT SUM(priceSum) FROM archive")
    fun getPriceSum() : Int?

    @Query("SELECT MAX(priceSum) FROM archive")
    fun getMaxPriceSum() : Int?
/*
    @Query("SELECT archivalDate FROM archive WHERE priceSum = (SELECT MAX(priceSum) FROM archive)")
    fun getWhenMaxPriceSum() : List<Date>
*/

    @Query("SELECT SUM(calorieSum) FROM archive")
    fun getCalorieSum() : Int?

    @Query("SELECT MAX(calorieSum) FROM archive")
    fun getMaxCalorieSum() : Int?
/*
    @Query("SELECT archivalDate FROM archive WHERE calorieSum = (SELECT MAX(calorieSum) FROM archive)")
    fun getWhenMaxCalorieSum() : List<Date>
*/

    @Query("SELECT nonAlcAmount FROM archive")
    fun getNonAlcAmountSum() : Double?

    @Query("SELECT MAX(nonAlcAmount) FROM archive")
    fun getMaxNonAlcAmountSum() : Double?
/*
    @Query("SELECT archivalDate FROM archive WHERE nonAlcAmount = (SELECT MAX(nonAlcAmount) FROM archive)")
    fun getWhenMaxNonAlcAmountSum() : List<Date>
*/
    @Query("SELECT AVG(nonAlcAmount) FROM archive")
    fun getAvgNonAlcAmountSum() : Double?
/*
    @Query("SELECT archivalDate FROM archive WHERE nonAlcAmount = (SELECT AVG(nonAlcAmount) FROM archive)")
    fun getWhenAvgNonAlcAmountSum() : List<Date>*/
}