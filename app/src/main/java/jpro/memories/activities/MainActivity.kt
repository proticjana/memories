package jpro.memories.activities

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import jpro.memories.adapters.MemoryAdapter
import jpro.memories.database.DatabaseHandler
import jpro.memories.databinding.ActivityMainBinding
import jpro.memories.models.MemoryModel
import kotlinx.android.synthetic.main.activity_main.*

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

    private fun setupMemoriesRecyclerView(memoryList: ArrayList<MemoryModel>) {
        rv_memories_list.layoutManager = LinearLayoutManager(this)
        rv_memories_list.setHasFixedSize(true)

        val memoryAdapter = MemoryAdapter(this, memoryList)
        rv_memories_list.adapter = memoryAdapter
    }

    private fun getMemoriesFromDB() {
        val dbHandler = DatabaseHandler(this)
        val memories: ArrayList<MemoryModel> = dbHandler.getMemoriesList()

        if (memories.isNotEmpty()) {
            rv_memories_list.visibility = View.VISIBLE
            tv_no_memories_available.visibility = View.GONE

            setupMemoriesRecyclerView(memories)
        } else {
            rv_memories_list.visibility = View.GONE
            tv_no_memories_available.visibility = View.VISIBLE
        }
    }
}