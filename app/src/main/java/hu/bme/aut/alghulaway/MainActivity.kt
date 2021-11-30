package hu.bme.aut.alghulaway

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.recyclerview.widget.LinearLayoutManager
import hu.bme.aut.alghulaway.databinding.ActivityMainBinding
import hu.bme.aut.alghulaway.db.Drink
import hu.bme.aut.alghulaway.db.DrinkDatabase
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
        setSupportActionBar(binding.toolbar)

        database = DrinkDatabase.getDatabase(applicationContext)

        binding.fab.setOnClickListener{
            AddEditDrinkDialogFragment().show(
                supportFragmentManager,
                AddEditDrinkDialogFragment.TAG
            )
        }

        initRecyclerView()
    }

    private fun initRecyclerView(){
        adapter = DrinkAdapter(this)
        binding.rvMain.layoutManager = LinearLayoutManager(this)
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
        }
    }
}