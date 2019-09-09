package soup.animation.sample.view_property.recycler_view

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import soup.animation.sample.R

class RecyclerViewAdapter : ListAdapter<Item, RecyclerViewAdapter.ViewHolder>(IdBasedDiffCallback(Item::id)) {

    private val list = arrayListOf<Item>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return LayoutInflater.from(parent.context)
            .inflate(R.layout.item_recyclerview, parent, false)
            .let { ViewHolder(it) }
            .apply {
                itemView.setOnClickListener {
                    getItemOrNull(adapterPosition)?.let {
                        if (list.remove(it)) {
                            submitList(ArrayList(list))
                        }
                    }
                }
            }
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        getItemOrNull(position)?.run(holder::bind)
    }

    private fun getItemOrNull(position: Int): Item? {
        return currentList.getOrNull(position)
    }

    fun addItemToLast() {
        list.add(Item(id = list.lastOrNull()?.id?.plus(1) ?: 0))
        submitList(ArrayList(list))
    }

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        private val textView: TextView = view.findViewById(R.id.text)

        fun bind(item: Item) {
            textView.text = item.text
        }
    }
}
