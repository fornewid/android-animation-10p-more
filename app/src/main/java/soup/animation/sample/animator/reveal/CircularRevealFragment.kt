package soup.animation.sample.animator.reveal

import android.animation.Animator
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewAnimationUtils
import android.view.ViewGroup
import androidx.core.animation.doOnEnd
import androidx.core.view.doOnLayout
import androidx.fragment.app.Fragment
import com.google.android.material.circularreveal.CircularRevealCompat
import com.google.android.material.circularreveal.CircularRevealLinearLayout
import com.google.android.material.circularreveal.CircularRevealWidget
import kotlinx.android.synthetic.main.fragment_animator_reveal.*
import soup.animation.sample.R
import soup.animation.sample.interpolator.Interpolators
import kotlin.math.hypot

class CircularRevealFragment : Fragment() {

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
        unfold.setOnClickListener {
            if (it.isSelected) {
                it.isSelected = false
                cardRevealView.foldMenu()
            } else {
                it.isSelected = true
                cardRevealView.unfoldMenu()
            }
        }
        dialogFab.setOnClickListener {
            CircularRevealDialogFragment()
                .show(childFragmentManager, null)
        }
        fab.setOnClickListener {
            if (it.isSelected) {
                it.isSelected = false
                revealView.hideContents()
            } else {
                it.isSelected = true
                revealView.showContents()
            }
        }
    }

    private fun CircularRevealLinearLayout.unfoldMenu() {
        visibility = View.VISIBLE
        createCircularRevealCompatOf(unfold, 0f, radius) {
            duration = 500
        }.start()
    }

    private fun CircularRevealLinearLayout.foldMenu() {
        createCircularRevealCompatOf(unfold, radius, 0f) {
            duration = 500
            doOnEnd {
                visibility = View.GONE
            }
        }.start()
    }

    private fun View.showContents() {
        visibility = View.VISIBLE
        createCircularRevealOf(fab, 0f, radius) {
            duration = 300
            interpolator = Interpolators.DECELERATE
        }.start()
    }

    private fun View.hideContents() {
        createCircularRevealOf(fab, radius, 0f) {
            duration = 300
            interpolator = Interpolators.ACCELERATE
            doOnEnd {
                visibility = View.GONE
            }
        }.start()
    }

    private inline fun View.createCircularRevealOf(
        target: View,
        startRadius: Float,
        endRadius: Float,
        block: Animator.() -> Unit
    ): Animator {
        return ViewAnimationUtils.createCircularReveal(
            this,
            target.centerX(),
            target.centerY(),
            startRadius,
            endRadius
        ).apply(block)
    }

    private inline fun CircularRevealWidget.createCircularRevealCompatOf(
        target: View,
        startRadius: Float,
        endRadius: Float,
        block: Animator.() -> Unit
    ): Animator {
        return CircularRevealCompat.createCircularReveal(
            this,
            target.centerX().toFloat(),
            target.centerY().toFloat(),
            startRadius,
            endRadius
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
