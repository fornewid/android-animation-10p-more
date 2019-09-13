package soup.animation.sample.view_property

import android.view.View
import soup.animation.sample.interpolator.Interpolators

interface ViewAnimation {

    fun View.animateInForDim() {
        animate().cancel()
        alpha = 1f
        animate()
            .setDuration(470)
            .setInterpolator(Interpolators.EASE_OUT_EXPO)
            .withLayer()
    }

    fun View.animateOutForDim() {
        animate().cancel()
        alpha = 1f
        animate()
            .setStartDelay(300)
            .setDuration(170)
            .setInterpolator(Interpolators.LINEAR)
            .alpha(0f)
            .withLayer()
    }

    fun View.animateInForHeader(delay: Long = 0) {
        animate().cancel()
        translationY = -height.toFloat()
        alpha = 1f
        animate()
            .setStartDelay(delay)
            .setDuration(470)
            .setInterpolator(Interpolators.EASE_OUT_EXPO)
            .translationY(0f)
            .withLayer()
    }

    fun View.animateOutForHeader() {
        animate().cancel()
        alpha = 1f
        animate()
            .setDuration(170)
            .setInterpolator(Interpolators.LINEAR)
            .alpha(0f)
            .withLayer()
    }

    fun View.animateInForCloseButton() {
        animate().cancel()
        translationX = (-width).toFloat()
        rotation = -90f
        animate()
            .translationX(0f)
            .rotation(0f)
            .setDuration(470)
            .setInterpolator(Interpolators.EASE_OUT_EXPO)
            .withLayer()
    }

    fun View.animateOutForCloseButton() {
        animate().cancel()
        translationX = 0f
        rotation = 0f
        animate()
            .translationX((-width).toFloat())
            .rotation(-90f)
            .setDuration(200)
            .setInterpolator(Interpolators.EASE_IN_EXPO)
            .withLayer()
    }
}
