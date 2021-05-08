package jpro.memories

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import jpro.memories.databinding.ActivityAddMemoryBinding
import jpro.memories.databinding.ActivityMainBinding

class AddMemoryActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityAddMemoryBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        setSupportActionBar(binding.tbAddMemory)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        binding.tbAddMemory.setNavigationOnClickListener {
            onBackPressed()
        }
    }
}