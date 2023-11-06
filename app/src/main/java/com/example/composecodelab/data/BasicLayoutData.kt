package com.example.composecodelab.data

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import com.example.composecodelab.R

data class BasicLayout(
    @DrawableRes val image: Int = 0,
    @StringRes val text: Int = 0
)

object BasicLayoutData {
    val data = arrayOf(
        BasicLayout(R.drawable.minji, R.string.inversion),
        BasicLayout(R.drawable.minji, R.string.inversion),
        BasicLayout(R.drawable.minji, R.string.inversion),
        BasicLayout(R.drawable.minji, R.string.inversion),
        BasicLayout(R.drawable.minji, R.string.inversion),
        BasicLayout(R.drawable.minji, R.string.inversion),
        BasicLayout(R.drawable.minji, R.string.inversion),
        BasicLayout(R.drawable.minji, R.string.inversion),
        BasicLayout(R.drawable.minji, R.string.inversion),
        BasicLayout(R.drawable.minji, R.string.inversion),
        BasicLayout(R.drawable.minji, R.string.inversion),
    )
}
