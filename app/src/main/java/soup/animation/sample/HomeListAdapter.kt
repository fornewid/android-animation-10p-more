package soup.animation.sample

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import soup.animation.sample.MainGraphDirections.Companion.actionToAnimator
import soup.animation.sample.MainGraphDirections.Companion.actionToAnimatorReveal
import soup.animation.sample.MainGraphDirections.Companion.actionToCustom
import soup.animation.sample.MainGraphDirections.Companion.actionToDrawable
import soup.animation.sample.MainGraphDirections.Companion.actionToDrawableGradient
import soup.animation.sample.MainGraphDirections.Companion.actionToDrawableLoading
import soup.animation.sample.MainGraphDirections.Companion.actionToDrawableNotification
import soup.animation.sample.MainGraphDirections.Companion.actionToDrawableRipple
import soup.animation.sample.MainGraphDirections.Companion.actionToFling
import soup.animation.sample.MainGraphDirections.Companion.actionToInterpolator
import soup.animation.sample.MainGraphDirections.Companion.actionToLayoutTransition
import soup.animation.sample.MainGraphDirections.Companion.actionToRecyclerView
import soup.animation.sample.MainGraphDirections.Companion.actionToSharedElements
import soup.animation.sample.MainGraphDirections.Companion.actionToSla
import soup.animation.sample.MainGraphDirections.Companion.actionToSpring
import soup.animation.sample.MainGraphDirections.Companion.actionToSpringRecyclerView
import soup.animation.sample.MainGraphDirections.Companion.actionToThirdParty
import soup.animation.sample.MainGraphDirections.Companion.actionToTransition
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
        HomeItem(R.drawable.ic_drawable, R.string.title_drawable_ripple) {
            actionToDrawableRipple()
        },
        HomeItem(R.drawable.ic_drawable, R.string.title_drawable_loading) {
            actionToDrawableLoading()
        },
        HomeItem(R.drawable.ic_drawable, R.string.title_drawable_gradient) {
            actionToDrawableGradient()
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
        HomeItem(R.drawable.ic_view_property, R.string.title_view_property_recycler_view) {
            actionToRecyclerView()
        },
        HomeItem(R.drawable.ic_animator, R.string.title_animator) {
            actionToAnimator()
        },
        HomeItem(R.drawable.ic_animator, R.string.title_animator_circular_reveal) {
            actionToAnimatorReveal()
        },
        HomeItem(R.drawable.ic_animator, R.string.title_animator_layout_transition) {
            actionToLayoutTransition()
        },
        HomeItem(R.drawable.ic_sla, R.string.title_sla) {
            actionToSla()
        },
        HomeItem(R.drawable.ic_spring, R.string.title_dynamic_fling) {
            actionToFling()
        },
        HomeItem(R.drawable.ic_spring, R.string.title_dynamic_spring) {
            actionToSpring()
        },
        HomeItem(R.drawable.ic_spring, R.string.title_dynamic_spring_recycler_view) {
            actionToSpringRecyclerView()
        },
        HomeItem(R.drawable.ic_transition, R.string.title_transition) {
            actionToTransition()
        },
        HomeItem(R.drawable.ic_shared_elements, R.string.title_shared_elements) {
            actionToSharedElements()
        },
        HomeItem(R.drawable.ic_third_party, R.string.title_third_party) {
            actionToThirdParty()
        },
        HomeItem(R.drawable.ic_animator, R.string.title_custom_constraint_layout) {
            actionToCustom()
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
