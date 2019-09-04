package soup.animation.sample.spring

import android.animation.ValueAnimator
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.doOnLayout
import androidx.dynamicanimation.animation.DynamicAnimation
import androidx.dynamicanimation.animation.SpringAnimation
import androidx.dynamicanimation.animation.SpringForce
import androidx.fragment.app.Fragment
import kotlinx.android.synthetic.main.fragment_spring.*
import soup.animation.sample.R
import soup.animation.sample.interpolator.Interpolators
import soup.animation.sample.util.lerp

class SpringFragment : Fragment() {

    private var maxTranslationX: Float = 0f
    private var maxTranslationY: Float = 0f

    private val animator = ValueAnimator.ofFloat(0f, 1f).apply {
        repeatMode = ValueAnimator.REVERSE
        repeatCount = ValueAnimator.INFINITE
        duration = 1_000L
        interpolator = Interpolators.LINEAR
    }

    private lateinit var springAnimation: SpringAnimation

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_spring, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        springAnimation = SpringAnimation(bug, DynamicAnimation.TRANSLATION_X)
            .setSpring(
                SpringForce()
                    .setDampingRatio(SpringForce.DAMPING_RATIO_MEDIUM_BOUNCY)
                    .setStiffness(SpringForce.STIFFNESS_VERY_LOW)
            )

        view.doOnLayout {
            maxTranslationX = view.measuredWidth - resources.getDimension(R.dimen.icon_size)
            maxTranslationY = resources.getDimension(R.dimen.icon_size) * 3

            animator.addUpdateListener {
                updateUi(it.animatedFraction)
            }
            animator.start()
        }
    }

    override fun onDestroyView() {
        animator.removeAllUpdateListeners()
        animator.cancel()
        super.onDestroyView()
    }

    private fun updateUi(fraction: Float) {
        icon.rotation = lerp(0f, 360f, fraction)
        icon.translationX = lerp(0f, maxTranslationX, fraction)
        icon.translationY = lerp(0f, -maxTranslationY, fraction)
        springAnimation.animateToFinalPosition(
            lerp(0f, maxTranslationX, fraction)
        )
    }
}
