package com.wipro.androidexercise.modelbuilder.services

import retrofit2.Call
import retrofit2.http.GET

import com.wipro.androidex.modelbuilder.services.ServiceUrls.FEEDS_SUB_URL
import com.wipro.androidexercise.modelbuilder.model.FeedListData

interface FeedAPIInterface {

    @get:GET(FEEDS_SUB_URL)
    var feeds: Call<FeedListData>
}
