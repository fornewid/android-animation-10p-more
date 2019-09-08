package soup.animation.sample.animator.reveal

import android.animation.Animator
import android.app.Dialog
import android.os.Bundle
import android.view.KeyEvent
import android.view.View
import android.view.ViewAnimationUtils
import androidx.appcompat.app.AppCompatDialogFragment
import androidx.core.animation.doOnEnd
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import kotlin.math.hypot

class AnimatorRevealDialogFragment : AppCompatDialogFragment() {

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return MaterialAlertDialogBuilder(context)
            .setTitle("Title")
            .setMessage("Message")
            .setPositiveButton("Confirm", null)
            .setNegativeButton("Cancel", null)
            .create()
            .apply {
                setOnShowListener {
                    showRevealAnimation()
                }
                setOnKeyListener { _, keyCode, _ ->
                    if (keyCode == KeyEvent.KEYCODE_BACK) {
                        hideRevealAnimationAndDismiss()
                        true
                    } else {
                        false
                    }
                }
            }
    }

    private fun showRevealAnimation() {
        dialog?.window?.decorView?.let { view ->
            view.createCircularRevealOf(view, 0f, view.diagonalLength()) {
                duration = 500L
            }.start()
        }
    }

    private fun hideRevealAnimationAndDismiss() {
        dialog?.window?.decorView?.let { view ->
            view.createCircularRevealOf(view, view.diagonalLength(), 0f) {
                duration = 500L
                doOnEnd {
                    dismiss()
                }
            }.start()
        }
    }

    private inline fun View.createCircularRevealOf(
        target: View,
        startRadius: Float,
        endRadius: Float,
        block: Animator.() -> Unit
    ): Animator {
        return ViewAnimationUtils.createCircularReveal(
            this,
            target.centerX(),
            target.centerY(),
            startRadius,
            endRadius
        ).apply(block)
    }

    private fun View.centerX(): Int {
        return (right + left) / 2
    }

    private fun View.centerY(): Int {
        return (bottom + top) / 2
    }

    private fun View.diagonalLength(): Float {
        return hypot(width.toFloat(), height.toFloat())
    }
}
