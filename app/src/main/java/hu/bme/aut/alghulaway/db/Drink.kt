package hu.bme.aut.alghulaway.db

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "drinks")
data class Drink(
    @ColumnInfo(name = "id") @PrimaryKey(autoGenerate = true) var id : Long? = null,
    @ColumnInfo(name = "abv") var abv: Double,
    @ColumnInfo(name = "amount") var amount: Double,
    //TODO: Add datetime
    @ColumnInfo(name = "calories") var calories : Int?,
    @ColumnInfo(name = "price") var price : Int?
)