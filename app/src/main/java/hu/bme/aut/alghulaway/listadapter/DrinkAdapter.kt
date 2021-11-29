package hu.bme.aut.alghulaway.listadapter

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import hu.bme.aut.alghulaway.databinding.DrinkBinding
import hu.bme.aut.alghulaway.db.Drink

class DrinkAdapter(private val listener: DrinkClickListener) :
    RecyclerView.Adapter<DrinkAdapter.DrinkViewHolder>() {

    private val drinks = mutableListOf<Drink>()

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): DrinkAdapter.DrinkViewHolder {
        TODO("Not yet implemented")
    }

    override fun onBindViewHolder(holder: DrinkViewHolder, position: Int) {
        val drink = drinks[position]
        holder.binding.tvAbv.text = drink.abv.toString() + " %"
        holder.binding.tvAmount.text = drink.amount.toString() + " ml"
        holder.binding.tvCalories.text = drink.calories?.toString() ?: ""
        holder.binding.tvPrice.text = drink.price?.toString() ?: ""
    }

    override fun getItemCount(): Int = drinks.size

    interface DrinkClickListener {
        fun onDrinkChanged(drink: Drink)
    }

    inner class DrinkViewHolder(val binding: DrinkBinding) : RecyclerView.ViewHolder(binding.root)

    fun addDrink(drink: Drink) {
        drinks.add(drink)
        notifyItemInserted( drinks.size - 1)
    }

    fun update(updDrinks: List<Drink>){
        drinks.clear()
        drinks.addAll(updDrinks)
        notifyDataSetChanged()
    }
}