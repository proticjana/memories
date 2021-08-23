package jpro.memories.adapters

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import jpro.memories.R
import jpro.memories.activities.AddMemoryActivity
import jpro.memories.activities.MainActivity
import jpro.memories.models.MemoryModel
import kotlinx.android.synthetic.main.item_memory.view.*

open class MemoryAdapter(
    private val context: Context,
    private var memories: ArrayList<MemoryModel>
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private var onClickListener: OnClickListener? = null

    fun setOnClickListener(onClickListener: OnClickListener) {
        this.onClickListener = onClickListener
    }

    fun notifyEditItem(activity: Activity, position: Int, requestCode: Int) {
        val intent = Intent(context, AddMemoryActivity::class.java)
        intent.putExtra(MainActivity.MEMORY_DETAILS, memories[position])
        activity.startActivityForResult(intent, requestCode)

        // Update RecyclerView
        notifyItemChanged(position)
    }

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
        val model = memories[position]

        if (holder is MyViewHolder) {
            holder.itemView.tv_date.text = model.date
            holder.itemView.tv_name.text = model.name
            holder.itemView.tv_description.text = model.description

            holder.itemView.setOnClickListener {
                if (onClickListener != null) {
                    onClickListener!!.onClick(position, model)
                }
            }
        }
    }

    // Gets the number of items in the list
    override fun getItemCount(): Int {
        return memories.size
    }

    interface OnClickListener {
        fun onClick(position: Int, model: MemoryModel)
    }

    // A ViewHolder describes an item view and metadata about its place within the RecyclerView
    private class MyViewHolder(view: View) : RecyclerView.ViewHolder(view)
}