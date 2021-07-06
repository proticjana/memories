package jpro.memories.activities

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import jpro.memories.database.DatabaseHandler
import jpro.memories.databinding.ActivityMainBinding
import jpro.memories.models.MemoryModel

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

        getMemoriesFromDB()
    }

    private fun getMemoriesFromDB() {
        val dbHandler = DatabaseHandler(this)
        val memories: ArrayList<MemoryModel> = dbHandler.getMemoriesList()

        if(memories.isNotEmpty()) {
            for(memory in memories) {
                Log.i("milan", "${memory.name} : ${memory.description}")
            }
        }
    }
}