package soup.animation.sample.animator.recyclerview

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import soup.animation.sample.R

class RecyclerViewAdapter(
    private val listener: (Item) -> Unit
) : ListAdapter<Item, RecyclerViewAdapter.ViewHolder>(IdBasedDiffCallback(Item::id)) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return LayoutInflater.from(parent.context)
            .inflate(R.layout.item_recyclerview, parent, false)
            .let { ViewHolder(it) }
            .apply {
                itemView.setOnClickListener {
                    currentList.getOrNull(adapterPosition)?.run(listener)
                }
            }
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        currentList.getOrNull(position)?.run(holder::bind)
    }

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        private val textView: TextView = view.findViewById(R.id.text)

        fun bind(item: Item) {
            textView.text = item.text
        }
    }
}
