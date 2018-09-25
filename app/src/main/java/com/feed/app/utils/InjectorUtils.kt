package com.feed.app.utils

import android.content.Context
import com.feed.app.data.FeedRepository

object InjectorUtils {
  private fun provideRepository(context: Context)= FeedRepository()
}