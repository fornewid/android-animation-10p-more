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

class ViewPropertyFragment : Fragment() {

    private var maxTranslationX: Float = 0f

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
            val (targetTransX, targetRotation) = if (icon.isChecked) {
                Pair(0f, 0f)
            } else {
                Pair(maxTranslationX, 360f)
            }
            animate()
                .setDuration(1000)
                .setInterpolator(Interpolators.ACCELERATE_DECELERATE)
                .rotation(targetRotation)
                .translationX(targetTransX)
                .withEndAction {
                    isChecked = !isChecked
                    uiHandler.sendEmptyMessageDelayed(UPDATE_UI, 1000L)
                }
        }
    }

    companion object {

        private const val UPDATE_UI = 1
    }
}
