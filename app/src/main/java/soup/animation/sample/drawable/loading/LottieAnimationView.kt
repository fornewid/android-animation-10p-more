package soup.animation.sample.drawable.loading

import android.animation.Animator
import androidx.annotation.RawRes
import com.airbnb.lottie.LottieAnimationView
import com.airbnb.lottie.LottieCompositionFactory

/**
 * Add a listener to this LottieAnimationView using the provided actions.
 *
 * @return the [Animator.AnimatorListener] added to the LottieAnimationView
 */
inline fun LottieAnimationView.addAnimatorListener(
    crossinline onEnd: (animator: Animator) -> Unit = {},
    crossinline onStart: (animator: Animator) -> Unit = {},
    crossinline onCancel: (animator: Animator) -> Unit = {},
    crossinline onRepeat: (animator: Animator) -> Unit = {}
): Animator.AnimatorListener {
    val listener = object : Animator.AnimatorListener {
        override fun onAnimationRepeat(animator: Animator) = onRepeat(animator)
        override fun onAnimationEnd(animator: Animator) = onEnd(animator)
        override fun onAnimationCancel(animator: Animator) = onCancel(animator)
        override fun onAnimationStart(animator: Animator) = onStart(animator)
    }
    addAnimatorListener(listener)
    return listener
}

inline fun LottieAnimationView.loadComposition(
    @RawRes rawRes: Int,
    crossinline endAction: LottieAnimationView.() -> Unit
) {
    LottieCompositionFactory.fromRawRes(context, rawRes).addListener {
        setComposition(it)
        endAction()
    }
}
