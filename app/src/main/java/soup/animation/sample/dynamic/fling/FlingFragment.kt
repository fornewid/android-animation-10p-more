package soup.animation.sample.dynamic.fling

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.doOnLayout
import androidx.dynamicanimation.animation.DynamicAnimation
import androidx.dynamicanimation.animation.FlingAnimation
import androidx.fragment.app.Fragment
import kotlinx.android.synthetic.main.fragment_dynamic_fling.*
import soup.animation.sample.R

class FlingFragment : Fragment() {

    private var maxTranslationX: Float = 0f

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_dynamic_fling, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        view.doOnLayout {
            maxTranslationX = view.measuredWidth - resources.getDimension(R.dimen.icon_size)
        }
        icon.setOnClickListener { icon.toggle() }
        icon.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) {
                icon.flingAnimationOf(DynamicAnimation.TRANSLATION_X)
                    .setStartVelocity(5000f)
                    .start()
            } else {
                icon.flingAnimationOf(DynamicAnimation.TRANSLATION_X)
                    .setStartVelocity(-5000f)
                    .start()
            }
        }
    }

    private fun View.flingAnimationOf(viewProperty: DynamicAnimation.ViewProperty): FlingAnimation {
        return FlingAnimation(this, viewProperty)
            .setFriction(1f)
            .setMinValue(0f)
            .setMaxValue(maxTranslationX)
    }
}
