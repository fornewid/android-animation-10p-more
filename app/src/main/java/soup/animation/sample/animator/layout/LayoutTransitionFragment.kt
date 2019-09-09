package soup.animation.sample.animator.layout

import android.animation.LayoutTransition
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.core.view.size
import androidx.fragment.app.Fragment
import kotlinx.android.synthetic.main.fragment_animator_layout_transition.*
import kotlinx.android.synthetic.main.item_home.view.*
import soup.animation.sample.R
import soup.animation.sample.interpolator.Interpolators

class LayoutTransitionFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_animator_layout_transition, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        container.layoutTransition?.apply {
            setInterpolator(LayoutTransition.APPEARING, Interpolators.ACCELERATE_DECELERATE)
            setInterpolator(LayoutTransition.DISAPPEARING, Interpolators.ACCELERATE_DECELERATE)
            setInterpolator(LayoutTransition.CHANGE_APPEARING, Interpolators.DECELERATE)
            setInterpolator(LayoutTransition.CHANGE_DISAPPEARING, Interpolators.DECELERATE)
            setInterpolator(LayoutTransition.CHANGING, Interpolators.DECELERATE)
            setDuration(LayoutTransition.APPEARING, 300)
            setDuration(LayoutTransition.DISAPPEARING, 300)
            setDuration(LayoutTransition.CHANGE_APPEARING, 300)
            setDuration(LayoutTransition.CHANGE_DISAPPEARING, 300)
            setDuration(LayoutTransition.CHANGING, 300)
        }
        fab.setOnClickListener {
            container.addView(
                container.inflate(R.layout.item_recyclerview).apply {
                    icon.setImageResource(R.drawable.ic_spring)
                    text.text = "Item ${container.size + 1}"
                }
            )
        }
    }

    private fun ViewGroup.inflate(@LayoutRes layoutResId: Int): View {
        return LayoutInflater.from(context)
            .inflate(layoutResId, this, false)
    }
}
