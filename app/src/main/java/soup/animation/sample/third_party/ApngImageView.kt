package soup.animation.sample.third_party

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.drawable.Drawable
import android.util.AttributeSet
import android.view.View
import android.widget.ImageView
import androidx.annotation.RawRes
import com.linecorp.apng.ApngDrawable
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import soup.animation.sample.R
import timber.log.Timber

class ApngImageView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyle: Int = 0
) : ImageView(context, attrs, defStyle) {

    private var anim: ApngDrawable? = null
    private var attached: Boolean = false
    private var autoPlay = true
    private var loop = false

    init {
        val a = context.theme.obtainStyledAttributes(attrs, R.styleable.ApngImageView, 0, 0)
        try {
            autoPlay = a.getBoolean(R.styleable.ApngImageView_apng_autoPlay, true)
            loop = a.getBoolean(R.styleable.ApngImageView_apng_loop, false)

            val rawResId = a.getResourceId(R.styleable.ApngImageView_apng_rawRes, 0)
            if (rawResId != 0) {
                setRawResource(rawResId)
            }
        } finally {
            a.recycle()
        }
    }

    @SuppressLint("CheckResult")
    fun setRawResource(@RawRes id: Int) {
        GlobalScope.launch(Dispatchers.Default) {
            val apngDrawable: ApngDrawable = try {
                ApngDrawable.decode(context.resources, id).apply {
                    loopCount = if (loop) ApngDrawable.LOOP_FOREVER else 1
                    setTargetDensity(resources.displayMetrics)
                }
            } catch (e: Exception) {
                Timber.w(e, "setRawResource: ApngDrawable.decode failed..")
                return@launch
            }
            GlobalScope.launch(Dispatchers.Main) {
                setImageDrawable(apngDrawable)
            }
        }
    }

    fun setAutoPlay(autoPlay: Boolean) {
        if (this.autoPlay != autoPlay) {
            this.autoPlay = autoPlay
            updateAnim()
            if (!autoPlay && anim != null) {
                // Reset drawable such that we show the first frame whenever we're not animating.
                anim?.setVisible(visibility == View.VISIBLE, true /* restart */)
            }
        }
    }

    fun setLoop(loop: Boolean) {
        this.loop = loop
    }

    override fun setImageDrawable(drawable: Drawable?) {
        super.setImageDrawable(drawable)
        updateAnim()
    }

    override fun setImageResource(resid: Int) {
        super.setImageResource(resid)
        updateAnim()
    }

    private fun updateAnim() {
        if (attached) {
            anim?.stop()
        }
        val drawable = drawable
        if (drawable is ApngDrawable) {
            anim = drawable
            if (isShown && autoPlay) {
                drawable.start()
            }
        } else {
            anim = null
        }
    }

    public override fun onAttachedToWindow() {
        super.onAttachedToWindow()
        attached = true
        updateAnim()
    }

    public override fun onDetachedFromWindow() {
        super.onDetachedFromWindow()
        anim?.stop()
        attached = false
    }

    override fun onVisibilityChanged(changedView: View, vis: Int) {
        super.onVisibilityChanged(changedView, vis)
        if (isShown && autoPlay) {
            anim?.start()
        } else {
            anim?.stop()
        }
    }
}
