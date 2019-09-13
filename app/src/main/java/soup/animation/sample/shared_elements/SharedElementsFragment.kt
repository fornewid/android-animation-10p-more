package soup.animation.sample.shared_elements

import android.os.Bundle
import android.transition.TransitionInflater
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.FragmentNavigatorExtras
import androidx.navigation.fragment.findNavController
import kotlinx.android.synthetic.main.fragment_shared_elements.*
import soup.animation.sample.MainGraphDirections.Companion.actionToSharedElementsDetail
import soup.animation.sample.R

class SharedElementsFragment : Fragment() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        exitTransition =
            TransitionInflater.from(context).inflateTransition(R.transition.move)
        reenterTransition =
            TransitionInflater.from(context).inflateTransition(R.transition.move)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_shared_elements, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        fab.setOnClickListener {
            val extras = FragmentNavigatorExtras(
                profileButton to profileButton.transitionName
            )
            findNavController().navigate(actionToSharedElementsDetail(), extras)
        }
    }
}
