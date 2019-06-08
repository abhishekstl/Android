package com.wipro.androidexercise.ui.feeds

import android.app.Application
import android.arch.lifecycle.AndroidViewModel
import android.arch.lifecycle.MutableLiveData
import com.wipro.androidexercise.modelbuilder.model.FeedListData
import com.wipro.androidexercise.modelbuilder.model.FeedListRow
import javax.inject.Inject

class FeedListViewModel @Inject constructor(
    feedRepository: FeedRepository): AndroidViewModel(Application()) {

    private val mfeedRepository: FeedRepository = feedRepository
    var feedsList = MutableLiveData<List<FeedListRow>>()
    var mToolBarTitle = MutableLiveData<String?>()
    var showLoading = MutableLiveData<Boolean>()
    var showError = MutableLiveData<Boolean>()

    init {
        showLoading.value = true
        showError.value = false
        getFeeds()
    }

    private fun getFeeds(){
         mfeedRepository.getFeeds(object : FeedResponse{
            override fun onSuccess(feedListData: FeedListData?) {
                feedsList.value =feedListData?.rows
                mToolBarTitle.value=feedListData?.title
                showLoading.value = false
                showError.value = false
            }
            override fun onFailure() {
                showLoading.value = false
                showError.value = true
            }
        });
    }

     fun refresh() {
        getFeeds()
    }


}
