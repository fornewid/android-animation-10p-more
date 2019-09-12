package soup.animation.sample.drawable

import android.animation.ValueAnimator
import android.graphics.drawable.Animatable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.annotation.DrawableRes
import androidx.core.animation.doOnRepeat
import androidx.core.content.ContextCompat
import androidx.core.view.postOnAnimationDelayed
import androidx.fragment.app.Fragment
import kotlinx.android.synthetic.main.fragment_drawable.*
import soup.animation.sample.R
import soup.animation.sample.interpolator.Interpolators

class DrawableFragment : Fragment() {

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
        return inflater.inflate(R.layout.fragment_drawable, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        animator.doOnRepeat {
            updateRippleEffect()
        }
        animator.addUpdateListener {
            updateUi(it.animatedFraction)
        }
        animator.start()
    }

    override fun onDestroyView() {
        animator.removeAllListeners()
        animator.removeAllUpdateListeners()
        animator.cancel()
        super.onDestroyView()
    }

    private fun updateRippleEffect() {
        animatedVector.toggle()
        batteryIcon.performRippleEffect()
        batteryIcon.toggle()
        starIcon.toggle()

        portrait.run {
            if (isActivated) {
                isActivated = false
                setImageDrawableAndStart(R.drawable.ic_portrait_from_auto_rotate_animation)
            } else {
                isActivated = true
                setImageDrawableAndStart(R.drawable.ic_portrait_to_auto_rotate_animation)
            }
        }
        landscape.run {
            if (isActivated) {
                isActivated = false
                setImageDrawableAndStart(R.drawable.ic_landscape_from_auto_rotate_animation)
            } else {
                isActivated = true
                setImageDrawableAndStart(R.drawable.ic_landscape_to_auto_rotate_animation)
            }
        }
        flashlight.run {
            if (isActivated) {
                isActivated = false
                setImageDrawableAndStart(R.drawable.ic_signal_flashlight_enable_animation)
            } else {
                isActivated = true
                setImageDrawableAndStart(R.drawable.ic_signal_flashlight_disable_animation)
            }
        }
    }

    private fun updateUi(fraction: Float) {
        batteryClip.setImageLevel((fraction * 10000).toInt())
        batteryChargingClip.setImageLevel((fraction * 10000).toInt())
    }

    private fun View.performRippleEffect() {
        isPressed = true
        postOnAnimationDelayed(50) {
            isPressed = false
        }
    }

    private fun ImageView.setImageDrawableAndStart(@DrawableRes resId: Int) {
        val drawable = ContextCompat.getDrawable(context, resId)
        setImageDrawable(drawable)
        if (drawable is Animatable) {
            drawable.start()
        }
    }
}
