package com.feed.app.ui.home

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.feed.app.R
import com.feed.app.data.FeedItem
import com.feed.app.utils.di.FeedComponent
import com.feed.app.utils.di.FeedModule
import kotlinx.android.synthetic.main.home_activity.rvFeed

class HomeActivity : AppCompatActivity() {

  private lateinit var feedRecyclerAdapter: FeedRecyclerAdapter
  private var feedList = mutableListOf<FeedItem>()

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.home_activity)
    val viewModel = ViewModelProviders.of(this)
        .get(HomeViewModel::class.java)
    injectDependencies()
    initList()
    setFeedsToList(viewModel)
  }

  private fun injectDependencies() {
    FeedComponent.instance = FeedModule()
  }

  private fun initList() {
    feedRecyclerAdapter = FeedRecyclerAdapter(feedList, this)
    rvFeed.adapter = feedRecyclerAdapter
  }

  private fun setFeedsToList(viewModel: HomeViewModel) {
    viewModel.feedLiveData.observe(this, Observer { feed ->
      feedList.clear()
      feedList.addAll(feed?.rows!!)
      feedRecyclerAdapter.notifyDataSetChanged()
    })
  }
}
