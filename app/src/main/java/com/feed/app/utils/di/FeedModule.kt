package com.feed.app.utils.di

import android.content.Context
import com.feed.app.data.FeedRepository
import com.feed.app.data.database.FeedDatabase
import com.feed.app.data.database.dao.FeedDao
import com.feed.app.data.network.RetrofitAdapter
import com.feed.app.data.network.WebService

class FeedModule(context: Context) : FeedComponent {
  override val feedDao: FeedDao = FeedDatabase.getInstance(context)
      .feedDao()
  override val api: WebService = RetrofitAdapter.getService()
  override val feedRepository: FeedRepository = FeedRepository(api,feedDao)
}