/*
 * Copyright (C) 2008 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package soup.animation.sample.drawable

import android.content.Context
import android.graphics.drawable.AnimationDrawable
import android.graphics.drawable.Drawable
import android.util.AttributeSet
import android.view.View
import androidx.appcompat.widget.AppCompatImageView

open class AnimatedImageView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null
) : AppCompatImageView(context, attrs) {

    private var anim: AnimationDrawable? = null
    private var isAttached: Boolean = false
    private var allowAnimation = true

    // Tracks the last image that was set, so that we don't refresh the image if it is exactly
    // the same as the previous one. If this is a resid, we track that. If it's a drawable, we
    // track the hashcode of the drawable.
    private var drawableId: Int = 0

    fun setAllowAnimation(allowAnimation: Boolean) {
        if (this.allowAnimation != allowAnimation) {
            this.allowAnimation = allowAnimation
            updateAnim()
            if (this.allowAnimation.not()) {
                // Reset drawable such that we show the first frame whenever we're not animating.
                anim?.setVisible(visibility == View.VISIBLE, true /* restart */)
            }
        }
    }

    private fun updateAnim() {
        val drawable = drawable
        if (isAttached) {
            anim?.stop()
        }
        if (drawable is AnimationDrawable) {
            setAnimationDrawable(drawable)
            if (isShown && allowAnimation) {
                drawable.start()
            }
        } else {
            setAnimationDrawable(null)
        }
    }

    protected open fun setAnimationDrawable(drawable: AnimationDrawable?) {
        anim = drawable
    }

    override fun setImageDrawable(drawable: Drawable?) {
        if (drawable != null) {
            if (drawableId == drawable.hashCode()) return
            drawableId = drawable.hashCode()
        } else {
            drawableId = 0
        }
        super.setImageDrawable(drawable)
        updateAnim()
    }

    override fun setImageResource(resid: Int) {
        if (drawableId == resid) return
        drawableId = resid
        super.setImageResource(resid)
        updateAnim()
    }

    public override fun onAttachedToWindow() {
        super.onAttachedToWindow()
        isAttached = true
        updateAnim()
    }

    public override fun onDetachedFromWindow() {
        super.onDetachedFromWindow()
        if (anim != null) {
            anim!!.stop()
        }
        isAttached = false
    }

    override fun onVisibilityChanged(changedView: View, vis: Int) {
        super.onVisibilityChanged(changedView, vis)
        if (isShown && allowAnimation) {
            anim?.start()
        } else {
            anim?.stop()
        }
    }
}
