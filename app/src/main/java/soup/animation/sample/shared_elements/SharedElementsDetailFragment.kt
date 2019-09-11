package soup.animation.sample.shared_elements

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import android.transition.TransitionInflater
import soup.animation.sample.R

class SharedElementsDetailFragment : Fragment() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enterTransition =
            TransitionInflater.from(context).inflateTransition(R.transition.move)
        sharedElementEnterTransition =
            TransitionInflater.from(context).inflateTransition(R.transition.move_shared_element)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_shared_elements_detail, container, false)
    }
}
