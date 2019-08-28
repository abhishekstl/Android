package com.wipro.androidexercise.databinding



import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.LinearLayoutManager

import com.wipro.androidexercise.modelbuilder.model.FeedListRow
import com.wipro.androidexercise.ui.feeds.FeedsAdapter
import androidx.recyclerview.widget.RecyclerView as RecyclerView1

object FeedsFragmentBindingAdapter {
    @BindingAdapter("feedsList")
    @JvmStatic
    fun setFeedsList(view: RecyclerView1, feeds: MutableList<FeedListRow>?) {

        val layoutManager = view.layoutManager

        if (layoutManager == null) {
            view.layoutManager = LinearLayoutManager(view.context, RecyclerView1.VERTICAL, false)
        }

        if (view.adapter as FeedsAdapter? == null) {
            if (feeds == null) return
            else{
                view.adapter = FeedsAdapter(view.context, feeds)
            }
        }

    }
}
