package com.wipro.androidexercise.modelbuilder.model

import android.os.Parcelable
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class FeedListRow(
    @SerializedName("title")
    @Expose
    val title: String?,
    @SerializedName("imageHref")
    @Expose
    val imageHref: String?,
    @SerializedName("description")
    @Expose
    val description: String?
):Parcelable