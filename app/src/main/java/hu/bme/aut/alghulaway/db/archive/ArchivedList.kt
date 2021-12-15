package hu.bme.aut.alghulaway.db.archive

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "archive")
data class ArchivedList(
    @ColumnInfo(name = "id") @PrimaryKey(autoGenerate = true) var id : Long? = null,
    // @ColumnInfo(name ="archivalDate") var archivalDate : java.sql.Date,
    @ColumnInfo(name = "alcSum") var alcSum : Double,
    @ColumnInfo(name = "drinkAmountSum") var drinkAmountSum : Double,
    @ColumnInfo(name = "drinkCount") var drinkCount: Int,
    @ColumnInfo(name = "maxAlcAmountInDrink") var maxAlcAmountInDrink : Double,
    @ColumnInfo(name = "nonAlcCount") var nonAlcCount : Int,
    @ColumnInfo(name = "nonAlcAmount") var nonAlcAmount : Double,
    @ColumnInfo(name = "calorieSum") var calorieSum : Int?,
    @ColumnInfo(name = "priceSum") var priceSum : Int?,
    @ColumnInfo(name = "freeDrinkCount") var freeDrinkCount : Int
)