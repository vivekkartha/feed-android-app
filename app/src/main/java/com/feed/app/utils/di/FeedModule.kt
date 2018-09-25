package com.feed.app.utils.di

import com.feed.app.data.FeedRepository
import com.feed.app.data.network.RetrofitAdapter
import com.feed.app.data.network.WebService

class FeedModule : FeedComponent {
  override val api: WebService = RetrofitAdapter.getService()
  override val feedRepository: FeedRepository = FeedRepository(api)
}