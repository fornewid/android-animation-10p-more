package soup.animation.sample.transition

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.transition.AutoTransition
import androidx.transition.TransitionManager
import kotlinx.android.synthetic.main.fragment_transition.*
import soup.animation.sample.R

class TransitionFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_transition, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        fab.setOnClickListener {
            // val transition = TransitionInflater.from(it.context).inflateTransition(R.transition.auto_transition)
            // TransitionManager.beginDelayedTransition(container, transition)
            TransitionManager.beginDelayedTransition(container, AutoTransition())
            outerBackground.switchVisibility()
            innerBackground.switchVisibility()
        }
    }

    private fun View.switchVisibility() {
        visibility = if (this.isVisible) {
            View.GONE
        } else {
            View.VISIBLE
        }
    }
}
