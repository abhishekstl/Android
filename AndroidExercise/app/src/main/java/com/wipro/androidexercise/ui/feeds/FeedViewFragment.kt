package com.wipro.androidexercise.ui.feeds


import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProvider
import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.widget.SwipeRefreshLayout
import android.support.v7.app.AppCompatActivity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.wipro.androidexercise.dependencyinjection.module.Injectable
import com.wipro.androidexercise.databinding.FragmentFeedViewBinding
import com.wipro.androidexercise.modelbuilder.model.FeedListRow
import javax.inject.Inject


class FeedViewFragment : Fragment(), Injectable, SwipeRefreshLayout.OnRefreshListener {

    lateinit var mBinding: FragmentFeedViewBinding

    lateinit var flVM: FeedListViewModel

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    override fun onCreateView(inflater: LayoutInflater,
                              container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {

        flVM = ViewModelProviders.of(this, viewModelFactory)
            .get(FeedListViewModel::class.java)



        subscribeFeedsData();
        subscribeTitle();
        subscribeLoading();
        subscribeError();


        mBinding = FragmentFeedViewBinding.inflate(inflater)

        mBinding.pullToRefresh.setOnRefreshListener(this)

        return mBinding.getRoot()
    }


    private fun subscribeError() {
        flVM.showError.observe(this, Observer{
            if(it!!) {
                mBinding.textViewError.visibility = View.VISIBLE
                setAdapterIfNull(mutableListOf<FeedListRow>())
                val message =  "Unable to fetch data, please check if you are connected to internet"
                Toast.makeText(activity as AppCompatActivity,message,Toast.LENGTH_LONG).show()

            }else{
                mBinding.textViewError.visibility = View.GONE
            }
            mBinding.pullToRefresh.setRefreshing(false)
        })
    }

    private fun subscribeLoading() {
        flVM.showLoading.observe(this, Observer{
            if(it!!) {
                mBinding.progressBar.visibility = View.VISIBLE
                mBinding.textViewLoading.visibility =  View.VISIBLE
            }else{
                mBinding.progressBar.visibility = View.GONE
                mBinding.textViewLoading.visibility =  View.GONE
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

            val isAdapterNull = setAdapterIfNull(it?.toMutableList())

            if(!isAdapterNull){
                (mBinding.recyclerViewFeedList.getAdapter() as FeedsAdapter?)?.refreshList(it)
            }

            mBinding.pullToRefresh.isRefreshing = false

        })
    }

    private fun setAdapterIfNull(feedsList: MutableList<FeedListRow>?) : Boolean{

        if( mBinding.recyclerViewFeedList.adapter == null) {
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
