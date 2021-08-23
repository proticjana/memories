package jpro.memories.activities

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.SearchView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import jpro.memories.adapters.MemoryAdapter
import jpro.memories.database.DatabaseHandler
import jpro.memories.databinding.ActivityMainBinding
import jpro.memories.models.MemoryModel
import jpro.memories.utils.SwipeToDeleteCallback
import jpro.memories.utils.SwipeToEditCallback
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    companion object {
        var ADD_MEMORY_ACTIVITY_REQ_CODE = 1
        var MEMORY_DETAILS = "memory_details"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        binding.fabAddMemory.setOnClickListener {
            val intent = Intent(this, AddMemoryActivity::class.java)
            startActivityForResult(intent, ADD_MEMORY_ACTIVITY_REQ_CODE)
        }

        val searchView = binding.svMain
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                searchView.clearFocus()
                return queryDB(query)
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                if (newText.isNullOrEmpty()) {
                    searchView.clearFocus()
                }
                return queryDB(newText)
            }
        })

        getMemoriesFromDB()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == ADD_MEMORY_ACTIVITY_REQ_CODE) {
            if (resultCode == RESULT_OK) {
                getMemoriesFromDB()
            } else {
                Log.e("MainActivity", "Add memory cancelled or back pressed.")
            }
        }
    }

    private fun setupMemoriesRecyclerView(memoryList: ArrayList<MemoryModel>) {
        rv_memories_list.layoutManager = LinearLayoutManager(this)
        rv_memories_list.setHasFixedSize(true)

        val memoryAdapter = MemoryAdapter(this, memoryList)
        rv_memories_list.adapter = memoryAdapter

        memoryAdapter.setOnClickListener(object : MemoryAdapter.OnClickListener {
            override fun onClick(position: Int, model: MemoryModel) {
                val intent = Intent(this@MainActivity, MemoryDetailActivity::class.java)
                intent.putExtra(MEMORY_DETAILS, model)
                startActivity(intent)
            }
        })

        val editSwipeHandler = object : SwipeToEditCallback(this) {
            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                memoryAdapter.notifyEditItem(
                    this@MainActivity,
                    viewHolder.adapterPosition,
                    ADD_MEMORY_ACTIVITY_REQ_CODE
                )
            }
        }

        val editItemTouchHelper = ItemTouchHelper(editSwipeHandler)
        editItemTouchHelper.attachToRecyclerView(rv_memories_list)

        val deleteSwipeHandler = object : SwipeToDeleteCallback(this) {
            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                memoryAdapter.removeAt(viewHolder.adapterPosition)

                // Update RecyclerView according to DB
                getMemoriesFromDB()
            }
        }

        val deleteItemTouchHelper = ItemTouchHelper(deleteSwipeHandler)
        deleteItemTouchHelper.attachToRecyclerView(rv_memories_list)
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

    private fun queryDB(query: String?): Boolean {
        val dbHandler = DatabaseHandler(this)
        val memories: ArrayList<MemoryModel> = dbHandler.queryMemoriesList(query)

        if (memories.isNotEmpty()) {
            rv_memories_list.visibility = View.VISIBLE
            tv_no_memories_available.visibility = View.GONE

            setupMemoriesRecyclerView(memories)

            return true
        } else {
            rv_memories_list.visibility = View.GONE
            tv_no_memories_available.visibility = View.VISIBLE

            return false
        }
    }
}