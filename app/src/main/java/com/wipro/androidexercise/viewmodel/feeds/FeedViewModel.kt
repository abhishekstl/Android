package com.wipro.androidexercise.viewmodel.feeds

import androidx.databinding.BaseObservable
import androidx.databinding.Bindable
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
