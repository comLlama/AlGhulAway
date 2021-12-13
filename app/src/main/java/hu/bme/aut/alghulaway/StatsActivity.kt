package hu.bme.aut.alghulaway

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import hu.bme.aut.alghulaway.databinding.ActivityStatsBinding
import hu.bme.aut.alghulaway.db.drink.DrinkDatabase
import kotlin.concurrent.thread

class StatsActivity : AppCompatActivity() {
    private lateinit var binding: ActivityStatsBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityStatsBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
        thread {
            val alcoholAmount = DrinkDatabase.getDatabase(applicationContext).drinkDao().getAlcoholAmount().toString()
            runOnUiThread{ binding.tvStats.text =  alcoholAmount + " " + getString(R.string.unitAlcoholAmount) }
        }
    }
}