package com.shareyacht.shareyacht.utils

import androidx.databinding.BindingAdapter
import com.google.android.material.button.MaterialButtonToggleGroup

object DataBindingUtil {
    @BindingAdapter("checkedBtnAttrChanged")
    @JvmStatic
    fun setToggleGroupChangedListener(
        toggleGroup: MaterialButtonToggleGroup,
        listener: MaterialButtonToggleGroup.OnButtonCheckedListener
    ) {
        toggleGroup.addOnButtonCheckedListener(listener)
    }
}