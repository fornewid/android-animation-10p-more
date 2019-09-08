package soup.animation.sample

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import soup.animation.sample.MainGraphDirections.Companion.actionToAnimator
import soup.animation.sample.MainGraphDirections.Companion.actionToDrawable
import soup.animation.sample.MainGraphDirections.Companion.actionToDrawableNotification
import soup.animation.sample.MainGraphDirections.Companion.actionToInterpolator
import soup.animation.sample.MainGraphDirections.Companion.actionToSpring
import soup.animation.sample.MainGraphDirections.Companion.actionToViewAnimation
import soup.animation.sample.MainGraphDirections.Companion.actionToViewProperty

class HomeListAdapter(
    private val listener: (HomeItem) -> Unit
) : RecyclerView.Adapter<HomeListAdapter.ViewHolder>() {

    private val items = listOf(
        HomeItem(R.drawable.ic_interpolator, R.string.title_interpolator) {
            actionToInterpolator()
        },
        HomeItem(R.drawable.ic_drawable, R.string.title_drawable) {
            actionToDrawable()
        },
        HomeItem(R.drawable.ic_drawable, R.string.title_drawable_notification) {
            actionToDrawableNotification()
        },
        HomeItem(R.drawable.ic_view_animation, R.string.title_view_animation) {
            actionToViewAnimation()
        },
        HomeItem(R.drawable.ic_view_property, R.string.title_view_property) {
            actionToViewProperty()
        },
        HomeItem(R.drawable.ic_animator, R.string.title_animator) {
            actionToAnimator()
        },
        HomeItem(R.drawable.ic_spring, R.string.title_spring) {
            actionToSpring()
        }
    )

    init {
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return LayoutInflater.from(parent.context)
            .inflate(R.layout.item_home, parent, false)
            .let { ViewHolder(it) }
            .apply {
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
            iconView.setImageResource(item.iconResId)
            textView.setText(item.textResId)
        }
    }
}
