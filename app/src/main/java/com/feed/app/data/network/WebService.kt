package com.feed.app.data.network

import com.feed.app.data.database.entity.Feed
import io.reactivex.Flowable
import io.reactivex.Observable
import retrofit2.http.GET

interface WebService {
  @GET("s/2iodh4vg0eortkl/facts.json")
  fun getAllFeed(): Flowable<Feed>
}