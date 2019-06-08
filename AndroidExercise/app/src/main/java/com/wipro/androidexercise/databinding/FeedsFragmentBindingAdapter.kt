package com.wipro.androidexercise.databinding

import android.databinding.BindingAdapter
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView

import com.wipro.androidexercise.modelbuilder.model.FeedListRow
import com.wipro.androidexercise.ui.feeds.FeedsAdapter

object FeedsFragmentBindingAdapter {


    @BindingAdapter("feedsList")
    @JvmStatic
    fun setFeedsList(view: RecyclerView, feeds: MutableList<FeedListRow>?) {

        val layoutManager = view.layoutManager

        if (layoutManager == null) {
            view.layoutManager = LinearLayoutManager(view.context, LinearLayoutManager.VERTICAL, false)
        }

        if (view.adapter as FeedsAdapter? == null) {
            if (feeds == null) {
                return;
            }
            else{
                view.adapter = FeedsAdapter(view.context, feeds)
            }
        }

    }
}
