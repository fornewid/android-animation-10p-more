package soup.animation.sample.drawable.gradient

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import kotlinx.android.synthetic.main.fragment_drawable_gradient.*
import soup.animation.sample.R

class GradientFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_drawable_gradient, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        animatedGradientCircle.clipToOval(true)
        animatedGradientRoundRect.clipToRoundRect(
            resources.getDimension(R.dimen.gradient_clip_radius))
    }
}
