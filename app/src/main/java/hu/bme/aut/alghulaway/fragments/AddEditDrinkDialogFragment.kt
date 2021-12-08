package hu.bme.aut.alghulaway.fragments

import androidx.appcompat.app.AlertDialog
import android.app.Dialog
import android.content.Context
import android.opengl.ETC1.isValid
import android.os.Bundle
import android.view.LayoutInflater
import androidx.fragment.app.DialogFragment
import hu.bme.aut.alghulaway.R
import hu.bme.aut.alghulaway.databinding.DialogAddEditDrinkBinding
import hu.bme.aut.alghulaway.databinding.DrinkBinding
import hu.bme.aut.alghulaway.db.Drink
import java.lang.RuntimeException

class AddEditDrinkDialogFragment : DialogFragment() {
    interface AddEditDrinkDialogListener{
        fun onDrinkModified(newDrink: Drink)
    }

    private lateinit var listener: AddEditDrinkDialogListener
    private lateinit var binding: DialogAddEditDrinkBinding

    override fun onAttach(context: Context) {
        super.onAttach(context)
        listener = context as? AddEditDrinkDialogListener
            ?: throw RuntimeException("AddEditDrinkDialogListener unimplemented in Activity")
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        binding = DialogAddEditDrinkBinding.inflate(LayoutInflater.from(context))

        return AlertDialog.Builder(requireContext())
            .setTitle("Title")
            .setView(binding.root)
            .setPositiveButton("OK"){
                dialogInterface, i->
                if (isValid()) {
                    listener.onDrinkModified(getDrink())
                }
            }
            .setNegativeButton("Cancel", null)
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
        const val TAG = "AddEditDrinkDialogFragment"
    }
}