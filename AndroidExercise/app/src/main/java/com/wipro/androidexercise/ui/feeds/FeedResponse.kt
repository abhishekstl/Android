package com.wipro.androidexercise.ui.feeds

import com.wipro.androidexercise.modelbuilder.model.FeedListData

interface FeedResponse {

    fun onSuccess(feedListData: FeedListData?)

    fun onFailure()

}
