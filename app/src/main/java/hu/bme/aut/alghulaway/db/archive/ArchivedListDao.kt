package hu.bme.aut.alghulaway.db.archive

import androidx.room.*

@Dao
interface ArchivedListDao {
    @Insert
    fun insert(list: ArchivedList) : Long

    @Update
    fun update(list: ArchivedList)

    @Delete
    fun delete(list: ArchivedList)

    @Query("SELECT * FROM archive")
    fun getAll()

    @Query("SELECT SUM(alcSum) FROM archive")
    fun getAlcSum() : Double?

    @Query("SELECT SUM(priceSum) FROM archive")
    fun getPriceSum() : Int?
}