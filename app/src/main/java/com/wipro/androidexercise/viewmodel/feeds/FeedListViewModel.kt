package com.wipro.androidexercise.viewmodel.feeds

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.wipro.androidexercise.modelbuilder.model.FeedListData
import com.wipro.androidexercise.modelbuilder.model.FeedListRow
import com.wipro.androidexercise.modelbuilder.dataaccess.FeedRepository
import com.wipro.androidexercise.ui.feeds.FeedResponse
import javax.inject.Inject

class FeedListViewModel @Inject constructor(
    feedRepository: FeedRepository
): AndroidViewModel(Application()) {

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

    fun getFeeds(){
         mfeedRepository.getFeeds(object : FeedResponse {
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
