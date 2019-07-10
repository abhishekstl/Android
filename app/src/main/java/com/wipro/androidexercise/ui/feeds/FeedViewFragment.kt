package com.wipro.androidexercise.ui.feeds


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.wipro.androidexercise.dependencyinjection.module.Injectable
import com.wipro.androidexercise.databinding.FragmentFeedViewBinding
import com.wipro.androidexercise.modelbuilder.model.FeedListRow
import com.wipro.androidexercise.viewmodel.feeds.FeedListViewModel
import javax.inject.Inject


class FeedViewFragment : Fragment(), Injectable, SwipeRefreshLayout.OnRefreshListener {


    lateinit var mBinding: FragmentFeedViewBinding

    lateinit var flVM: FeedListViewModel

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        mBinding = FragmentFeedViewBinding.inflate(inflater)
        mBinding.pullToRefresh.setOnRefreshListener(this)
        return mBinding.getRoot()
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        flVM = ViewModelProviders.of(this, viewModelFactory).get(FeedListViewModel::class.java)

        subscribeFeedsData();
        subscribeTitle();
        subscribeLoading();
        subscribeError();
    }


    private fun subscribeError() {
        flVM.showError.observe(this, Observer {
            if (it!!) {
                mBinding.recyclerViewFeedList.visibility = View.GONE
                mBinding.textViewError.visibility = View.VISIBLE
                setAdapterIfNull(mutableListOf<FeedListRow>())
            } else {
                mBinding.textViewError.visibility = View.GONE
            }
            mBinding.pullToRefresh.setRefreshing(false)
        })
    }

    private fun subscribeLoading() {
        flVM.showLoading.observe(this, Observer {
            if (it!!) {
                mBinding.progressBar.visibility = View.VISIBLE
                mBinding.textViewLoading.visibility = View.VISIBLE
            } else {
                mBinding.progressBar.visibility = View.GONE
                mBinding.textViewLoading.visibility = View.GONE
            }
        })
    }


    private fun subscribeTitle() {

        flVM.mToolBarTitle.observe(this, Observer {
            setActionBarTitle(it)
        })
    }

    private fun subscribeFeedsData() {
        flVM.feedsList.observe(this, Observer {

            if (mBinding.recyclerViewFeedList.visibility == View.GONE)
                mBinding.recyclerViewFeedList.visibility = View.VISIBLE


            val isAdapterNull = setAdapterIfNull(it?.toMutableList())

            if (!isAdapterNull) {
                (mBinding.recyclerViewFeedList.getAdapter() as FeedsAdapter?)?.refreshList(it)
            }

            mBinding.pullToRefresh.isRefreshing = false

        })
    }

    private fun setAdapterIfNull(feedsList: MutableList<FeedListRow>?): Boolean {

        if (mBinding.recyclerViewFeedList.adapter == null) {
            mBinding.recyclerViewFeedList.adapter =
                FeedsAdapter(mBinding.recyclerViewFeedList.context, feedsList)

            return true
        }

        return false

    }

    override fun onRefresh() {
        flVM.refresh()
    }

    private fun setActionBarTitle(title: String?) {

        if ((activity as AppCompatActivity).supportActionBar != null) {
            (activity as AppCompatActivity).supportActionBar?.title = title
        }
    }


}
