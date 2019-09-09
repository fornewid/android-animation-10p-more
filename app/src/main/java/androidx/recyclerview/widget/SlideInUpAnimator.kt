package androidx.recyclerview.widget

import android.animation.Animator
import android.animation.AnimatorListenerAdapter

class SlideInUpAnimator : BaseItemAnimator() {

    override fun preAnimateAddImpl(holder: RecyclerView.ViewHolder) {
        holder.itemView.translationY = holder.itemView.height.toFloat()
        holder.itemView.alpha = 0f
    }

    override fun animateAddImpl(holder: RecyclerView.ViewHolder) {
        val view = holder.itemView
        val animation = view.animate()
        mAddAnimations.add(holder)
        animation
            .translationY(0f)
            .alpha(1f)
            .setDuration(addDuration)
            .setListener(object : AnimatorListenerAdapter() {

                override fun onAnimationStart(animator: Animator?) {
                    dispatchAddStarting(holder)
                }

                override fun onAnimationCancel(animator: Animator?) {
                    view.alpha = 1f
                }

                override fun onAnimationEnd(animator: Animator?) {
                    animation.setListener(null)
                    dispatchAddFinished(holder);
                    mAddAnimations.remove(holder);
                    dispatchFinishedWhenDone();
                }
            })
            .start()
    }

    override fun animateRemoveImpl(holder: RecyclerView.ViewHolder) {
        val view = holder.itemView
        val animation = view.animate()
        mRemoveAnimations.add(holder)
        animation
            .translationY(holder.itemView.height.toFloat())
            .alpha(0f)
            .setDuration(removeDuration)
            .setListener(
                object : AnimatorListenerAdapter() {

                    override fun onAnimationStart(animator: Animator) {
                        dispatchRemoveStarting(holder)
                    }

                    override fun onAnimationEnd(animator: Animator) {
                        animation.setListener(null)
                        view.alpha = 1f
                        dispatchRemoveFinished(holder)
                        mRemoveAnimations.remove(holder)
                        dispatchFinishedWhenDone()
                    }
                }
            )
            .start()
    }
}
