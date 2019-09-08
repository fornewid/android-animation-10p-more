package soup.animation.sample.animator_reveal

import android.animation.Animator
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewAnimationUtils
import android.view.ViewGroup
import androidx.core.animation.doOnEnd
import androidx.core.view.doOnLayout
import androidx.fragment.app.Fragment
import kotlinx.android.synthetic.main.fragment_animator_reveal.*
import soup.animation.sample.R
import soup.animation.sample.interpolator.Interpolators
import kotlin.math.hypot

class AnimatorRevealFragment : Fragment() {

    private var isShown = true
    private var radius = 0f

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_animator_reveal, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        view.doOnLayout {
            radius = it.diagonalLength()
        }
        fab.setOnClickListener {
            if (isShown) {
                isShown = false
                hideContents()
            } else {
                isShown = true
                showContents()
            }
        }
    }

    private fun hideContents() {
        fab.isSelected = true

        revealView.visibility = View.VISIBLE
        revealView.createCircularRevealTo(fab, radius) {
            duration = 300
            interpolator = Interpolators.DECELERATE
        }.start()
    }

    private fun showContents() {
        fab.isSelected = false

        revealView.createCircularRevealFrom(fab, radius) {
            duration = 300
            interpolator = Interpolators.ACCELERATE
            doOnEnd {
                revealView.visibility = View.GONE
            }
        }.start()
    }

    private inline fun View.createCircularRevealTo(
        target: View,
        radius: Float,
        block: Animator.() -> Unit
    ): Animator {
        return ViewAnimationUtils.createCircularReveal(
            this,
            target.centerX(),
            target.centerY(),
            0f,
            radius
        ).apply(block)
    }

    private inline fun View.createCircularRevealFrom(
        target: View,
        radius: Float,
        block: Animator.() -> Unit
    ): Animator {
        return ViewAnimationUtils.createCircularReveal(
            this,
            target.centerX(),
            target.centerY(),
            radius,
            0f
        ).apply(block)
    }

    private fun View.centerX(): Int {
        return (right + left) / 2
    }

    private fun View.centerY(): Int {
        return (bottom + top) / 2
    }

    private fun View.diagonalLength(): Float {
        return hypot(width.toFloat(), height.toFloat())
    }
}
