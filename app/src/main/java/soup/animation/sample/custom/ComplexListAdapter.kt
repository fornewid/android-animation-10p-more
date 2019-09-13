package soup.animation.sample.custom

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.ext.IdBasedDiffCallback
import soup.animation.sample.R

class RecyclerViewAdapter : ListAdapter<Item, RecyclerViewAdapter.ViewHolder>(
    IdBasedDiffCallback { id.toString() }
) {

    private val list = (1..30).map { Item(it) }

    init {
        submitList(list)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return LayoutInflater.from(parent.context)
            .inflate(R.layout.item_recyclerview, parent, false)
            .let { ViewHolder(it) }
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

data class Item(val id: Int) {

    val text: String = "Item $id"
}
