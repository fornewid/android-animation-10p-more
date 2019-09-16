package soup.animation.sample.drawable.gradient

import android.content.Context
import android.graphics.Canvas
import android.graphics.Rect
import android.graphics.Region
import android.os.Build
import android.util.AttributeSet
import soup.animation.sample.R

class AnimatedBorderView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyle: Int = 0
) : AnimatedGradientView(context, attrs, defStyle) {

    private var borderWidth: Int = 0

    init {
        if (attrs != null) {
            val a = context.obtainStyledAttributes(attrs, R.styleable.AnimatedBorderView, defStyle, 0)
            try {
                borderWidth = a.getDimensionPixelSize(R.styleable.AnimatedBorderView_borderWidth, 0)
            } finally {
                a.recycle()
            }
        }
    }

    override fun draw(canvas: Canvas) {
        canvas.clipBorder(borderWidth)
        super.draw(canvas)
    }

    private fun Canvas.clipBorder(gap: Int) {
        val rect = Rect(gap, gap, width - gap, height - gap)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            clipOutRect(rect)
        } else {
            clipRect(rect, Region.Op.DIFFERENCE)
        }
    }
}
