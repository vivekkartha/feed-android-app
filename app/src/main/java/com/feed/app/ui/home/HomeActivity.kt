package com.feed.app.ui.home

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.content.Context
import android.net.ConnectivityManager
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.view.View
import android.widget.Toast
import com.feed.app.R
import com.feed.app.data.database.entity.FeedItem
import com.feed.app.data.database.entity.Status.ERROR
import com.feed.app.data.database.entity.Status.LOADING
import com.feed.app.data.database.entity.Status.SUCCESS
import com.feed.app.utils.di.FeedComponent
import com.feed.app.utils.di.FeedModule
import kotlinx.android.synthetic.main.home_activity.progressBar
import kotlinx.android.synthetic.main.home_activity.rvFeed
import kotlinx.android.synthetic.main.home_activity.swipe
import kotlinx.android.synthetic.main.home_activity.tvError

class HomeActivity : AppCompatActivity() {

  private lateinit var feedRecyclerAdapter: FeedRecyclerAdapter
  private var feedList = mutableListOf<FeedItem>()

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.home_activity)
    injectDependencies(this)
    val viewModel = getViewModel()
    setAdapterAndLayoutManagerToRecyclerView()
    /** Listen to feed response */
    observeOnFeedResponseStatus(viewModel)
    if (!isNetworkAvailable()) showError()
    /**Fetch the feed */
    if (savedInstanceState == null) viewModel.getFeed()

    handleErrorRefreshClick(viewModel)
    swipe.setOnRefreshListener {
      if (!isNetworkAvailable()) {
        warnNoInternet()
        dismissRefresh()
      } else viewModel.getFeed(showLoading = false)
    }
  }

  private fun dismissRefresh() {
    swipe?.isRefreshing = false
  }

  private fun warnNoInternet() {
    Toast.makeText(this, "You are offline.", Toast.LENGTH_SHORT)
        .show()
  }

  private fun handleErrorRefreshClick(viewModel: HomeViewModel) {
    tvError.setOnClickListener {
      if(isNetworkAvailable()) viewModel.getFeed() else warnNoInternet()
    }
  }

  private fun getViewModel() = ViewModelProviders.of(this)
      .get(HomeViewModel::class.java)

  private fun injectDependencies(context: Context) {
    FeedComponent.instance = FeedModule(context)
  }

  private fun setAdapterAndLayoutManagerToRecyclerView() {
    feedRecyclerAdapter = FeedRecyclerAdapter(feedList, this)
    rvFeed.layoutManager = LinearLayoutManager(this)
    rvFeed.adapter = feedRecyclerAdapter
  }

  private fun isNetworkAvailable(): Boolean {
    val connectivityManager =
      getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager?
    val activeNetworkInfo = connectivityManager!!.activeNetworkInfo
    return activeNetworkInfo != null && activeNetworkInfo.isConnected
  }

  private fun observeOnFeedResponseStatus(viewModel: HomeViewModel) {
    viewModel.feedLiveData.observe(this, Observer { status ->
      when (status) {
        is SUCCESS -> onFeedReceived(status)
        is ERROR -> onFeedError()
        is LOADING -> if (isNetworkAvailable()) showProgress()
      }
    })
  }

  private fun onFeedError() {
    hideProgress()
    showError()
  }

  private fun onFeedReceived(status: SUCCESS) {
    hideProgress()
    dismissRefresh()
    tvError?.visibility = View.GONE
    supportActionBar?.title = status.feed.title
    setFeedToList(status.feed.rows)
  }

  private fun hideProgress() {
    progressBar?.visibility = View.GONE
  }

  private fun showProgress() {
    progressBar.visibility = View.VISIBLE
    tvError.visibility = View.GONE
  }

  private fun showError() {
    dismissRefresh()
    tvError.visibility = View.VISIBLE
  }

  private fun setFeedToList(feed: List<FeedItem>) {
    feedList.clear()
    feedList.addAll(feed)
    feedRecyclerAdapter.notifyDataSetChanged()
  }
}
