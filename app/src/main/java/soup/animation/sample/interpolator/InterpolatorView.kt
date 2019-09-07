package soup.animation.sample.interpolator

import android.animation.Animator
import android.animation.ValueAnimator
import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.view.View
import android.view.animation.AnimationUtils
import android.view.animation.Interpolator
import android.view.animation.LinearInterpolator
import androidx.annotation.InterpolatorRes
import soup.animation.sample.R
import kotlin.math.min

class InterpolatorView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null
) : View(context, attrs) {

    private val circleRadius =
        context.resources.getDimension(R.dimen.interpolator_circle_radius)

    private val baselinePaint = strokePaintOf(2f) {
        color = Color.DKGRAY
    }
    private val outlinePaint = strokePaintOf(2f) {
        color = Color.LTGRAY
        pathEffect = DashPathEffect(floatArrayOf(5f, 5f), 2f)
    }
    private val interpolatorLinePaint = strokePaintOf(3f) {
        color = Color.BLACK
    }
    private val circlePaint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        color = Color.RED
        style = Paint.Style.FILL
    }

    private val anim: Animator = ValueAnimator.ofFloat(0f, 1f).apply {
        duration = 1000L
        repeatMode = ValueAnimator.RESTART
        repeatCount = ValueAnimator.INFINITE
        interpolator = LinearInterpolator()
        addUpdateListener {
            fraction = it.animatedFraction
            postInvalidateOnAnimation()
        }
    }
    private var isAttached: Boolean = false

    private var interpolator: Interpolator? = null
    private var points: List<PointF> = emptyList()

    private var bound = RectF(0f, 0f, 0f, 0f)
    private var padding = 0f
    private var fraction = 0f

    fun setInterpolator(interpolator: Interpolator?) {
        this.interpolator = interpolator
        updateAnim()
    }

    fun setInterpolatorResource(@InterpolatorRes resId: Int) {
        val interpolator = AnimationUtils.loadInterpolator(context, resId)
        if (this.interpolator === interpolator) return
        this.interpolator = interpolator
        updateAnim()
    }

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        padding = (min(w, h) / 5).toFloat()
        bound = RectF(padding, padding, w - padding, h - padding)
        this.points = interpolator?.getPoints(bound.width(), bound.height()) ?: emptyList()
    }

    private fun Interpolator.getPoints(width: Float, height: Float): List<PointF> {
        return (0..width.toInt()).map {
            val input = it.toFloat()
            val fraction = input / width
            val output = getInterpolation(fraction) * height
            PointF(input, height - output)
        }
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)

        canvas.save()
        canvas.translate(padding, padding)

        val boundW = bound.width()
        val boundH = bound.height()

        // outline
        canvas.drawLine(0f, 0f, boundW + padding, 0f, outlinePaint)
        canvas.drawLine(boundW, 0f, boundW, boundH, outlinePaint)
        canvas.drawLine(boundW + padding, 0f, boundW + padding, boundH, outlinePaint)

        // baseline
        canvas.drawLine(0f, boundH, boundW + padding, boundH, baselinePaint)
        canvas.drawLine(0f, 0f, 0f, boundH, baselinePaint)

        // interpolator line
        points.forEach {
            canvas.drawPoint(it.x, it.y, interpolatorLinePaint)
        }

        // interpolator circle
        val interpolation = interpolator?.getInterpolation(fraction) ?: 0f
        canvas.drawCircle(
            boundW * fraction,
            boundH * (1 - interpolation),
            circleRadius,
            circlePaint
        )

        // interpolator circle
        canvas.drawCircle(
            boundW + padding / 2,
            boundH * (1 - interpolation),
            circleRadius,
            circlePaint
        )

        canvas.restore()
    }

    private fun updateAnim() {
        if (isAttached) {
            anim.cancel()
        }
        if (isShown) {
            anim.start()
        }
    }

    override fun onAttachedToWindow() {
        super.onAttachedToWindow()
        isAttached = true
        updateAnim()
    }

    override fun onDetachedFromWindow() {
        super.onDetachedFromWindow()
        anim.cancel()
        isAttached = false
    }

    override fun onVisibilityChanged(changedView: View, vis: Int) {
        super.onVisibilityChanged(changedView, vis)
        if (isShown) {
            anim.start()
        } else {
            anim.cancel()
        }
    }

    private inline fun strokePaintOf(width: Float, block: Paint.() -> Unit): Paint {
        return Paint(Paint.ANTI_ALIAS_FLAG).apply {
            strokeWidth = width
            style = Paint.Style.STROKE
            block()
        }
    }
}
