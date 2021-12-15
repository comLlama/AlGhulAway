package hu.bme.aut.alghulaway

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import hu.bme.aut.alghulaway.databinding.ActivityMainBinding
import hu.bme.aut.alghulaway.databinding.ActivitySettingsBinding

class SettingsActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySettingsBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySettingsBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
    }
}