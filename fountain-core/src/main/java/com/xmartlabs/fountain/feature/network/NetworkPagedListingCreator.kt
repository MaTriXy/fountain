package com.xmartlabs.fountain.feature.network

import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.Transformations
import android.arch.paging.LivePagedListBuilder
import android.arch.paging.PagedList
import com.xmartlabs.fountain.ListResponse
import com.xmartlabs.fountain.Listing
import com.xmartlabs.fountain.adapter.BaseNetworkDataSourceAdapter
import java.util.concurrent.Executor

object NetworkPagedListingCreator {
  fun <Value, ServiceResponse : ListResponse<out Value>> createListing(
      firstPage: Int,
      ioServiceExecutor: Executor,
      pagedListConfig: PagedList.Config,
      networkDataSourceAdapter: BaseNetworkDataSourceAdapter<ServiceResponse>
  ): Listing<Value> {
    val sourceFactory = NetworkPagedDataSourceFactory(
        firstPage = firstPage,
        ioServiceExecutor = ioServiceExecutor,
        pagedListConfig = pagedListConfig,
        networkDataSourceAdapter = networkDataSourceAdapter
    )
    val livePagedList = LivePagedListBuilder(sourceFactory, pagedListConfig)
        .build()

    val refreshTrigger = MutableLiveData<Unit>()
    val refreshState = Transformations.switchMap(refreshTrigger) {
      sourceFactory.resetData()
    }

    return Listing(
        pagedList = livePagedList,
        networkState = Transformations.switchMap(sourceFactory.sourceLiveData) {
          it.networkState
        },
        retry = {
          sourceFactory.retry()
        },
        refresh = {
          refreshTrigger.value = null
        },
        refreshState = refreshState
    )
  }
}
