package soup.animation.sample.custom

import android.content.Context
import android.os.Parcel
import android.os.Parcelable
import android.util.AttributeSet
import android.view.View
import android.view.ViewParent
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.view.children
import com.google.android.material.appbar.AppBarLayout
import soup.animation.sample.R

class CollapsingConstraintLayout @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : ConstraintLayout(context, attrs, defStyleAttr), AppBarLayout.OnOffsetChangedListener {

    private var appBarLayoutId: Int = NO_ID

    private var progress: Float = 0f

    init {
        val a = context.obtainStyledAttributes(attrs, R.styleable.CollapsingConstraintLayout, defStyleAttr, 0)
        try {
            appBarLayoutId = a.getResourceId(R.styleable.CollapsingConstraintLayout_appBarLayoutId, NO_ID)
        } finally {
            a.recycle()
        }
    }

    private fun ViewParent.findAppBarLayout(): AppBarLayout? {
        if (appBarLayoutId != NO_ID) {
            if (parent is View) {
                return (parent as View).findViewById(appBarLayoutId)
            }
        }
        return parent as? AppBarLayout ?: parent?.findAppBarLayout()
    }

    override fun onAttachedToWindow() {
        super.onAttachedToWindow()
        findAppBarLayout()?.addOnOffsetChangedListener(this)
    }

    override fun onDetachedFromWindow() {
        findAppBarLayout()?.removeOnOffsetChangedListener(this)
        super.onDetachedFromWindow()
    }

    override fun onOffsetChanged(appBarLayout: AppBarLayout, verticalOffset: Int) {
        setProgress(-verticalOffset / appBarLayout.totalScrollRange.toFloat())
    }

    private fun setProgress(progress: Float) {
        val newValue = progress.coerceIn(0f, 1f)
        if (this.progress != newValue) {
            this.progress = newValue

            children.forEach {
                if (it is ProgressHelper) {
                    it.setProgress(newValue)
                }
            }
        }
    }

    /* Save and restore progress */

    override fun onSaveInstanceState(): Parcelable? {
        return SavedState(super.onSaveInstanceState())
            .also {
            it.progress = progress
        }
    }

    override fun onRestoreInstanceState(state: Parcelable?) {
        if (state is SavedState) {
            super.onRestoreInstanceState(state.superState)
            setProgress(state.progress)
        } else {
            super.onRestoreInstanceState(state)
        }
    }

    private class SavedState : BaseSavedState {

        var progress: Float = 0f

        constructor(superState: Parcelable?) : super(superState)

        private constructor(parcel: Parcel) : super(parcel) {
            progress = parcel.readFloat()
        }

        override fun writeToParcel(out: Parcel, flags: Int) {
            super.writeToParcel(out, flags)
            out.writeFloat(progress)
        }

        companion object {

            @JvmField
            val CREATOR = object : Parcelable.Creator<SavedState> {
                override fun createFromParcel(parcel: Parcel): SavedState {
                    return SavedState(parcel)
                }

                override fun newArray(size: Int): Array<SavedState?> {
                    return arrayOfNulls(size)
                }
            }
        }
    }
}
