package soup.animation.sample.interpolator

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import kotlinx.android.synthetic.main.fragment_interpolator.*
import soup.animation.sample.R

class InterpolatorFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_interpolator, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        easeInOutCubic.setInterpolator(Interpolators.EASE_IN_OUT_CUBIC)
        easeInOutQuad.setInterpolator(Interpolators.EASE_IN_OUT_QUAD)
    }
}
