package hu.bme.aut.alghulaway

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import hu.bme.aut.alghulaway.databinding.ActivityStatsBinding
import hu.bme.aut.alghulaway.db.archive.ArchivedListDatabase
import hu.bme.aut.alghulaway.db.drink.DrinkDatabase
import kotlin.concurrent.thread

class StatsActivity() : AppCompatActivity() {
    private lateinit var binding: ActivityStatsBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityStatsBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        var dao = ArchivedListDatabase.getDatabase(this).listDao()
        thread {
            binding.tvStatSumAlc.text = dao.getAlcSum().toString()
            binding.tvStatMaxAlc.text = dao.getMaxAlcSum().toString()
            binding.tvStatSumPrice.text = dao.getPriceSum().toString()
        } //TODO: A lekerdezesekbol kellene meg ilyeneket irni, hogy szep es erdekes legyen de 4 perc van a podleadasig :(((

    }
}