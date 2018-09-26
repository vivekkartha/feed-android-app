package com.feed.app.ui.home

import android.annotation.SuppressLint
import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import com.feed.app.data.FeedRepository
import com.feed.app.data.Status
import com.feed.app.utils.di.app
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.rxkotlin.subscribeBy
import io.reactivex.schedulers.Schedulers

class HomeViewModel(private var feedRepository: FeedRepository = app().feedRepository) : ViewModel() {

  var feedLiveData: MutableLiveData<Status> = MutableLiveData()

  @SuppressLint("CheckResult")
  fun getFeed() {
    feedLiveData.value = Status.LOADING
    feedRepository.getFeed()
        .observeOn(AndroidSchedulers.mainThread())
        .subscribeOn(Schedulers.io())
        .subscribeBy(onNext = { feed ->
          feedLiveData.value = Status.SUCCESS(feed)
        },
            onError = { feedLiveData.value = Status.ERROR("Unable to fetch feed") })
  }
}