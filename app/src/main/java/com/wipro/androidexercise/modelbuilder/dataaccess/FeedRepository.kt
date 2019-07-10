package com.wipro.androidexercise.modelbuilder.dataaccess


import com.wipro.androidexercise.modelbuilder.model.FeedListData
import com.wipro.androidexercise.modelbuilder.services.FeedAPIInterface
import com.wipro.androidexercise.ui.feeds.FeedResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import javax.inject.Inject


class FeedRepository @Inject constructor(
    private val apiInterface: FeedAPIInterface){

    //private val LOG_TAG = "FeedRepository"

    fun getFeeds(fResponse: FeedResponse)  {

        val feedsCall = apiInterface.feeds
        feedsCall.enqueue(object : Callback<FeedListData> {
            override fun onResponse(call: Call<FeedListData>, response: Response<FeedListData>) {

                if(response.raw().networkResponse() == null) {
                    if(!response.isSuccessful){
                        fResponse.onFailure()
                        return
                    }
                   // Log.d(LOG_TAG, "Cached Response is returned")
                }
                //else {
                  //  Log.d(LOG_TAG, "Network Response is returned")
                //}

                fResponse.onSuccess(response.body())
            }

            override fun onFailure(call: Call<FeedListData>, t: Throwable) {
                fResponse.onFailure()
            }
        })
    }
}