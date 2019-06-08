package com.wipro.androidexercise.ui.feeds

import android.databinding.BaseObservable
import android.databinding.Bindable
import com.wipro.androidexercise.BR
import com.wipro.androidexercise.modelbuilder.model.FeedListRow


class FeedViewModel : BaseObservable() {

    @get:Bindable
    var feed: FeedListRow? = null
        set(feed) {
            field = feed
            notifyPropertyChanged(BR.feed)
        }


}
