package com.vaibhav.moviedetails.view.bindingAdapters

import android.databinding.BindingAdapter
import android.widget.ImageView
import com.bumptech.glide.Glide

/**
 * @author Vaibhav Bhandula on 10/04/19.
 */
@BindingAdapter("imageUrl")
fun ImageView.setImageUrl(url: String?) {
    Glide.with(context).load(url).into(this)
}
