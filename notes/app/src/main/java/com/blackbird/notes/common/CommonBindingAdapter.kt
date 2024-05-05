package com.blackbird.notes.common

import android.view.View
import androidx.core.view.isVisible
import androidx.databinding.BindingAdapter


@BindingAdapter("android:isVisible")
fun View.IsVisible(isVisble:Boolean){
    this.isVisible = isVisble
}