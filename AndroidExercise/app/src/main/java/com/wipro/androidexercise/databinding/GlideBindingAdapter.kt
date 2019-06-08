package com.wipro.androidexercise.databinding


import android.databinding.BindingAdapter
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.wipro.androidexercise.R

object GlideBindingAdapter {


    @BindingAdapter("imageUrl")
    @JvmStatic
    fun setImage(view: ImageView, imageUrl: String) {


        val options = RequestOptions()
                .placeholder(R.drawable.default_image)
                .error(R.drawable.default_image)

        Glide.with(view.context)
                .setDefaultRequestOptions(options)
                .load(imageUrl)
                .into(view)
    }

}
