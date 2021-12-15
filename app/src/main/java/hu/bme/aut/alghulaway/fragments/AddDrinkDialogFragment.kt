package hu.bme.aut.alghulaway.fragments

import androidx.appcompat.app.AlertDialog
import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import androidx.fragment.app.DialogFragment
import hu.bme.aut.alghulaway.R
import hu.bme.aut.alghulaway.databinding.DialogAddDrinkBinding
import hu.bme.aut.alghulaway.db.drink.Drink
import java.lang.RuntimeException

class AddDrinkDialogFragment : DialogFragment() {
    interface AddDrinkDialogListener{
        fun onDrinkAdded(newDrink: Drink)
    }

    private lateinit var listener: AddDrinkDialogListener
    private lateinit var binding: DialogAddDrinkBinding

    override fun onAttach(context: Context) {
        super.onAttach(context)
        listener = context as? AddDrinkDialogListener
            ?: throw RuntimeException("AddDrinkDialogListener unimplemented in Activity")
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        binding = DialogAddDrinkBinding.inflate(LayoutInflater.from(context))

        return AlertDialog.Builder(requireContext())
            .setTitle(getString(R.string.dialogAddDrinkTitle))
            .setView(binding.root)
            .setPositiveButton(getString(R.string.dialogPositiveButton)){
                dialogInterface, i->
                if (isValid()) {
                    listener.onDrinkAdded(getDrink())
                }
            }
            .setNegativeButton(getString(R.string.dialogNegativeButton), null)
            .create()
    }

    private fun isValid() = binding.etABV.text.isNotEmpty() && binding.etAmount.text.isNotEmpty()

    private fun getDrink() = Drink(
        abv = binding.etABV.text.toString().toDouble(),
        amount = binding.etAmount.text.toString().toDouble(),
        calories = binding.etCalories.text.toString().toIntOrNull(),
        price = binding.etPrice.text.toString().toIntOrNull()
    )

    companion object{
        const val TAG = "AddDrinkDialogFragment"
    }
}