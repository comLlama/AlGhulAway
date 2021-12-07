package hu.bme.aut.alghulaway.listadapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import hu.bme.aut.alghulaway.R
import hu.bme.aut.alghulaway.databinding.DrinkBinding
import hu.bme.aut.alghulaway.db.Drink

class DrinkAdapter(private val listener: DrinkClickListener) :
    RecyclerView.Adapter<DrinkAdapter.DrinkViewHolder>() {

    private val drinks = mutableListOf<Drink>()

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ) = DrinkViewHolder (
        DrinkBinding.inflate(LayoutInflater.from(parent.context), parent, false)
    )

    override fun onBindViewHolder(holder: DrinkViewHolder, position: Int) {
        val drink = drinks[position]
        holder.binding.tvAbv.text = drink.abv.toString() + holder.itemView.context.getString(R.string.unitAbv)
        holder.binding.tvAmount.text = drink.amount.toString() + " " + holder.itemView.context.getString(R.string.unitAmount)
        if (drink.calories != null) {
            holder.binding.tvCalories.text = drink.calories.toString() + " " + holder.itemView.context.getString(R.string.unitCalories)
        } else {
            holder.binding.tvCalories.text = ""
        }
        holder.binding.tvPrice.text = drink.price?.toString()  ?: ""
        if (drink.price != null) {
            holder.binding.tvPrice.text = drink.price.toString() + " " + holder.itemView.context.getString(R.string.unitPrice)
        } else {
            holder.binding.tvCalories.text = ""
        }
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

    fun removeDrink(drink: Drink) {
        var idx = drinks.indexOf(drink)
        drinks.remove(drink)
        notifyItemRemoved(idx)
    }

    fun update(updDrinks: List<Drink>){
        drinks.clear()
        drinks.addAll(updDrinks)
        notifyDataSetChanged()
    }
}