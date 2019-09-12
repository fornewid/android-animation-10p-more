package soup.animation.sample.drawable.loading

import android.animation.ValueAnimator
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import androidx.core.animation.doOnRepeat
import androidx.core.view.isVisible
import androidx.core.widget.ContentLoadingProgressBar
import androidx.fragment.app.Fragment
import kotlinx.android.synthetic.main.fragment_drawable_loading.*
import soup.animation.sample.R
import soup.animation.sample.interpolator.Interpolators
import soup.animation.sample.util.lerp

class LoadingFragment : Fragment() {

    private val animator = ValueAnimator.ofFloat(0f, 1f).apply {
        repeatMode = ValueAnimator.REVERSE
        repeatCount = ValueAnimator.INFINITE
        duration = 1_000L
        interpolator = Interpolators.ACCELERATE
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_drawable_loading, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        animator.doOnRepeat {
            toggleVisibility()
        }
        animator.addUpdateListener {
            loadingHorizontal.setProgress(it.animatedFraction)
            loadingHorizontalCustom.setProgress(it.animatedFraction)
            contentLoadingHorizontal.setProgress(it.animatedFraction)
            contentLoadingHorizontalCustom.setProgress(it.animatedFraction)
        }
        animator.start()
        stage.startStageAnimation(1f)
    }

    override fun onDestroyView() {
        animator.cancel()
        animator.removeAllListeners()
        animator.removeAllUpdateListeners()
        super.onDestroyView()
    }

    private fun toggleVisibility() {
        loading.toggleVisible()
        loadingCustom.toggleVisible()
        contentLoading.showOrHide()
        contentLoadingCustom.showOrHide()
    }

    private fun ProgressBar.setProgress(fraction: Float) {
        progress = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            lerp(min, max, fraction).toInt()
        } else {
            lerp(0, max, fraction).toInt()
        }
    }

    private fun ProgressBar.toggleVisible() {
        visibility = if (isVisible) {
            View.GONE
        } else {
            View.VISIBLE
        }
    }

    private fun ContentLoadingProgressBar.showOrHide() {
        if (isVisible) {
            hide()
        } else {
            show()
        }
    }
}
