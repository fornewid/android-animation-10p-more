package soup.animation.sample.view_property

import android.os.Bundle
import android.os.Handler
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.doOnLayout
import androidx.fragment.app.Fragment
import kotlinx.android.synthetic.main.fragment_view_property.*
import soup.animation.sample.R
import soup.animation.sample.interpolator.Interpolators

class ViewPropertyFragment : Fragment(), ViewAnimation {

    private var maxTranslationX: Float = 0f
    private var maxTranslationY: Float = 0f

    private val uiHandler = Handler(Handler.Callback {
        updateUi()
        true
    })

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_view_property, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        view.doOnLayout {
            maxTranslationX = view.measuredWidth - resources.getDimension(R.dimen.icon_size)
            maxTranslationY = resources.getDimension(R.dimen.icon_size) * -2
            uiHandler.sendEmptyMessage(UPDATE_UI)
        }
    }

    override fun onDestroyView() {
        uiHandler.removeMessages(UPDATE_UI)
        super.onDestroyView()
    }

    private fun updateUi() {
        icon?.run {
            animate().cancel()
            val targetTransX: Float
            val targetTransY: Float
            val targetRotation: Float
            val targetScaleX: Float
            val targetScaleY: Float
            val targetAlpha: Float
            if (icon.isChecked) {
                targetTransX = 0f
                targetTransY = 0f
                targetRotation = 0f
                targetScaleX = 1f
                targetScaleY = 1f
                targetAlpha = 1f
            } else {
                targetTransX = maxTranslationX
                targetTransY = maxTranslationY
                targetRotation = 360f
                targetScaleX = 2f
                targetScaleY = .5f
                targetAlpha = .5f
            }
            animate()
                .setDuration(1000)
                .setInterpolator(Interpolators.ACCELERATE_DECELERATE)
                .rotation(targetRotation)
                .scaleX(targetScaleX)
                .scaleY(targetScaleY)
                .alpha(targetAlpha)
                .translationX(targetTransX)
                .translationY(targetTransY)
                .withEndAction {
                    isChecked = !isChecked
                    uiHandler.sendEmptyMessage(UPDATE_UI)
                }

            if (icon.isChecked) {
                dim.animateOutForDim()
                flashButton.animateOutForHeader()
                starButton.animateOutForHeader()
                toyButton.animateOutForHeader()
                closeButton.animateOutForCloseButton()
            } else {
                dim.animateInForDim()
                flashButton.animateInForHeader(0)
                starButton.animateInForHeader(70)
                toyButton.animateInForHeader(140)
                closeButton.animateInForCloseButton()
            }
        }
    }

    companion object {

        private const val UPDATE_UI = 1
    }
}
