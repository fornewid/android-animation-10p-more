package soup.animation.sample

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class HomeListAdapter(
    private val listener: (HomeItem) -> Unit
) : RecyclerView.Adapter<HomeListAdapter.ViewHolder>() {

    private val items = (1..12)
        .map { index ->
            HomeItem(
                iconId = R.drawable.ic_facing_back,
                text = "Item $index"
            )
        }
        .toList()

    init {
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.item_home, parent, false)
        ).apply {
            itemView.setOnClickListener {
                getItem(adapterPosition)?.run(listener)
            }
        }
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        getItem(position)?.run(holder::bind)
    }

    override fun getItemCount(): Int {
        return items.size
    }

    private fun getItem(position: Int): HomeItem? {
        return items.getOrNull(position)
    }

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        private val iconView: ImageView = view.findViewById(R.id.icon)
        private val textView: TextView = view.findViewById(R.id.text)

        fun bind(item: HomeItem) {
            iconView.setImageResource(item.iconId)
            textView.text = item.text
        }
    }
}
