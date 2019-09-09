package soup.animation.sample.spring.recycler_view

import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.ext.IdBasedDiffCallback
import soup.animation.sample.R

class RecyclerViewAdapter : ListAdapter<Item, RecyclerViewAdapter.ViewHolder>(
    IdBasedDiffCallback { id.toString() }
) {

    private val list = listOf(
        Item(1, Color.RED),
        Item(2, Color.GREEN),
        Item(3, Color.BLUE),
        Item(4, Color.DKGRAY),
        Item(5, Color.MAGENTA),
        Item(6, Color.YELLOW),
        Item(7, Color.CYAN),
        Item(8, Color.BLACK),
        Item(9, Color.LTGRAY)
    )

    init {
        shuffle()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return LayoutInflater.from(parent.context)
            .inflate(R.layout.item_recyclerview_grid, parent, false)
            .let { ViewHolder(it) }
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        getItemOrNull(position)?.run(holder::bind)
    }

    private fun getItemOrNull(position: Int): Item? {
        return currentList.getOrNull(position)
    }

    fun shuffle() {
        submitList(list.shuffled())
    }

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        private val colorView: View = view.findViewById(R.id.colorView)

        fun bind(item: Item) {
            colorView.setBackgroundColor(item.backgroundColor)
        }
    }
}
