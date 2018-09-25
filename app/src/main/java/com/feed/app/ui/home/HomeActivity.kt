package com.feed.app.ui.home

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.feed.app.R.layout

class HomeActivity : AppCompatActivity() {

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(layout.activity_main)
    val viewModel = ViewModelProviders.of(this).get(HomeViewModel::class.java)
    viewModel.feedLiveData.observe(this, Observer {
      //TODO: Set to list
    })
  }
}
