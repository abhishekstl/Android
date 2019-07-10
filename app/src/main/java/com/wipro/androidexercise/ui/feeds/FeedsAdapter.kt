package com.wipro.androidexercise.ui.feeds

import android.content.Context
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.wipro.androidexercise.BR
import com.wipro.androidexercise.R
import com.wipro.androidexercise.modelbuilder.model.FeedListRow
import com.wipro.androidexercise.viewmodel.feeds.FeedViewModel


class FeedsAdapter(private val vContext: Context, feedsList: MutableList<FeedListRow>?) : RecyclerView.Adapter<FeedsAdapter.FeedsBindingViewHolder>() {

    private var mFeedsList : MutableList<FeedListRow>? = mutableListOf()
    private var mContext: Context;

    init {
        mFeedsList = feedsList
        mContext = vContext
    }

    fun refreshList(feedsList: List<FeedListRow>?) {

        mFeedsList?.clear()
        if(feedsList!=null) {
            mFeedsList?.addAll(feedsList)
        }

        notifyDataSetChanged()


    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FeedsBindingViewHolder {

        val binding = DataBindingUtil.inflate<ViewDataBinding>(LayoutInflater.from(mContext), R.layout.feed_item, parent, false)
        return FeedsBindingViewHolder(binding.root)
    }

    override fun onBindViewHolder(holder: FeedsBindingViewHolder, position: Int) {
        if(mFeedsList !=null){
            val currentFeedObj = mFeedsList?.get(position)
            val feedView = FeedViewModel()
            feedView.feed = currentFeedObj
            holder.binding!!.setVariable(BR.feedView, feedView)
            holder.binding!!.executePendingBindings()
        }

    }

    override fun getItemCount(): Int {
        return mFeedsList?.size?:0
    }


    inner class FeedsBindingViewHolder(feedView: View) : RecyclerView.ViewHolder(feedView) {

        var binding: ViewDataBinding? = null

        init {
            binding = DataBindingUtil.bind(feedView)
        }

    }

}
