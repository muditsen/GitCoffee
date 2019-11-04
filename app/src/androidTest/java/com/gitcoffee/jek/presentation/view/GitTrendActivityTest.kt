package com.gitcoffee.jek.presentation.view

import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.espresso.matcher.ViewMatchers.withText
import androidx.test.platform.app.InstrumentationRegistry
import androidx.test.rule.ActivityTestRule
import com.gitcoffee.jek.R
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Rule
import org.junit.Test


class GitTrendActivityTest {


    @get:Rule
    var mActivityRule: ActivityTestRule<GitTrendActivity> = ActivityTestRule(
        GitTrendActivity::class.java
    )

    @Test
    fun useAppContext() {
        // Context of the app under test.
        val appContext = InstrumentationRegistry.getInstrumentation().context
        assertEquals("com.gitcoffee.jek.test", appContext.packageName)
    }


    @Test
    fun toolbarTitleTextTest() {
        onView(withId(R.id.toolbar_title)).check(matches(withText(trending)))
    }

    @Test
    fun swipeToRefreshLayout() {
        onView(withId(R.id.srl_trendList)).perform(ViewActions.swipeDown())
            .check { view, _ -> assertTrue((view as SwipeRefreshLayout).isRefreshing) }
    }

    companion object {
        const val trending: String = "Trending"
    }
}