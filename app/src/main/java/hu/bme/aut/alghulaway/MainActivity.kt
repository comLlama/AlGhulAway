package hu.bme.aut.alghulaway

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.FragmentManager
import androidx.recyclerview.widget.GridLayoutManager
import hu.bme.aut.alghulaway.databinding.ActivityMainBinding
import hu.bme.aut.alghulaway.db.archive.ArchivedList
import hu.bme.aut.alghulaway.db.archive.ArchivedListDatabase
import hu.bme.aut.alghulaway.db.drink.Drink
import hu.bme.aut.alghulaway.db.drink.DrinkDatabase
import hu.bme.aut.alghulaway.fragments.AddDrinkDialogFragment
import hu.bme.aut.alghulaway.fragments.EditDrinkDialogFragment
import hu.bme.aut.alghulaway.listadapter.DrinkAdapter
import java.sql.Date
import java.util.*
import kotlin.concurrent.thread

class MainActivity : AppCompatActivity(), DrinkAdapter.DrinkClickListener,
                        AddDrinkDialogFragment.AddDrinkDialogListener,
                        EditDrinkDialogFragment.EditDrinkDialogListener{

    private lateinit var binding: ActivityMainBinding

    private lateinit var database: DrinkDatabase
    private lateinit var archive: ArchivedListDatabase
    private lateinit var adapter: DrinkAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        database = DrinkDatabase.getDatabase(applicationContext)

        binding.fab.setOnClickListener{
            AddDrinkDialogFragment().show(
                supportFragmentManager,
                AddDrinkDialogFragment.TAG
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
            R.id.action_removeAll -> {
                AlertDialog.Builder(this)
                    .setMessage("Do you want to remove all drinks on current list?")
                    .setPositiveButton(getString(R.string.dialogPositiveButton)) {_, _ -> removeAllFromList()}
                    .setNegativeButton(getString(R.string.dialogNegativeButton), null)
                    .show()
                true
            }
            R.id.action_archiveCurrentList -> {
                AlertDialog.Builder(this)
                    .setMessage("Do you want to archive current list?")
                    .setPositiveButton(getString(R.string.dialogPositiveButton)) { _, _ -> archiveCurrentList() }
                    .setNegativeButton(getString(R.string.dialogNegativeButton), null)
                    .show()
                true
            }
            R.id.action_archive -> {
                startActivity(Intent(this, ArchiveActivity::class.java))
                true
            }
            R.id.action_stats -> {
                startActivity(Intent(this, StatsActivity::class.java))
                true
            }
            R.id.action_settings -> {
                startActivity(Intent(this, SettingsActivity::class.java))
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun initRecyclerView(){
        adapter = DrinkAdapter(this, this)
        binding.rvMain.layoutManager = GridLayoutManager(this, resources.getInteger(R.integer.itemPerRow))
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

    override fun onDrinkAdded(newDrink: Drink) {
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

    private fun removeAllFromList(){
        adapter.removeAll()
        thread {
            database.drinkDao().deleteAll()
            updateAlcoholSum()
        }
    }

    override fun onDrinkRemoved(drink : Drink) {
        adapter.removeDrink(drink)
        thread{
            database.drinkDao().delete(drink)
            updateAlcoholSum()
        }
    }

    override fun onDrinkEdited(drink: Drink) {
        adapter.editDrink(drink)
        thread {
            database.drinkDao().update(drink)
            updateAlcoholSum()
        }
    }
    private fun archiveCurrentList(){
        thread{
            archive.listDao().insert(
                ArchivedList(
                    alcSum = database.drinkDao()?.getAlcoholAmount(),
                    drinkAmountSum = database.drinkDao().getDrinkAmount(),
                    drinkCount = database.drinkDao().getDrinkCount(),
                    maxAlcAmountInDrink = database.drinkDao().getMaxAlcAmount(),
                    nonAlcCount = database.drinkDao().getNonAlcCount(),
                    nonAlcAmount = database.drinkDao().getNonAlcAmount(),
                    calorieSum = database.drinkDao().getCalorieSum(),
                    priceSum = database.drinkDao().getPriceSum(),
                    freeDrinkCount = database.drinkDao().getFreeDrinkCount()
                )
            )
        }
    }
}