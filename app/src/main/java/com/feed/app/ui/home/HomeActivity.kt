package com.feed.app.ui.home

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.widget.Toast
import com.feed.app.R
import com.feed.app.data.FeedItem
import com.feed.app.data.Status.ERROR
import com.feed.app.data.Status.SUCCESS
import com.feed.app.utils.di.FeedComponent
import com.feed.app.utils.di.FeedModule
import kotlinx.android.synthetic.main.home_activity.rvFeed

class HomeActivity : AppCompatActivity() {

  private lateinit var feedRecyclerAdapter: FeedRecyclerAdapter
  private var feedList = mutableListOf<FeedItem>()

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.home_activity)
    injectDependencies()
    val viewModel = ViewModelProviders.of(this)
        .get(HomeViewModel::class.java)
    initList()
    observeOnFeedResponseStatus(viewModel)
    viewModel.getFeed()
  }

  private fun injectDependencies() {
    FeedComponent.instance = FeedModule()
  }

  private fun initList() {
    feedRecyclerAdapter = FeedRecyclerAdapter(feedList, this)
    rvFeed.adapter = feedRecyclerAdapter
  }

  private fun observeOnFeedResponseStatus(viewModel: HomeViewModel) {
    viewModel.feedLiveData.observe(this, Observer { status ->
      when (status) {
        is SUCCESS -> setFeedToList(status.feed.rows)
        is ERROR -> showError()
      }
    })
  }

  private fun showError() {
    Toast.makeText(this, "Unable to fetch", Toast.LENGTH_SHORT)
        .show()
  }

  private fun setFeedToList(feed: List<FeedItem>) {
    feedList.clear()
    feedList.addAll(feed)
    feedRecyclerAdapter.notifyDataSetChanged()
  }
}
