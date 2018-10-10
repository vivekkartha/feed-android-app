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
  fun insertAll_Test() {
    val feed = getFeed(1)
    feedDao.insertAll(feed)
    feedDao.getAll()
        .test()
        .assertValue { feeds -> feeds.rows.size == 1 }
  }

  @Test
  fun getAll_Test() {
    val feed = getFeed(3)
    feedDao.insertAll(feed)
    feedDao.getAll()
        .test()
        .assertValueCount(1)
  }

  private fun getFeed(n: Int): Feed {
    val feedItems = mutableListOf<FeedItem>()
    repeat(n) {
      feedItems.add(
          FeedItem(
              title = "itemTitle$n", description = "description$n", imageHref = "www.test$n.com"
          )
      )
    }
    return Feed(1, "Title", feedItems)
  }
}