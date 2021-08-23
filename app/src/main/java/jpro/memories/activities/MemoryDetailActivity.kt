package jpro.memories.activities

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import jpro.memories.databinding.ActivityMemoryDetailBinding
import jpro.memories.models.MemoryModel

class MemoryDetailActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityMemoryDetailBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        var memory: MemoryModel? = null
        if (intent.hasExtra(MainActivity.MEMORY_DETAILS)) {
            memory = intent.getParcelableExtra(MainActivity.MEMORY_DETAILS)
        }

        if (memory != null) {
            setSupportActionBar(binding.tbMemoryDetail)
            supportActionBar!!.setDisplayHomeAsUpEnabled(true)
            supportActionBar!!.title = memory.name

            binding.tbMemoryDetail.setNavigationOnClickListener { onBackPressed() }
            binding.tvDate.text = memory.date
            binding.tvLocation.text = memory.location
            binding.tvLatitude.text = memory.latitude.toString()
            binding.tvLongitude.text = memory.longitude.toString()
            binding.tvDescription.text = memory.description
        }
    }
}