package com.wipro.androidexercise.ui.splash

import android.content.Context
import android.net.ConnectivityManager
import android.util.Log
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.AppCompatTextView
import androidx.test.core.app.ActivityScenario
import androidx.test.espresso.*
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.filters.LargeTest
import com.wipro.androidexercise.R
import com.wipro.androidexercise.ui.feeds.FeedsListActivity
import junit.framework.AssertionFailedError
import org.hamcrest.Description
import org.hamcrest.Matcher
import org.hamcrest.Matchers
import org.hamcrest.TypeSafeMatcher
import org.junit.Before
import org.junit.Rule
import org.junit.Test


@LargeTest
class FeedListActivityTest {

    private fun isConnected(context: Context): Boolean {
        val connectivityManager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val activeNetworkInfo = connectivityManager.activeNetworkInfo
        return activeNetworkInfo != null && activeNetworkInfo.isConnected
    }

    private var isErrorTextDisplayed = false

    @Rule
    @JvmField
    var activityScenarioRule = ActivityScenarioRule<FeedsListActivity>(FeedsListActivity::class.java)

    @Before
    fun waitForLaunch(){

        activityScenarioRule.scenario.onActivity {
            if(isConnected(it)){
                Log.d("network", "connected")
            } else{
                Log.d("network", "disconnected")
                Thread.sleep(2000)
            }
        }

        ActivityScenario.launch(FeedsListActivity::class.java)



        if(!checkIfErrorTextIsDisplayed()) {
            waitForProgressBarToEnd()
            checkIfErrorTextIsDisplayed()
        }

    }

    private fun checkIfErrorTextIsDisplayed(): Boolean {
        try {
            this.isErrorTextDisplayed = onView(withText(R.string.error_data_msg)).isDisplayed()
            // View is in hierarchy

        } catch(e: AssertionFailedError) {
            this.isErrorTextDisplayed = false

        }
        return this.isErrorTextDisplayed
    }

    private fun waitForProgressBarToEnd() : Boolean{
       var isProgressBarDisplayed = try {
            onView(withId(R.id.progressBar)).isDisplayed()
        } catch(e: AssertionFailedError) {
            false
        }

        if(isProgressBarDisplayed){
            var i = 0
            while(i <= 200)
            {
                try {
                    isProgressBarDisplayed = onView(withId(R.id.progressBar)).isDisplayed()
                }
                catch(e: AssertionFailedError) {
                    isProgressBarDisplayed = false
                    break
                }

                Thread.sleep(100L)
                i++
            }
        }
        return isProgressBarDisplayed
    }

    @Test
    fun errorContentVerification(){
        if(isErrorTextDisplayed){
            val textView = onView(
                Matchers.allOf(
                    withId(R.id.textViewError),
                    withText(Matchers.endsWith("Swipe down to refresh")),
                    isDisplayed()
                )
            )
            textView.run {  }
            textView.check(matches(withText(Matchers.endsWith("Swipe down to refresh"))))
        }
    }

    private fun ViewInteraction.isDisplayed(): Boolean {
        return try {
            check(matches(ViewMatchers.isDisplayed()))
            true
        } catch (e: NoMatchingViewException) {
            false
        }
    }


    @Test
    fun scrollTest() {
        if(!isErrorTextDisplayed) {
            Espresso.onView(withId(R.id.recyclerViewFeedList))
                .perform(ViewActions.swipeUp())
        }
    }

    @Test
    fun verifyActionBarText(){
        if(!isErrorTextDisplayed) {
            onView(
                Matchers.allOf(
                    isAssignableFrom(AppCompatTextView::class.java),
                    withParent(withId(R.id.action_bar))
                )
            ).check(matches(withText("About Canada")))
        }
    }



    @Test
    fun swipeLayoutTest() {
        onView(withId(R.id.pullToRefresh))
            .perform(withCustomConstraints(ViewActions.swipeDown(), isDisplayingAtLeast(5)))



    }


    @Test
    fun splashActivityTest() {
        if(!isErrorTextDisplayed) {
            val textView = onView(
                Matchers.allOf(
                    withId(R.id.textViewDescription),
                    withText("Beavers are second only to humans in their ability to manipulate and change their environment. They can measure up to 1.3 metres long. A group of beavers is called a colony"),
                    childAtPosition(
                        childAtPosition(
                            withId(R.id.cardView),
                            0
                        ),
                        1
                    ),
                    isDisplayed()
                )
            )
            textView.check(matches(withText("Beavers are second only to humans in their ability to manipulate and change their environment. They can measure up to 1.3 metres long. A group of beavers is called a colony")))
        }
    }



    private fun childAtPosition(
        parentMatcher: Matcher<View>, position: Int
    ): Matcher<View> {

        return object : TypeSafeMatcher<View>() {
            override fun describeTo(description: Description) {
                description.appendText("Child at position $position in parent ")
                parentMatcher.describeTo(description)
            }

            public override fun matchesSafely(view: View): Boolean {
                val parent = view.parent
                return parent is ViewGroup && parentMatcher.matches(parent)
                        && view == parent.getChildAt(position)
            }
        }
    }

    private fun withCustomConstraints(action: ViewAction, constraints: Matcher<View>): ViewAction {
        return object : ViewAction {
            override fun getConstraints(): Matcher<View> {
                return constraints
            }

            override fun getDescription(): String {
                return action.description
            }

            override fun perform(uiController: UiController, view: View) {
                action.perform(uiController, view)
            }
        }
    }


}
