package soup.animation.sample.util

fun lerp(from: Int, to: Int, progress: Float): Float {
    return from + (to - from) * progress
}

fun lerp(from: Float, to: Float, progress: Float): Float {
    return from + (to - from) * progress
}
