package soup.animation.sample.drawable.ripple

import android.animation.ValueAnimator
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.animation.doOnRepeat
import androidx.core.view.postOnAnimationDelayed
import androidx.fragment.app.Fragment
import kotlinx.android.synthetic.main.fragment_drawable_ripple.*
import soup.animation.sample.R
import soup.animation.sample.interpolator.Interpolators

class RippleFragment : Fragment() {

    private val animator = ValueAnimator.ofFloat(0f, 1f).apply {
        repeatMode = ValueAnimator.REVERSE
        repeatCount = ValueAnimator.INFINITE
        duration = 1_000L
        interpolator = Interpolators.LINEAR
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_drawable_ripple, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        animator.doOnRepeat {
            updateRippleEffect()
        }
        animator.start()
    }

    override fun onDestroyView() {
        animator.removeAllListeners()
        animator.cancel()
        super.onDestroyView()
    }

    private fun updateRippleEffect() {
        rippleNormal.performRippleEffect()
        rippleBorderless.performRippleEffect()
        rippleWrong.performRippleEffect()
        rippleCustom1.performRippleEffect()
        rippleCustom2.performRippleEffect()
    }

    private fun View.performRippleEffect() {
        isPressed = true
        postOnAnimationDelayed(50) {
            isPressed = false
        }
    }
}
