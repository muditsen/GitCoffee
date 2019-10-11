package com.gitcoffee.jek.presentation.view

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.gitcoffee.jek.R
import com.gitcoffee.jek.network.Status
import com.gitcoffee.jek.presentation.viewmodels.TrendingViewModel

class GitTrendActivity : AppCompatActivity(),SwipeRefreshLayout.OnRefreshListener,View.OnClickListener{


    private lateinit var trendViewModel: TrendingViewModel


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_git_trend)
        trendViewModel  = ViewModelProviders.of(this).get(TrendingViewModel::class.java)

        trendViewModel.fetchTrendingRepo()

        addObserver()
    }

    override fun onRefresh() {

    }

    override fun onClick(view:View?) {

    }

    private fun addObserver(){
        trendViewModel.getTrendingRepo().observe(this, Observer {
            when(it.status){
                Status.LOADING->{
                    Toast.makeText(this,"Loading..",Toast.LENGTH_SHORT).show()
                }

                Status.ERROR->{
                    Toast.makeText(this,"Error..",Toast.LENGTH_SHORT).show()
                }

                Status.SUCCESS->{
                    Toast.makeText(this,"Success..",Toast.LENGTH_SHORT).show()
                }
            }
        })
    }

}