package com.gitcoffee.jek.presentation.view

import android.content.res.Resources
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.facebook.shimmer.ShimmerFrameLayout
import com.gitcoffee.jek.R
import com.gitcoffee.jek.network.Status
import com.gitcoffee.jek.presentation.JekApplication
import com.gitcoffee.jek.presentation.models.TrendingRepoItem
import com.gitcoffee.jek.presentation.view.adapters.GitTrendingAdapter
import com.gitcoffee.jek.presentation.viewmodels.TrendingViewModel
import javax.inject.Inject

class GitTrendActivity : AppCompatActivity(), View.OnClickListener {

    //Views
    private lateinit var swipeRefreshLayout: SwipeRefreshLayout

    private lateinit var rvTrendingListView: RecyclerView

    private lateinit var sfl: ShimmerFrameLayout

    private lateinit var rlErrorScreen: View

    //ViewModel
    private lateinit var trendViewModel: TrendingViewModel

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    @Inject
    lateinit var gitTrendingAdapter: GitTrendingAdapter

    private var isFirstFetch: Boolean = true


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        //Inject variables
        (applicationContext as JekApplication).getAppComponent().inject(this)


        setContentView(R.layout.activity_git_trend)
        setSupportActionBar(findViewById(R.id.toolbar))
        setUpActionBar()
        initViews()

        trendViewModel = ViewModelProviders.of(this, viewModelFactory).get(TrendingViewModel::class.java)

        addObserver()

        trendViewModel.fetchTrendingRepo()

    }

    private fun setUpActionBar() {
        supportActionBar?.apply {
            title = ""
            setDisplayShowTitleEnabled(false)
            setDisplayHomeAsUpEnabled(false)
            elevation = 2.0f * Resources.getSystem().displayMetrics.density
        }
    }

    private fun initViews() {
        sfl = findViewById(R.id.sfl_trendList)
        rvTrendingListView = findViewById(R.id.rv_git_trend)
        rvTrendingListView.apply {
            layoutManager = LinearLayoutManager(this@GitTrendActivity)
            adapter = gitTrendingAdapter
        }

        swipeRefreshLayout = findViewById(R.id.srl_trendList)

        swipeRefreshLayout.setOnRefreshListener {
           // isFirstFetch = true
            trendViewModel.fetchTrendingRepo(force = true)
        }

        findViewById<View>(R.id.tv_retry).setOnClickListener {
            trendViewModel.fetchTrendingRepo(force=true)
            isFirstFetch = true
        }

        rlErrorScreen = findViewById(R.id.rl_errorScreen)
    }


    override fun onClick(view: View?) {

    }

    private fun addObserver() {

        trendViewModel.getTrendingRepoData().observe(this, Observer {
            when (it.status) {
                Status.LOADING -> performTrendRepoLoading()

                Status.ERROR -> handleTrendRepoError()

                Status.SUCCESS -> handleTrendRepoSuccess(it.data!!)
            }
        })
    }

    private fun performTrendRepoLoading() {
        if (isFirstFetch) {
            sfl.visibility = View.VISIBLE
            sfl.startShimmer()
        }
        rvTrendingListView.visibility = View.GONE
        sfl.startLayoutAnimation()
        rlErrorScreen.visibility = View.GONE
    }

    private fun handleTrendRepoError() {
        sfl.stopShimmer()
        sfl.visibility = View.GONE
        swipeRefreshLayout.isRefreshing = false
        rlErrorScreen.visibility = View.VISIBLE

    }

    private fun handleTrendRepoSuccess(responseList: List<TrendingRepoItem>) {
        rvTrendingListView.visibility = View.VISIBLE
        sfl.stopShimmer()
        isFirstFetch = false
        swipeRefreshLayout.isRefreshing = false
        sfl.visibility = View.GONE
        gitTrendingAdapter.trendingRepoItems = responseList
        gitTrendingAdapter.notifyDataSetChanged()
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.item_sort_by_stars -> {
                return true
            }

            R.id.item_sort_by_name -> {
                return true
            }
        }
        return super.onOptionsItemSelected(item)

    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        //menuInflater.inflate(R.menu.menu_trending_activity, menu)
        return true
    }

}
