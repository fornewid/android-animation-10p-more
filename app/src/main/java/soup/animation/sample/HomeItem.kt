package soup.animation.sample

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.navigation.NavDirections

class HomeItem(
    @DrawableRes
    val iconResId: Int,
    @StringRes
    val textResId: Int,
    val direction: () -> NavDirections
)
