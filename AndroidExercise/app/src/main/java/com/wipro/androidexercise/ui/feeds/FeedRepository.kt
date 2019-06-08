package com.wipro.androidexercise.ui.feeds

import com.wipro.androidexercise.modelbuilder.model.FeedListData
import com.wipro.androidexercise.modelbuilder.services.FeedAPIInterface
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import javax.inject.Inject

class FeedRepository @Inject constructor(
    private val apiInterface: FeedAPIInterface){

    fun getFeeds(fResponse: FeedResponse)  {

        apiInterface.feeds.enqueue(object : Callback<FeedListData> {
            override fun onResponse(call: Call<FeedListData>, response: Response<FeedListData>) {
                fResponse.onSuccess(response.body())
            }

            override fun onFailure(call: Call<FeedListData>, t: Throwable) {
                fResponse.onFailure()
            }
        })
    }
}