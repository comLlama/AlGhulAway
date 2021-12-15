package hu.bme.aut.alghulaway.listadapter

import android.content.res.Resources
import android.graphics.drawable.Drawable
import android.util.TypedValue
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.annotation.ColorInt
import androidx.recyclerview.widget.RecyclerView
import hu.bme.aut.alghulaway.R
import hu.bme.aut.alghulaway.databinding.DrinkBinding
import hu.bme.aut.alghulaway.db.drink.Drink
import java.util.*

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

        val alc = drink.abv * drink.amount
        val miThreshold = holder.itemView.context.resources.getInteger(R.integer.alcMidThreshold)
        val hiThreshold = holder.itemView.context.resources.getInteger(R.integer.alcHighTreshold)
        if (alc < miThreshold) {
            holder.binding.root.context.setTheme(R.style.Theme_ListItemCardLowAlc)
        } else if (alc < hiThreshold) {
            holder.binding.root.context.setTheme(R.style.Theme_ListItemCardMedAlc)
        } else {
            holder.binding.root.context.setTheme(R.style.Theme_ListItemCardHighAlc)
        }
        var typedValue = TypedValue()
        holder.itemView.context.theme.resolveAttribute(R.attr.backgroundColor, typedValue, true)
        holder.binding.root.setBackgroundColor(typedValue.data)

        //holder.binding.root.invalidate()
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

    fun removeAll() {
        var size = drinks.size
        drinks.removeAll(drinks)
        notifyItemRangeRemoved(0, size)
    }

    fun update(updDrinks: List<Drink>){
        drinks.clear()
        drinks.addAll(updDrinks)
        notifyDataSetChanged()
    }
}