package hu.bme.aut.alghulaway

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.recyclerview.widget.GridLayoutManager
import hu.bme.aut.alghulaway.databinding.ActivityMainBinding
import hu.bme.aut.alghulaway.db.drink.Drink
import hu.bme.aut.alghulaway.db.drink.DrinkDatabase
import hu.bme.aut.alghulaway.fragments.AddEditDrinkDialogFragment
import hu.bme.aut.alghulaway.listadapter.DrinkAdapter
import kotlin.concurrent.thread

class MainActivity : AppCompatActivity(), DrinkAdapter.DrinkClickListener,
                        AddEditDrinkDialogFragment.AddEditDrinkDialogListener {

    private lateinit var binding: ActivityMainBinding

    private lateinit var database: DrinkDatabase
    private lateinit var adapter: DrinkAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        database = DrinkDatabase.getDatabase(applicationContext)

        binding.fab.setOnClickListener{
            AddEditDrinkDialogFragment().show(
                supportFragmentManager,
                AddEditDrinkDialogFragment.TAG
            )
        }

        initRecyclerView()
        thread {updateAlcoholSum()}
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId){
            R.id.action_archive -> {
                startActivity(Intent(this, ArchiveActivity::class.java))
                true
            }
            R.id.action_stats -> {
                startActivity(Intent(this, StatsActivity::class.java))
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun initRecyclerView(){
        adapter = DrinkAdapter(this)
        binding.rvMain.layoutManager = GridLayoutManager(this, 3)
        binding.rvMain.adapter = adapter
        loadItemsInBackground()
    }

    private fun loadItemsInBackground(){
        thread {
            val drinks = database.drinkDao().getAll()
            runOnUiThread {
                adapter.update(drinks)
            }
        }
    }

    override fun onDrinkChanged(drink: Drink) {
        thread {
            database.drinkDao().update(drink)
            // Log.d("MainActivity", "Drink update was successful")
        }
    }

    override fun onDrinkModified(newDrink: Drink) {
        thread {
            val insertId = database.drinkDao().insert(newDrink)
            newDrink.id = insertId
            runOnUiThread { adapter.addDrink(newDrink) }
            updateAlcoholSum()
        }
    }

    private fun updateAlcoholSum(){ // Must be called from thread!
        var sum = database.drinkDao().getAlcoholAmount()
        runOnUiThread {binding.tvSumAlc.text = sum.toString() + " " + getString(R.string.unitAlcoholAmount)}
    }
}