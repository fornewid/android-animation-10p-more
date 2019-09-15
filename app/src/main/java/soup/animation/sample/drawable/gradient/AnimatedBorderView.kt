package soup.animation.sample.drawable.gradient

import android.content.Context
import android.graphics.*
import android.os.Build
import android.util.AttributeSet
import soup.animation.sample.R

class AnimatedBorderView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyle: Int = 0
) : AnimatedGradientView(context, attrs, defStyle) {

    private var ribbonGap: Float = 0f

    init {
        if (attrs != null) {
            val a = context.obtainStyledAttributes(attrs, R.styleable.AnimatedBorderView, defStyle, 0)
            try {
                ribbonGap = a.getDimension(R.styleable.AnimatedBorderView_borderWidth, 0f)
            } finally {
                a.recycle()
            }
        }
    }

    override fun draw(canvas: Canvas?) {
        canvas?.clip(ribbonGap)
        super.draw(canvas)
    }

    private fun Canvas.clip(gap: Float) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            gap.toInt().let {
                clipOutRect(Rect(it, it, width - it, height - it))
            }
        } else {
            clipPath(
                Path().apply {
                    addRect(
                        RectF(gap, gap, width - gap, height - gap),
                        Path.Direction.CW
                    )
                },
                Region.Op.DIFFERENCE
            )
        }
    }
}
