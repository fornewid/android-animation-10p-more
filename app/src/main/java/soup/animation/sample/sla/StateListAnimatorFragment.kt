package soup.animation.sample.sla

import android.os.Bundle
import android.transition.AutoTransition
import android.transition.TransitionManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import kotlinx.android.synthetic.main.fragment_sla.*
import soup.animation.sample.R

class StateListAnimatorFragment : Fragment() {

    private val translation by lazy {
        resources.getDimension(R.dimen.animator_sla_translation)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_sla, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        itemView.setOnClickListener {
            setExpanded(it.isActivated.not())
        }
    }

    private fun setExpanded(expand: Boolean) {
        val duration = 200L
        val absTransX = if (expand) translation else 0f
        title.animate().setDuration(duration).translationX(-absTransX)
        description.animate().setDuration(duration).translationX(-absTransX)
        unfold.animate().setDuration(duration).translationX(absTransX)

        TransitionManager.beginDelayedTransition(container, AutoTransition().apply {
            this.duration = duration
        })
        itemView.isActivated = expand
        actionGroup.isVisible = expand
    }
}
