package soup.animation.sample.drawable.gradient

import android.content.Context
import android.graphics.drawable.AnimationDrawable
import android.util.AttributeSet
import soup.animation.sample.drawable.AnimatedImageView

class AnimatedFadeImageView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null
) : AnimatedImageView(context, attrs) {

    override fun setAnimationDrawable(drawable: AnimationDrawable?) {
        super.setAnimationDrawable(drawable)
        drawable?.run {
            setEnterFadeDuration(0)
            setExitFadeDuration(1000)
        }
    }
}
