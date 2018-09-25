package com.feed.app.utils.di

import com.feed.app.data.FeedRepository
import com.feed.app.data.network.WebService

fun app() = FeedComponent.instance
interface FeedComponent {
  val feedRepository: FeedRepository
  val api: WebService

  companion object {
    lateinit var instance: FeedComponent
  }
}