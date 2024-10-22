package androidx.recyclerview.widget

import android.annotation.SuppressLint
import android.view.Gravity
import android.view.View
import androidx.dynamicanimation.animation.SpringAnimation.*
import soup.animation.sample.util.listenForAllSpringsEnd
import soup.animation.sample.util.spring

/**
 * A [RecyclerView.ItemAnimator] that fades & slides newly added items in from a given
 * direction.
 *
 * Refer to Plaid (https://github.com/android/plaid)
 */
open class SpringSlideInAnimator @JvmOverloads constructor(
    slideFromEdge: Int = Gravity.BOTTOM, // Default to sliding in upward
    layoutDirection: Int = -1
) : DefaultItemAnimator() {

    private val slideFromEdge: Int = Gravity.getAbsoluteGravity(slideFromEdge, layoutDirection)
    private val pendingAdds = mutableListOf<RecyclerView.ViewHolder>()
    private val runningAdds = mutableListOf<RecyclerView.ViewHolder>()
    private val pendingMoves = mutableListOf<RecyclerView.ViewHolder>()
    private val runningMoves = mutableListOf<RecyclerView.ViewHolder>()

    @SuppressLint("RtlHardcoded")
    override fun animateAdd(holder: RecyclerView.ViewHolder): Boolean {
        holder.itemView.alpha = 0f
        when (slideFromEdge) {
            Gravity.LEFT -> holder.itemView.translationX = -holder.itemView.width / 3f
            Gravity.TOP -> holder.itemView.translationY = -holder.itemView.height / 3f
            Gravity.RIGHT -> holder.itemView.translationX = holder.itemView.width / 3f
            // Gravity.BOTTOM
            else -> holder.itemView.translationY = holder.itemView.height / 3f
        }
        pendingAdds.add(holder)
        return true
    }

    override fun animateMove(
        holder: RecyclerView.ViewHolder?,
        fromViewX: Int,
        fromViewY: Int,
        toViewX: Int,
        toViewY: Int
    ): Boolean {
        holder ?: return false
        val view = holder.itemView
        val fromX = fromViewX + holder.itemView.translationX.toInt()
        val fromY = fromViewY + holder.itemView.translationY.toInt()
        endAnimation(holder)
        val deltaX = toViewX - fromX
        val deltaY = toViewY - fromY
        if (deltaX == 0 && deltaY == 0) {
            dispatchMoveFinished(holder)
            return false
        }
        if (deltaX != 0) {
            view.translationX = (-deltaX).toFloat()
        }
        if (deltaY != 0) {
            view.translationY = (-deltaY).toFloat()
        }
        pendingMoves.add(holder)
        return true
    }

    override fun runPendingAnimations() {
        super.runPendingAnimations()
        if (pendingAdds.isNotEmpty()) {
            for (i in pendingAdds.indices.reversed()) {
                addItem(pendingAdds.removeAt(i))
            }
        }

        if (pendingMoves.isNotEmpty()) {
            for (i in pendingMoves.indices.reversed()) {
                moveItem(pendingMoves.removeAt(i))
            }
        }
    }

    override fun endAnimation(holder: RecyclerView.ViewHolder) {
        if (pendingAdds.contains(holder)) endPendingAdd(holder)
        if (runningAdds.contains(holder)) endRunningAdd(holder)
        if (pendingMoves.contains(holder)) endPendingMove(holder)
        if (runningMoves.contains(holder)) endRunningMove(holder)
        super.endAnimation(holder)
    }

    override fun endAnimations() {
        pendingAdds.forEach(::endPendingAdd)
        runningAdds.forEach(::endRunningAdd)
        pendingMoves.forEach(::endPendingMove)
        runningMoves.forEach(::endRunningMove)
        super.endAnimations()
    }

    override fun isRunning() =
        pendingAdds.isNotEmpty() ||
                runningAdds.isNotEmpty() ||
                pendingMoves.isNotEmpty() ||
                runningMoves.isNotEmpty() ||
                super.isRunning()

    private fun addItem(holder: RecyclerView.ViewHolder) {
        val springAlpha = holder.itemView.spring(ALPHA)
        val springTranslationX = holder.itemView.spring(TRANSLATION_X)
        val springTranslationY = holder.itemView.spring(TRANSLATION_Y)
        listenForAllSpringsEnd({ cancelled ->
            if (cancelled) {
                clearAnimatedValues(holder.itemView)
            }
            dispatchAddFinished(holder)
            dispatchFinishedWhenDone()
            runningAdds -= holder
        }, springAlpha, springTranslationX, springTranslationY)
        springAlpha.animateToFinalPosition(1f)
        springTranslationX.animateToFinalPosition(0f)
        springTranslationY.animateToFinalPosition(0f)
        dispatchAddStarting(holder)
        runningAdds += holder
    }

    private fun moveItem(holder: RecyclerView.ViewHolder) {
        val springX = holder.itemView.spring(TRANSLATION_X)
        val springY = holder.itemView.spring(TRANSLATION_Y)
        listenForAllSpringsEnd({ cancelled ->
            if (cancelled) {
                clearAnimatedValues(holder.itemView)
            }
            dispatchMoveFinished(holder)
            dispatchFinishedWhenDone()
            runningMoves -= holder
        }, springX, springY)
        springX.animateToFinalPosition(0f)
        springY.animateToFinalPosition(0f)
        dispatchMoveStarting(holder)
        runningMoves += holder
    }

    private fun endPendingAdd(holder: RecyclerView.ViewHolder) {
        clearAnimatedValues(holder.itemView)
        dispatchAddFinished(holder)
        pendingAdds -= holder
    }

    private fun endRunningAdd(holder: RecyclerView.ViewHolder) {
        holder.itemView.spring(ALPHA).cancel()
        holder.itemView.spring(TRANSLATION_X).cancel()
        holder.itemView.spring(TRANSLATION_Y).cancel()
        runningAdds -= holder
    }

    private fun endPendingMove(holder: RecyclerView.ViewHolder) {
        clearAnimatedValues(holder.itemView)
        dispatchMoveFinished(holder)
        pendingMoves -= holder
    }

    private fun endRunningMove(holder: RecyclerView.ViewHolder) {
        holder.itemView.spring(TRANSLATION_X).cancel()
        holder.itemView.spring(TRANSLATION_Y).cancel()
        runningMoves -= holder
    }

    override fun dispatchFinishedWhenDone() {
        if (!isRunning) {
            dispatchAnimationsFinished()
        }
    }

    private fun clearAnimatedValues(view: View) {
        view.alpha = 1f
        view.translationX = 0f
        view.translationY = 0f
    }
}
