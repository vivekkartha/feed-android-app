package com.feed.app.data.network

import retrofit2.http.GET

interface WebService {
  @GET("s/2iodh4vg0eortkl/facts.json")
  fun getAllFeed()
}