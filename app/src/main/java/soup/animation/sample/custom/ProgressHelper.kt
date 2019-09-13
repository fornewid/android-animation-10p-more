package soup.animation.sample.custom

import androidx.annotation.FloatRange

interface ProgressHelper {

    fun setProgress(@FloatRange(from = 0.0, to = 1.0) progress: Float)

    @FloatRange(from = 0.0, to = 1.0)
    fun getProgress(): Float
}
