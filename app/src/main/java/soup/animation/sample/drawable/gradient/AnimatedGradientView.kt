package soup.animation.sample.drawable.gradient

import android.animation.Animator
import android.animation.ArgbEvaluator
import android.animation.ValueAnimator
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.GradientDrawable
import android.util.AttributeSet
import android.view.View
import android.view.animation.LinearInterpolator

open class AnimatedGradientView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyle: Int = 0
) : View(context, attrs, defStyle) {

    private val colors = createColors()

    private fun createColors(): IntArray {
        return intArrayOf(
            Color.RED,
            Color.GREEN,
            Color.BLUE
        )
    }

    private val drawable = GradientDrawable(
        GradientDrawable.Orientation.BR_TL,
        colors.take(2).toIntArray()
    )
    private val animator: Animator = createGradationAnimator(drawable)

    init {
        background = drawable
    }

    override fun onAttachedToWindow() {
        super.onAttachedToWindow()
        updateAnim()
    }

    override fun onDetachedFromWindow() {
        super.onDetachedFromWindow()
        animator.cancel()
    }

    override fun onVisibilityChanged(changedView: View, vis: Int) {
        super.onVisibilityChanged(changedView, vis)
        if (isShown) {
            updateAnim()
        } else {
            animator.cancel()
        }
    }

    private fun updateAnim() {
        if (isAttachedToWindow) {
            animator.cancel()
        }
        if (isShown) {
            animator.start()
        }
    }

    private fun createGradationAnimator(gradationDrawable: GradientDrawable): Animator {
        val max = colors.size
        return ValueAnimator.ofFloat(0f, max.toFloat()).apply {
            val evaluator = ArgbEvaluator()
            interpolator = LinearInterpolator()
            duration = 3_000L
            repeatCount = ValueAnimator.INFINITE
            repeatMode = ValueAnimator.RESTART
            addUpdateListener {
                val index = (it.animatedValue as Float).toInt()
                val fraction = it.animatedValue as Float - index
                val start = colors[index % max]
                val center = colors[(index + 1) % max]
                val end = colors[(index + 2) % max]
                gradationDrawable.colors = intArrayOf(
                    evaluator.evaluate(fraction, start, center) as Int,
                    evaluator.evaluate(fraction, center, end) as Int
                )
            }
        }
    }
}
