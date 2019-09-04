package soup.animation.sample.interpolator

import android.graphics.Path
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import androidx.core.view.animation.PathInterpolatorCompat
import androidx.fragment.app.Fragment
import soup.animation.sample.R

class InterpolatorFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_interpolator, container, false)
    }

    fun aa() {
        PathInterpolatorCompat.create(0.33f, 0f)
        PathInterpolatorCompat.create(0.33f, 0f, 1f, 1f)
        PathInterpolatorCompat.create(Path().apply {
            moveTo(0f,0f)
            lineTo(1f, 0f)
            lineTo(0f, 1f)
        })
        AnimationUtils.loadInterpolator(context, android.R.interpolator.linear)
    }
}
