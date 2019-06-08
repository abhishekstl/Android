package com.wipro.androidexercise.ui.feeds

import android.databinding.DataBindingUtil
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.app.AppCompatActivity
import com.wipro.androidexercise.R
import com.wipro.androidexercise.databinding.ActivityFeedsListBinding
import dagger.android.DispatchingAndroidInjector
import dagger.android.support.HasSupportFragmentInjector
import javax.inject.Inject

class FeedsListActivity : AppCompatActivity(), HasSupportFragmentInjector {

    @Inject
    lateinit var dispatchingAndroidInjector: DispatchingAndroidInjector<Fragment>

    override fun supportFragmentInjector() = dispatchingAndroidInjector


    var mBinding: ActivityFeedsListBinding? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_feeds_list)
        init()
    }

    private fun init() {
        val fragment = FeedViewFragment()
        val transaction = supportFragmentManager.beginTransaction()
        transaction.replace(R.id.main_container, fragment, getString(R.string.fragment_view_feed))
        transaction.commit()
    }

}