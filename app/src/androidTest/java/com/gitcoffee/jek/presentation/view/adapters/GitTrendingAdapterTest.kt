package com.gitcoffee.jek.presentation.view.adapters

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.contrib.RecyclerViewActions
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.rule.ActivityTestRule
import com.gitcoffee.jek.R
import com.gitcoffee.jek.presentation.models.TrendingRepoItem
import com.gitcoffee.jek.presentation.view.GitTrendActivity
import org.junit.Before
import org.junit.Rule
import org.junit.Test

//@RunWith(AndroidJUnit4ClassRunner::class)
class GitTrendingAdapterTest {

    @get:Rule
    var mActivityRule: ActivityTestRule<GitTrendActivity> = ActivityTestRule(
        GitTrendActivity::class.java
    )

    @Before
    fun setUp() {
        //Idiling test for 3 seconds to fetch data from server.
        Thread.sleep(3000)


    }

    private fun getTestList(): ArrayList<TrendingRepoItem> {
        val list = ArrayList<TrendingRepoItem>()

        val numberOfItem = 100
        for (i in 0 until numberOfItem) {

            val trendingRepoEntity = TrendingRepoItem(
                author = "scds $i",
                name = "Mudit $i",
                avatar = "mudit $i",
                url = "http://github.com/muditsen $i",
                description = "Hey there this is testing desc. $i",
                language = "Java/kotlin $i",
                languageColor = "#343434",
                stars = 18 + i,
                forks = 12 + i,
                currentPeriodStars = 9 + i,
                builtBy = ArrayList()
            )
            list.add(trendingRepoEntity)
        }


        return list
    }

    @Test
    fun itemClickOnRecyclerView() {

        setUpActivity()

        val view = mActivityRule.activity.findViewById<RecyclerView>(R.id.rv_git_trend)

        val adapter = view.adapter


        val secondLastItem = (adapter?.itemCount ?: 1) - 2

        onView(withId(R.id.rv_git_trend)).perform(
            RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>(
                0,
                click()
            )
        )

        onView(withId(R.id.rv_git_trend)).perform(
            RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>(
                3,
                click()
            )
        )

        onView(withId(R.id.rv_git_trend)).perform(
            RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>(
                2,
                click()
            )
        )

        onView(withId(R.id.rv_git_trend)).perform(
            RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>(
                1,
                click()
            )
        )


        onView(withId(R.id.rv_git_trend)).perform(
            RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>(
                secondLastItem,
                click()
            )
        )

        onView(withId(R.id.rv_git_trend)).perform(
            RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>(
                secondLastItem - 5,
                click()
            )
        )


    }

    private fun setUpActivity() {

        /*val list = getTestList()
        mActivityRule.activity.runOnUiThread {
            mActivityRule.activity.findViewById<View>(R.id.sfl_trendList).visibility = View.GONE
            mActivityRule.activity.findViewById<View>(R.id.rv_git_trend).visibility = View.VISIBLE
            mActivityRule.activity.gitTrendingAdapter.trendingRepoItems = list
            mActivityRule.activity.gitTrendingAdapter.notifyDataSetChanged()
        }
*/
    }


    @Test
    fun itemClickOfSameItem() {

        setUpActivity()

        onView(withId(R.id.rv_git_trend)).perform(
            RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>(
                0,
                click()
            )
        )

        onView(withId(R.id.rv_git_trend)).perform(
            RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>(
                0,
                click()
            )
        )

        onView(withId(R.id.rv_git_trend)).perform(
            RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>(
                3,
                click()
            )
        )

        onView(withId(R.id.rv_git_trend)).perform(
            RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>(
                3,
                click()
            )
        )


    }


}