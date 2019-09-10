package soup.animation.sample.drawable.loading

import android.animation.ValueAnimator
import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.annotation.DrawableRes
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.animation.doOnEnd
import androidx.core.content.ContextCompat
import kotlinx.android.synthetic.main.view_stage.view.*
import soup.animation.sample.R
import soup.animation.sample.interpolator.Interpolators

class StageView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyle: Int = 0
) : ConstraintLayout(context, attrs, defStyle) {

    private var progressAnimator: ValueAnimator? = null

    init {
        View.inflate(context, R.layout.view_stage, this)
    }

    fun startStageAnimation(currentOffset: Float) {
        resetStageEventAnimation()
        startAnimation(currentOffset)
    }

    private fun resetStageEventAnimation() {
        progress.setMutateImageResource(R.drawable.progress)
        progressThumb.run {
            cancelAnimation()
            scaleX = .3f
            scaleX = .3f
            progressThumb.setAnimation(R.raw.stage_loop)
            progressThumb.repeatCount = 0
            progressThumb.frame = 0
        }
    }

    private fun ImageView.setMutateImageResource(@DrawableRes resId: Int) {
        ContextCompat.getDrawable(context, resId)?.let {
            setImageDrawable(it.mutate())
        }
    }

    private fun startAnimation(offset: Float) {
        progressAnimator = ValueAnimator.ofFloat(0f, offset).apply {
            startDelay = 350
            duration = (1000 * offset).toLong()
            interpolator = Interpolators.EASE_OUT_CUBIC
            addUpdateListener {
                renderProgress(it.animatedFraction)
            }
            doOnEnd {
                renderEnd()
            }
            start()
        }
    }

    private fun renderProgress(fraction: Float) {
        progress?.setImageLevel((10000 * fraction).toInt())
        progressThumb?.translationX = progress.width * fraction
        when {
            fraction >= 1f -> count3?.startBounceAnimation()
            fraction >= .66f -> count2?.startBounceAnimation()
            fraction >= .33f -> count1?.startBounceAnimation()
        }
    }

    private fun renderEnd() {
        progressThumb.animate()
            .setDuration(700)
            .setInterpolator(Interpolators.EASE_OUT_EXPO)
            .scaleX(1f)
            .scaleY(1f)
    }

    private fun TextView.startBounceAnimation() {
        if (isSelected) return
        isSelected = true
        animate()
            .scaleX(1.2f)
            .scaleY(1.2f)
            .setDuration(70)
            .setInterpolator(Interpolators.EASE_OUT_EXPO)
            .withLayer()
            .withEndAction {
                animate()
                    .scaleX(1f)
                    .scaleY(1f)
                    .setDuration(330)
                    .start()
            }
    }

    override fun onDetachedFromWindow() {
        stopStageEventAnimation()
        super.onDetachedFromWindow()
    }

    private fun stopStageEventAnimation() {
        count1?.animate()?.cancel()
        count2?.animate()?.cancel()
        count3?.animate()?.cancel()

        progressAnimator?.run {
            cancel()
            removeAllUpdateListeners()
            removeAllListeners()
        }
        progressAnimator = null
    }
}
