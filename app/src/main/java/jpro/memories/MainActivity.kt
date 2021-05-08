package jpro.memories

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import jpro.memories.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        binding.fabAddMemory.setOnClickListener {
            val intent = Intent(this, AddMemoryActivity::class.java)
            startActivity(intent)
        }
    }
}