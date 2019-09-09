package androidx.recyclerview.widget

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.view.View
import androidx.dynamicanimation.animation.DynamicAnimation
import androidx.dynamicanimation.animation.SpringAnimation
import androidx.dynamicanimation.animation.SpringForce
import soup.animation.sample.R

class SpringMoveAnimator : BaseItemAnimator() {

    override fun animateMoveImpl(holder: RecyclerView.ViewHolder, fromX: Int, fromY: Int, toX: Int, toY: Int) {
        val view = holder.itemView
        val deltaX = toX - fromX
        val deltaY = toY - fromY
        if (deltaX != 0) {
            view.spring(DynamicAnimation.TRANSLATION_X)
                .animateToFinalPosition(0f)
        }
        if (deltaY != 0) {
            view.spring(DynamicAnimation.TRANSLATION_Y)
                .animateToFinalPosition(0f)
        }
        // TODO: make EndActions end listeners instead, since end actions aren't called when
        // vpas are canceled (and can't end them. why?)
        // need listener functionality in VPACompat for this. Ick.
        val animation = view.animate()
        mMoveAnimations.add(holder)
        animation.setDuration(moveDuration).setListener(object : AnimatorListenerAdapter() {
            override fun onAnimationStart(animator: Animator) {
                dispatchMoveStarting(holder)
            }

            override fun onAnimationCancel(animator: Animator) {
                if (deltaX != 0) {
                    view.translationX = 0f
                }
                if (deltaY != 0) {
                    view.translationY = 0f
                }
            }

            override fun onAnimationEnd(animator: Animator) {
                animation.setListener(null)
                dispatchMoveFinished(holder)
                mMoveAnimations.remove(holder)
                dispatchFinishedWhenDone()
            }
        }).start()
    }

    private fun View.spring(
        property: DynamicAnimation.ViewProperty = DynamicAnimation.TRANSLATION_X,
        damping: Float = SpringForce.DAMPING_RATIO_NO_BOUNCY,
        stiffness: Float = 500f
    ): SpringAnimation {
        val key = getKey(property)
        val springAnim = getTag(key) as? SpringAnimation?
        if (springAnim != null) {
            return springAnim
        }
        return SpringAnimation(this, property).apply {
            spring = SpringForce().apply {
                this.dampingRatio = damping
                this.stiffness = stiffness
            }
            setTag(key, this)
        }
    }

    private fun getKey(property: DynamicAnimation.ViewProperty): Int {
        return when (property) {
            DynamicAnimation.TRANSLATION_X -> R.id.translation_x
            DynamicAnimation.TRANSLATION_Y -> R.id.translation_y
            else -> return 0
        }
    }
}
