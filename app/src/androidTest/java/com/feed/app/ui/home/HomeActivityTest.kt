package com.feed.app.ui.home

import android.content.Context
import android.net.ConnectivityManager
import android.support.test.espresso.Espresso.onView
import android.support.test.espresso.assertion.ViewAssertions.matches
import android.support.test.espresso.matcher.ViewMatchers.isDisplayed
import android.support.test.espresso.matcher.ViewMatchers.withId
import android.support.test.filters.LargeTest
import android.support.test.rule.ActivityTestRule
import android.support.test.runner.AndroidJUnit4
import android.view.View
import android.view.ViewGroup
import com.feed.app.R
import org.hamcrest.Description
import org.hamcrest.Matcher
import org.hamcrest.Matchers.allOf
import org.hamcrest.TypeSafeMatcher
import org.junit.Assert
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@LargeTest
@RunWith(AndroidJUnit4::class)
class HomeActivityTest {

  @Rule
  @JvmField
  var mActivityTestRule = ActivityTestRule(HomeActivity::class.java)

  @Test
  fun loadListFromNetwork_Test() {
    Thread.sleep(4000)
    val viewGroup = onView(
        allOf(
            childAtPosition(
                childAtPosition(
                    withId(R.id.rvFeed),
                    0
                ),
                0
            ),
            isDisplayed()
        )
    )
    viewGroup.check(matches(isDisplayed()))
  }

  @Test
  fun isInternetAvailable_Test() {
    Assert.assertTrue(isNetworkAvailable())
  }

  private fun isNetworkAvailable(): Boolean {
    val activity = mActivityTestRule.activity
    val connectivityManager =
      activity.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager?
    val activeNetworkInfo = connectivityManager!!.activeNetworkInfo
    return activeNetworkInfo != null && activeNetworkInfo.isConnected
  }

  private fun childAtPosition(
    parentMatcher: Matcher<View>,
    position: Int
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
}
