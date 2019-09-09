package soup.animation.sample.view_property.recycler_view

import android.annotation.SuppressLint
import androidx.recyclerview.widget.DiffUtil

class IdBasedDiffCallback<T>(
    private val id: T.() -> Int
) : DiffUtil.ItemCallback<T>() {

    override fun areItemsTheSame(oldItem: T, newItem: T): Boolean {
        return oldItem.id() == newItem.id()
    }

    @SuppressLint("DiffUtilEquals")
    override fun areContentsTheSame(oldItem: T, newItem: T): Boolean {
        return oldItem == newItem
    }
}
