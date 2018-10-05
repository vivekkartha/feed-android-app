package com.feed.app

import android.arch.core.executor.testing.InstantTaskExecutorRule
import android.arch.persistence.room.Room
import android.support.test.InstrumentationRegistry
import android.support.test.runner.AndroidJUnit4
import com.feed.app.data.database.FeedDatabase
import com.feed.app.data.database.dao.FeedDao
import com.feed.app.data.database.entity.Feed
import com.feed.app.data.database.entity.FeedItem
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class FeedDaoTest {

  private lateinit var feedDao: FeedDao
  @Rule @JvmField
  var instantTaskExecutorRule = InstantTaskExecutorRule()

  @Before
  fun initDb() {
    val feedDatabase =
      Room.inMemoryDatabaseBuilder(InstrumentationRegistry.getContext(), FeedDatabase::class.java)
          .allowMainThreadQueries()
          .build()
    feedDao = feedDatabase.feedDao()
  }

  @Test
  fun insertion_Test() {
    val feed = getFeed()
    feedDao.insertAll(feed)
    feedDao.getAll()
        .test()
        .assertValue { feed -> feed.rows.size == 1 }
  }

  private fun getFeed(): Feed = Feed(
      1, "Title", listOf(
      FeedItem(title = "itemTitle", description = "description", imageHref = "www.test.com")
  )
  )
}