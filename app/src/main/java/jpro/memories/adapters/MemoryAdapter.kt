package jpro.memories.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import jpro.memories.R
import jpro.memories.models.MemoryModel
import kotlinx.android.synthetic.main.item_memory.view.*

open class MemoryAdapter(
    private val context: Context,
    private var list: ArrayList<MemoryModel>
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    // Inflates the item views which is designed in xml layout file
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return MyViewHolder(
            LayoutInflater.from(context).inflate(
                R.layout.item_memory,
                parent,
                false
            )
        )
    }

    // Used to display the data at the specified position. Updates the contents of the
    // RecyclerView.ViewHolder.itemView to reflect the item at the given position
    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val model = list[position]

        if (holder is MyViewHolder) {
            holder.itemView.tv_date.text = model.date
            holder.itemView.tv_name.text = model.name
            holder.itemView.tv_description.text = model.description
        }
    }

    // Gets the number of items in the list
    override fun getItemCount(): Int {
        return list.size
    }


    // A ViewHolder describes an item view and metadata about its place within the RecyclerView
    private class MyViewHolder(view: View) : RecyclerView.ViewHolder(view)

}