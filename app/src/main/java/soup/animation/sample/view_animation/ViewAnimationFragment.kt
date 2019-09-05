package soup.animation.sample.view_animation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.*
import android.view.animation.Animation.RELATIVE_TO_SELF
import androidx.core.view.doOnLayout
import androidx.fragment.app.Fragment
import kotlinx.android.synthetic.main.fragment_view_animation.*
import soup.animation.sample.R

class ViewAnimationFragment : Fragment() {

    private var maxTranslationX: Float = 0f
    private var maxTranslationY: Float = 0f

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_view_animation, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        view.doOnLayout {
            maxTranslationX = view.measuredWidth - resources.getDimension(R.dimen.icon_size)
            maxTranslationY = resources.getDimension(R.dimen.icon_size) * -2
            startAnimation()
        }
    }

    override fun onDestroyView() {
        icon.clearAnimation()
        super.onDestroyView()
    }

    private fun startAnimation() {
        val animation = AnimationSet(true).apply {
            duration = 1000
            setInterpolator(context, android.R.interpolator.accelerate_decelerate)
            addAnimation(
                ScaleAnimation(1f, 2f, 1f, .5f, RELATIVE_TO_SELF, .5f, RELATIVE_TO_SELF, .5f)
                    .withRepeat()
            )
            addAnimation(
                AlphaAnimation(1f, .5f)
                    .withRepeat()
            )
            addAnimation(
                RotateAnimation(0f, 360f, RELATIVE_TO_SELF, .5f, RELATIVE_TO_SELF, .5f)
                    .withRepeat()
            )
            addAnimation(
                TranslateAnimation(0f, maxTranslationX, 0f, maxTranslationY)
                    .withRepeat()
            )
//                setAnimationListener(object : Animation.AnimationListener {
//
//                    override fun onAnimationStart(animation: Animation) {
//                    }
//
//                    override fun onAnimationRepeat(animation: Animation) {
//                    }
//
//                    override fun onAnimationEnd(animation: Animation) {
//                    }
//                })
        }
        icon.startAnimation(animation)
    }

    private fun Animation.withRepeat(): Animation {
        repeatCount = Animation.INFINITE
        repeatMode = Animation.REVERSE
        return this
    }
}
