package soup.animation.sample.drawable

import android.animation.Animator
import android.animation.ArgbEvaluator
import android.animation.ValueAnimator
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.GradientDrawable
import android.util.AttributeSet
import android.view.View

class AnimatedGradientView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyle: Int = 0
) : View(context, attrs, defStyle) {

    private val startColor = Color.RED
    private val centerColor = Color.GREEN
    private val endColor = Color.BLUE

    private val drawable = GradientDrawable(
        GradientDrawable.Orientation.BR_TL,
        intArrayOf(startColor, endColor)
    )
    private val animator: Animator = createGradationAnimator(drawable)

    init {
        background = drawable
    }

    public override fun onAttachedToWindow() {
        super.onAttachedToWindow()
        updateAnim()
    }

    public override fun onDetachedFromWindow() {
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
        return ValueAnimator.ofFloat(0f, 3f).apply {
            val evaluator = ArgbEvaluator()
            duration = 3_000L
            repeatCount = ValueAnimator.INFINITE
            repeatMode = ValueAnimator.RESTART
            addUpdateListener {
                val fraction = it.animatedValue as Float
                gradationDrawable.colors = when {
                    fraction <= 1f -> intArrayOf(
                        evaluator.evaluate(fraction, startColor, centerColor) as Int,
                        evaluator.evaluate(fraction, centerColor, endColor) as Int
                    )
                    fraction <= 2f -> {
                        val fraction = fraction - 1
                        intArrayOf(
                            evaluator.evaluate(fraction, centerColor, endColor) as Int,
                            evaluator.evaluate(fraction, endColor, startColor) as Int
                        )
                    }
                    else -> {
                        val fraction = fraction - 2
                        intArrayOf(
                            evaluator.evaluate(fraction, endColor, startColor) as Int,
                            evaluator.evaluate(fraction, startColor, centerColor) as Int
                        )
                    }
                }
            }
        }
    }
}
