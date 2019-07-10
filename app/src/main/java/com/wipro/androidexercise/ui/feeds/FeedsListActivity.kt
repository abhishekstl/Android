package com.wipro.androidexercise.ui.feeds


import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import com.wipro.androidexercise.R
import com.wipro.androidexercise.databinding.ActivityFeedsListBinding
import dagger.android.DispatchingAndroidInjector
import dagger.android.support.HasSupportFragmentInjector
import javax.inject.Inject

class FeedsListActivity : AppCompatActivity(), HasSupportFragmentInjector {

    @Inject
    lateinit var dispatchingAndroidInjector: DispatchingAndroidInjector<Fragment>

    override fun supportFragmentInjector() = dispatchingAndroidInjector

    var feedViewFragment : FeedViewFragment? = null

    var mBinding: ActivityFeedsListBinding? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_feeds_list)
        init(savedInstanceState)
    }

    private fun init(savedInstanceState: Bundle?) {

        if(savedInstanceState == null){
            feedViewFragment = FeedViewFragment()
            val transaction = supportFragmentManager.beginTransaction()
            transaction.replace(R.id.main_container, feedViewFragment!!, getString(R.string.fragment_view_feed))
            transaction.commit()
        }

    }

}