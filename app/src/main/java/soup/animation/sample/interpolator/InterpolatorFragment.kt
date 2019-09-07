package soup.animation.sample.interpolator

import android.animation.ValueAnimator
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import android.view.animation.Interpolator
import androidx.core.view.doOnLayout
import androidx.fragment.app.Fragment
import kotlinx.android.synthetic.main.fragment_interpolator.*
import soup.animation.sample.R
import soup.animation.sample.interpolator.Interpolators.ACCELERATE
import soup.animation.sample.interpolator.Interpolators.ACCELERATE_DECELERATE
import soup.animation.sample.interpolator.Interpolators.DECELERATE
import soup.animation.sample.interpolator.Interpolators.EASE_IN_OUT_CUBIC
import soup.animation.sample.interpolator.Interpolators.EASE_IN_OUT_QUAD
import soup.animation.sample.interpolator.Interpolators.LINEAR
import soup.animation.sample.interpolator.Interpolators.SPRING

class InterpolatorFragment : Fragment() {

    private lateinit var BOUNCE: Interpolator

    private var translationX: Int = 0
    private val animator = ValueAnimator.ofFloat(0f, 1f).apply {
        repeatMode = ValueAnimator.REVERSE
        repeatCount = ValueAnimator.INFINITE
        duration = 1_000L
        interpolator = LINEAR
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_interpolator, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        BOUNCE = AnimationUtils.loadInterpolator(context, android.R.interpolator.bounce)
        interpolator.setInterpolatorResource(android.R.interpolator.overshoot)
        view.doOnLayout {
            translationX = view.measuredWidth - resources.getDimensionPixelSize(R.dimen.icon_size)
        }
        animator.addUpdateListener {
            updateUi(it.animatedFraction)
        }
        animator.start()
    }

    override fun onDestroyView() {
        animator.removeAllUpdateListeners()
        animator.cancel()
        super.onDestroyView()
    }

    private fun updateUi(fraction: Float) {
        linear.translationX = translationX * LINEAR.getInterpolation(fraction)
        accelerate.translationX = translationX * ACCELERATE.getInterpolation(fraction)
        decelerate.translationX = translationX * DECELERATE.getInterpolation(fraction)
        accelerateDecelerate.translationX = translationX * ACCELERATE_DECELERATE.getInterpolation(fraction)
        spring.translationX = translationX * SPRING.getInterpolation(fraction)
        bounce.translationX = translationX * BOUNCE.getInterpolation(fraction)
        easeInOutCubic.translationX = translationX * EASE_IN_OUT_CUBIC.getInterpolation(fraction)
        easeInOutQuad.translationX = translationX * EASE_IN_OUT_QUAD.getInterpolation(fraction)
    }
}
