package hu.bme.aut.alghulaway.fragments

import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.text.Editable
import android.view.LayoutInflater
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.DialogFragment
import hu.bme.aut.alghulaway.R
import hu.bme.aut.alghulaway.databinding.DialogAddDrinkBinding
import hu.bme.aut.alghulaway.db.drink.Drink
import java.lang.RuntimeException

class EditDrinkDialogFragment(private val drink : Drink) : DialogFragment() {
    interface EditDrinkDialogListener{
        fun onDrinkEdited(drink: Drink)
        fun onDrinkRemoved(drink: Drink)
    }

    private lateinit var listener: EditDrinkDialogListener
    private lateinit var binding: DialogAddDrinkBinding

    override fun onAttach(context: Context) {
        super.onAttach(context)
        listener = context as? EditDrinkDialogListener
            ?: throw RuntimeException("EditDrinkDialogListener unimplemented in Activity")
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        binding = DialogAddDrinkBinding.inflate(LayoutInflater.from(context))
        //TODO: placeholders
        binding.etABV.setText(drink.abv.toString())
        binding.etAmount.setText(drink.amount.toString())
        binding.etCalories.setText(drink.calories?.toString())
        binding.etPrice.setText(drink.price?.toString())

        return AlertDialog.Builder(requireContext())
            .setTitle("Edit Drink")
            .setView(binding.root)
            .setPositiveButton("Edit"){
                    _, _->
                if (isValid()) {
                    listener.onDrinkEdited(getDrink())
                }
            }
            .setNegativeButton("Delete"){
                _, _->
                listener.onDrinkRemoved(drink)
            }
            .create()
    }

    private fun isValid() = binding.etABV.text.isNotEmpty() && binding.etAmount.text.isNotEmpty()

    private fun getDrink() = Drink(
        id = drink.id,
        abv = binding.etABV.text.toString().toDouble(),
        amount = binding.etAmount.text.toString().toDouble(),
        calories = binding.etCalories.text.toString().toIntOrNull(),
        price = binding.etPrice.text.toString().toIntOrNull()
    )

    companion object{
        const val TAG = "EditDrinkDialogFragment"
    }
}