package com.wipro.androidexercise.modelbuilder.model

import android.os.Parcelable
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class FeedListData(
    @SerializedName("title")
    @Expose
    val title: String?,
    @SerializedName("rows")
    @Expose
    var rows: List<FeedListRow>?
): Parcelable