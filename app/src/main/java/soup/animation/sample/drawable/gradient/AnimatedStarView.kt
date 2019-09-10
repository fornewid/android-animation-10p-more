package soup.animation.sample.drawable.gradient

import android.content.Context
import android.graphics.Canvas
import android.graphics.Matrix
import android.graphics.Path
import android.graphics.Region
import android.os.Build
import android.util.AttributeSet
import androidx.core.graphics.PathParser
import soup.animation.sample.R
import kotlin.math.min

class AnimatedStarView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyle: Int = 0
) : AnimatedGradientView(context, attrs, defStyle) {

    private val clipPath = PathParser.createPathFromPathData(PATH_IC_STAR)
    private val clipOuter: Boolean

    init {
        val a = context.obtainStyledAttributes(attrs, R.styleable.AnimatedStarView, 0, 0)
        clipOuter = a.getBoolean(R.styleable.AnimatedStarView_clipOuter, false)
        a.recycle()
    }

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)
        clipPath.scale(min(w, h) / 24f)
    }

    private fun Path.scale(scale: Float) {
        transform(Matrix().apply {
            setScale(scale, scale)
        })
    }

    override fun draw(canvas: Canvas) {
        if (clipOuter) {
            canvas.clipOuter(clipPath)
        } else {
            canvas.clipInner(clipPath)
        }
        super.draw(canvas)
    }

    private fun Canvas.clipOuter(path: Path) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            clipOutPath(path)
        } else {
            clipPath(path, Region.Op.DIFFERENCE)
        }
    }

    private fun Canvas.clipInner(path: Path) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            clipPath(path)
        } else {
            clipPath(path, Region.Op.INTERSECT)
        }
    }

    companion object {

        private const val PATH_IC_STAR =
            "M12,17.27l4.15,2.51c0.76,0.46 1.69,-0.22 1.49,-1.08l-1.1,-4.72l3.67,-3.18c0.67,-0.58 0.31,-1.68 -0.57,-1.75l-4.83,-0.41l-1.89,-4.46c-0.34,-0.81 -1.5,-0.81 -1.84,0L9.19,8.63L4.36,9.04c-0.88,0.07 -1.24,1.17 -0.57,1.75l3.67,3.18l-1.1,4.72c-0.2,0.86 0.73,1.54 1.49,1.08L12,17.27z"
    }
}
